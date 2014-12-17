package controllers;

import action.TokenSecured;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.MedicalAsk;
import models.MedicalQuestion;
import models.MedicalResponse;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.CypherExecutor;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.util.Collections.EMPTY_MAP;

/**
 * User: ecsark
 * Date: 10/29/14
 * Time: 00:09
 */
@SuppressWarnings("unchecked")
public class Medical extends Controller {

    @Inject
    private CypherExecutor cypher;

    private static ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @BodyParser.Of(BodyParser.Json.class)
    @Security.Authenticated(TokenSecured.class)
    public Result ask() {
        MedicalAsk question;
        try {
            question = mapper.treeToValue(request().body().asJson(), MedicalAsk.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return badRequest("Ask ill formatted");
        }
        String token = question.tk;
        List<Integer> choices = question.c;

        MedicalResponse response = analyze(token, choices);
        return ok(Json.toJson(response));
    }


    //TODO
    private MedicalResponse analyze(String token, List<Integer> choices) {
        final Iterator<Map<String,Object>> iterator = cypher.query("match n return n", EMPTY_MAP);
        while (iterator.hasNext()) {
            Map<String, Object> n = iterator.next();
        }

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
