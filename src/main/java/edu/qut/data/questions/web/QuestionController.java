package edu.qut.data.questions.web;

import edu.qut.data.questions.model.json.QuestionRequest;
import edu.qut.data.questions.model.json.Question;
import edu.qut.data.questions.model.json.validation.QuestionValidator;
import edu.qut.data.questions.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The QuestionController provides a RESTful API that is used for managing a simple question based Poll. The caller of
 * this API can query all established questions, create a poll question and list the results for an individual question.
 * <br/>
 * This service was written to run under AWS and consequently uses a DynamoDB database to store the poll results.
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionValidator questionValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(questionValidator);
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ResponseEntity<List<Question>> viewPollQuestions() {

        return questionService.viewPollQuestions();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<String> createQuestionPoll(@Valid @RequestBody QuestionRequest request) {

        return questionService.createQuestionPoll(request);
    }

    @RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
    public ResponseEntity<Question> viewPollQuestion(@PathVariable(value = "id", required = true) String id) {

        return questionService.viewPollQuestion(id);
    }
}