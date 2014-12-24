package controllers;

import action.TokenSecured;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Login;
import models.MedicalAsk;
import models.MedicalQuestion;
import models.MedicalResponse;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.NeoKnowledgeBase;
import utils.ResponseHelper;

import javax.inject.Inject;
import java.util.List;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:09
 */
@SuppressWarnings("unchecked")
public class Medical extends Controller {

    @Inject
    private NeoKnowledgeBase kb;

    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


    @BodyParser.Of(BodyParser.Json.class)
    @Security.Authenticated(TokenSecured.class)
    public Result feedback() {
        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result ask() {
        MedicalAsk question;
        try {
            question = mapper.treeToValue(request().body().asJson(), MedicalAsk.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest("Ask ill formatted");
        }
        String token = question.token;
        List<Integer> choices = question.choices;

        MedicalResponse response = analyze(token, choices);
        return ok(ResponseHelper.generate(response));
    }


    //TODO
    private MedicalResponse analyze(String token, List<Integer> choices) {
        Login login = Users.getLogin(token);
        if (login != null) {
            kb.evaluate(choices, login.sessionId);
        }
        kb.evaluate(choices);

        MedicalResponse response = new MedicalResponse();
        MedicalQuestion question = response.createQuestion();
        question.setQuestionId(24)
                .setQuestionText("How long has it last")
                .setQuestionType(MedicalQuestion.SINGLE_CHOICE);
        question.createChoice()
                .setAnswerId(0)
                .setAnswerText("1-2 hours");

        return response;
    }
}
