package controllers;

import actions.LoginRequired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.*;
import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import repositories.DiseaseRepository;
import repositories.SessionRepository;
import repositories.SymptomRepository;
import repositories.UserRepository;
import services.MedicalIntelligence;
import utils.EvaluationContext;
import utils.JsonHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:09
 */
@org.springframework.stereotype.Controller
public class OpenMedical extends Controller {

    @Autowired
    private MedicalIntelligence medicalIntelligence;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SymptomRepository symptomRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
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



    @BodyParser.Of(BodyParser.Json.class)
    public Result ask() {
        try {
            MQuery query = mapper.treeToValue(request().body().asJson(), MQuery.class);

            IMessage response = null;
            // TODO: user customized
            EvaluationContext<NDisease> eval = medicalIntelligence.evaluate(query);

            switch (query.status) {
                case MQuery.DIAGNOSIS: response = generateCandidates(eval, 5); break;//TODO: customize limit

                case MQuery.MORE_QUESTION: response = medicalIntelligence.furtherQuestions(eval, query); break;

                case MQuery.ADVICE: break; // TODO

                default: // AUTO
                    int askedQuestions = query.getQuestionIds().size();
                    double evalPerf = eval.getMaxValue();

                    if (askedQuestions > 12 || (askedQuestions > 5 && evalPerf > 0.8))
                        response = generateCandidates(eval, 5);//TODO: customize limit

                    else
                        response = medicalIntelligence.furtherQuestions(eval, query);
            }

            return ok(JsonHelper.generate(response));

        } catch (JsonProcessingException e) {
            return badRequest("Illegal format");
        }
    }


    private MCandidates generateCandidates (EvaluationContext<NDisease> eval, int limit) {
        MCandidates candidates = new MCandidates();
        final List<Map.Entry<NDisease, Double>> topCandidates = eval.getEntryOfTopNValue(limit);
        for (Map.Entry<NDisease, Double> entry : topCandidates) {
            final MEntityEntry entity = candidates.addEntityEntry();
            entity.id = entry.getKey().id;
            entity.name = entry.getKey().cnText;
            entity.value = entry.getValue();
        }
        return candidates;
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
            for (RDiagnose diag : s.diagnosed) {
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
                RDiagnose diag = s.addDiagnosedDisease(d);

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
}
