package services;

import models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * User: ecsark
 * Date: 12/22/14
 * Time: 01:12
 */

@Service
public class GraphImporter {

    @Autowired
    private SymptomRepository symptomRepo;
    @Autowired
    private DiseaseRepository diseaseRepo;
    @Autowired
    private QuestionRepository questionGroupRepo;
    @Autowired
    private SymptomGroupRepository symptomGroupRepo;
    @Autowired
    private CheckupRepository checkupRepo;
    @Autowired
    private Neo4jTemplate template;

    private static final String NODE_DISEASE = "Disease";
    private static final String NODE_SYMPTOM_GROUP = "SymptomGroup";
    private static final String NODE_SYMPTOM = "Symptom";
    private static final String NODE_QUESTION_GROUP = "QuestionGroup";
    private static final String NODE_CHECKUP = "Checkup";

    private static final String REL_CAUSE = "CAUSE";
    private static final String REL_INCLUDE = "INCLUDE";
    private static final String REL_ASK = "ASK";

    private static final String PARAM = "PARAM";
    private static final String QUESTION_TEXT = "TEXT";
    private static final String QUESTION_TYPE = "TYPE";

    @Transactional
    public void clearAll () {
        symptomGroupRepo.deleteAll();
        symptomRepo.deleteAll();
        diseaseRepo.deleteAll();
        questionGroupRepo.deleteAll();
        checkupRepo.deleteAll();
    }

    @Transactional
    public void importNodes (File nodeFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nodeFile));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equals("") || line.startsWith("#"))
                continue;
            String[] args = line.split(":");

            testFewArgThenException(2, args.length, line);

            switch (args[1]) {
                case NODE_DISEASE:
                    NDisease d = new NDisease();
                    d.cnText = args[0];
                    diseaseRepo.save(d);
                    break;
                case NODE_SYMPTOM:
                    NSymptom s = new NSymptom();
                    s.cnText = args[0];
                    symptomRepo.save(s);
                    break;
                case NODE_SYMPTOM_GROUP:
                    NSymptomGroup g = new NSymptomGroup();
                    g.cnText = args[0];
                    symptomGroupRepo.save(g);
                    break;
                case NODE_QUESTION_GROUP:
                    NQuestion q = new NQuestion();
                    q.cnText = args[0];
                    questionGroupRepo.save(q);
                    break;
                case NODE_CHECKUP:
                    NCheckup c = new NCheckup();
                    c.cnText = args[0];
                    checkupRepo.save(c);
                    break;
                default:
                    throw new IllegalArgumentException("Line "+line+" node type unrecognized");
            }
        }
        br.close();
    }

    @Transactional
    public void importRelationship (File nodeFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(nodeFile));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.equals("") || line.startsWith("#"))
                continue;
            String[] args = line.split(":");

            testFewArgThenException(3, args.length, line);

            switch (args[1]) {
                case REL_CAUSE:
                    NDisease d = diseaseRepo.getByCnText(args[0]);
                    testNullThenException(d, line, "disease", args[0]);
                    NSymptom s = symptomRepo.getByCnText(args[2]);
                    testNullThenException(s, line, "symptom", args[2]);d.addSymptom(s);
                    break;
                case REL_ASK:
                    testFewArgThenException(7, args.length, line);

                    NQuestion qg = questionGroupRepo.getByCnText(args[0]);
                    testNullThenException(qg, line, "question", args[0]);

                    NSymptom sy = symptomRepo.getByCnText(args[2]);
                    testNullThenException(sy, line, "symptom", args[2]);

                    int index = 3;
                    RAsk ask = null;
                    while (index+1 < args.length) {
                        switch (args[index]) {
                            case QUESTION_TEXT:
                                ask = qg.addChoice(sy, args[index+1]);
                                index += 2;
                                break;
                            case QUESTION_TYPE:
                                qg.qType = Integer.parseInt(args[index+1]);
                                index += 2;
                                break;
                            default:
                                throw new IllegalArgumentException("Line "+line+" relationship type "+args[index]+" unrecognized");
                        }
                    }
                    template.save(ask);
                    template.save(qg);
                    break;
                case REL_INCLUDE:
                    NSymptomGroup sg = symptomGroupRepo.getByCnText(args[0]);
                    testNullThenException(sg, line, "symptom group", args[0]);
                    NSymptom sp = symptomRepo.getByCnText(args[2]);
                    testNullThenException(sp, line, "symptom", args[2]);
                    sg.addSymptom(sp);
                    break;
                default:
                    throw new IllegalArgumentException("Line "+line+" relationship type unrecognized");
            }
        }
    }

    private static void testNullThenException (Object obj, String lineContent, String entityType, String argContent) {
        if (obj == null)
            throw new IllegalArgumentException("In line "+lineContent+": "+entityType+" name"+
                    argContent+" undefined");
    }

    private static void testFewArgThenException (int required, int actual, String lineContent) {
        if (actual < required)
            throw new IllegalArgumentException("Line "+lineContent+" needs at least"+Integer.toString(required)+
                    " arguments, separated by colon");
    }
}

