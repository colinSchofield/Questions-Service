package edu.qut.data.questions.service;

import edu.qut.data.questions.model.db.QuestionChoice;
import edu.qut.data.questions.model.db.QuestionPoll;
import edu.qut.data.questions.model.db.repository.QuestionPollRepository;
import edu.qut.data.questions.model.json.Choice;
import edu.qut.data.questions.model.json.QuestionRequest;
import edu.qut.data.questions.model.json.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The QuestionService contains the business logic for managing a question poll, including callouts to the AWS DynamoDB,
 * which is used for persisting the information.
 */
@Service
public class QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionPollRepository questionPollRepository;

    public ResponseEntity<List<Question>> viewPollQuestions() {

        log.debug("About to list all poll questions");
        Iterable<QuestionPoll> questionList = questionPollRepository.findAll();

        if (questionList == null || !questionList.iterator().hasNext()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Question> jsonResponse = new ArrayList<>();
        questionList.forEach(q -> jsonResponse.add(mapToJSON(q)));

        log.debug("Returning {} questions to the caller", jsonResponse.size());
        return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
    }

    public ResponseEntity<String> createQuestionPoll(QuestionRequest request) {

        try {
            log.debug("About to create a question poll of {}.", request.getQuestion());
            List<QuestionChoice> choices = new ArrayList<>();
            request.getChoices().forEach(choice -> choices.add(new QuestionChoice(choice)));
            QuestionPoll question = new QuestionPoll(request.getQuestion(), getCurrentDateAsISO8601(), choices);

            questionPollRepository.save(question);

            log.debug("Created Question Poll with id of {}.", question.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(question.getId());

        } catch (Exception ex) {

            log.error("Error creating question poll", ex);
            throw ex;
        }
    }

    public ResponseEntity<Question> viewPollQuestion(String id) {

        log.debug("About to query database for id {}.", id);

        Optional<QuestionPoll> optional = questionPollRepository.findById(id);
        if (!optional.isPresent()) {

            log.warn("No poll question for key {}.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {

            return ResponseEntity.status(HttpStatus.OK).body(mapToJSON(optional.get()));
        }
    }

    private String getCurrentDateAsISO8601() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        return df.format(new Date());
    }

    private Question mapToJSON(QuestionPoll q) {

        List<Choice> choices = new ArrayList<>();
        q.getChoices().forEach(qc -> choices.add(new Choice(qc.getChoice(), qc.getVotes())));
        return new Question("/questions/" + q.getId(), q.getQuestion(), q.getPublishedAt(), choices);
    }
}