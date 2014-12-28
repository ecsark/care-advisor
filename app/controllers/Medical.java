package controllers;

import actions.LoginRequired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.*;
import models.NDisease;
import models.NSession;
import models.NSymptom;
import models.NUser;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.SessionRepository;
import repositories.SymptomRepository;
import repositories.UserRepository;
import services.EntitySearchEngine;
import services.NeoKnowledgeBase;
import utils.JsonHelper;

import javax.inject.Inject;
import java.util.List;

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
    private SessionRepository sessionRepository;

    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    @Security.Authenticated(LoginRequired.class)
    public Result getUserInfo() {
        long userId = Users.getUserId();
        NUser u = userRepository.findByRefId(userId);
        if (u == null) {
            return forbidden();
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
            MAsk question = mapper.treeToValue(request().body().asJson(), MAsk.class);

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
            return forbidden();

        MHistory history = new MHistory();
        for (NSession s : u.sessions) {
            MHistory.MRecord record = history.addHistory();
            record.setCreatedTime(s.created).setSessionId(s.id);
            for (NSymptom nsym : s.symptoms)
                record.addSymptom().setName(nsym.cnText).setId(nsym.id);
            for (NDisease ndis : s.diseases)
                record.addDisease().setName(ndis.cnText).setId(ndis.id);
        }
        return ok(JsonHelper.generate(history));
    }

    @Security.Authenticated(LoginRequired.class)
    public Result requestSession() {
        NUser u = userRepository.findByRefId(Users.getUserId());
        if (u == null)
            return forbidden();
        NSession s = u.newSession();
        userRepository.save(u);
        return ok(Long.toString(s.id));
    }



    @Security.Authenticated(LoginRequired.class)
    @BodyParser.Of(BodyParser.Json.class)
    public Result submitSymptoms() {

        try {
            MAsk question = mapper.treeToValue(request().body().asJson(), MAsk.class);
            NUser u = userRepository.findByRefId(Users.getUserId());
            if (u == null)
                return forbidden();
            NSession s = u.newSession();
            for (long symId : question.choices) {
                NSymptom symptom = symptomRepository.getById(symId);
                if (symptom == null)
                    return notFound("Symptom id " + symId + " not found in database!");

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
            MAsk question = mapper.treeToValue(request().body().asJson(), MAsk.class);
            NUser u = userRepository.findByRefId(Users.getUserId());
            if (u == null)
                return forbidden();
            NSession s = sessionRepository.findOne(Long.parseLong(question.token));
            if (s == null || s.user.id != u.id)
                return unauthorized();
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
