package controllers;

import actions.LoginRequired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.*;
import models.*;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.DiseaseRepository;
import repositories.SessionRepository;
import repositories.SymptomRepository;
import repositories.UserRepository;
import services.EntitySearchEngine;
import services.NeoKnowledgeBase;
import utils.JsonHelper;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:09
 */
@org.springframework.stereotype.Controller
public class Medical extends Controller {

    @Inject
    private NeoKnowledgeBase kb;

    //@Inject
    private EntitySearchEngine search;

    @Inject
    private UserRepository userRepository;
    @Inject
    private SymptomRepository symptomRepository;
    @Inject
    private DiseaseRepository diseaseRepository;
    @Inject
    private SessionRepository sessionRepository;

    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    @Security.Authenticated(LoginRequired.class)
    public Result getUserInfo() {
        long userId = Users.getUserId();
        NUser u = userRepository.findByRefId(userId);
        if (u == null) {
            return forbidden("Please first update user information");
        } else {
            MUserInfo userInfo = new MUserInfo();
            userInfo.birthdate = u.birthDate;
            userInfo.sex = u.sex;
            return ok(JsonHelper.generate(userInfo));
        }
    }


    @Security.Authenticated(LoginRequired.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result updateUserInfo () {

        try {
            MUserInfo userInfo = mapper.treeToValue(request().body().asJson(), MUserInfo.class);
            long userId = Users.getUserId();
            NUser u = userRepository.findByRefId(userId);
            if (u == null)
                u = new NUser();
            u.refId = userId;
            u.birthDate = userInfo.birthdate;
            u.sex = userInfo.sex;
            userRepository.save(u);
            return ok();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest();
        }
    }

    public Result listUsers () {
        NUser u = new NUser();

        NSession session = u.newSession();
        userRepository.save(u);

        Iterable<NUser> users = userRepository.findAll();
        NUser t = users.iterator().next();
        return ok();
    }



    @BodyParser.Of(BodyParser.Json.class)
    public Result ask() {
        try {
            MRecord question = mapper.treeToValue(request().body().asJson(), MRecord.class);

            MResponse response = null;
            if (session(Users.SESSION_USER_ID_KEY) != null) {
                long userId = Users.getUserId();
                //TODO
            } else {
            //TODO
            }

            return ok(JsonHelper.generate(response));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest();
        }
    }


    @Security.Authenticated(LoginRequired.class)
    public Result getHistory() {
        NUser u = userRepository.findBySchemaPropertyValue(NUser.REF_ID_INDEX, Users.getUserId());
        if (u == null)
            return forbidden("Please first update user information");

        MHistory history = new MHistory();
        for (NSession s : u.sessions) {
            MRecord record = history.addHistory();
            record.setCreatedTime(s.created).setSessionId(s.id);
            for (NSymptom nsym : s.symptoms)
                record.addSymptom().setName(nsym.cnText).setId(nsym.id);
            for (RDiagnosis diag : s.diagnosed) {
                NDisease ndis = diag.disease;
                MObject obj = record.addDisease().setName(ndis.cnText).setId(ndis.id);
                if (diag.diagDate != null)
                    obj.setParam("diag_tm", diag.diagDate);
            }
        }
        return ok(JsonHelper.generate(history));
    }


    @Security.Authenticated(LoginRequired.class)
    public Result requestSession() {
        NUser u = userRepository.findByRefId(Users.getUserId());
        if (u == null)
            return forbidden("Please first update user information");
        NSession s = u.newSession();
        userRepository.save(u);
        return ok(Long.toString(s.id));
    }



    @Security.Authenticated(LoginRequired.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result submitSymptoms() {

        try {
            MRecord question = mapper.treeToValue(request().body().asJson(), MRecord.class);
            NUser u = userRepository.findByRefId(Users.getUserId());
            if (u == null)
                return forbidden("Please first update user information");
            NSession s = u.newSession();
            for (MObject obj : question.diseases) {
                Long symId = obj.id;
                NSymptom symptom = symptomRepository.getById(symId);
                if (symptom == null)
                    return notFound("Invalid Symptom ID: " + Long.toString(symId));

                s.addSymptom(symptom);
            }
            sessionRepository.save(s);
            return ok(Long.toString(s.id));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest();
        }
    }


    @Security.Authenticated(LoginRequired.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result submitDisease() {

        try {
            MRecord record = mapper.treeToValue(request().body().asJson(), MRecord.class);
            NUser u = userRepository.findByRefId(Users.getUserId());
            if (u == null)
                return forbidden("Please first update user information");
            NSession s = sessionRepository.findOne(record.sessionId);
            if (s == null || !Objects.equals(s.user.id, u.id))
                return unauthorized("Invalid session");

            for (MObject obj : record.diseases) {
                NDisease d = diseaseRepository.findOne(obj.id);
                if (d == null)
                    return notFound("Invalid Disease ID: " + Long.toString(obj.id));
                RDiagnosis diag = s.addDiagnosedDisease(d);

                Date time = (Date) obj.getParam("diag_tm");
                if (time != null)
                    diag.diagDate = time;

            }

            sessionRepository.save(s);
            return ok();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest();
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result searchDiseaseByName() {
        String disText = request().body().asJson().findValue("d_txt").textValue();
        List<MEntityEntry> candidates = search.searchDiseases(disText);
        return ok(JsonHelper.generate(new MCandidates(candidates)));
    }

}
