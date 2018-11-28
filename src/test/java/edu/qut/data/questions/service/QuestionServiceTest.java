package edu.qut.data.questions.service;

import edu.qut.data.questions.model.db.QuestionChoice;
import edu.qut.data.questions.model.db.QuestionPoll;
import edu.qut.data.questions.model.db.repository.QuestionPollRepository;
import edu.qut.data.questions.model.json.Question;
import edu.qut.data.questions.model.json.QuestionRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    @Autowired
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionPollRepository questionPollRepository;

    private Iterable<QuestionPoll> questionIterable;

    QuestionRequest qr;

    Optional<QuestionPoll> optional;

    Optional<QuestionPoll> optionalNotFound;

    @Before
    public void setup() {

        List<QuestionChoice> choices = new ArrayList<>();
        choices.add(new QuestionChoice("Red", 2));
        choices.add(new QuestionChoice("Blue", 3));
        QuestionPoll qp = new QuestionPoll("What is your favourite colour?", "2015-08-05T08:40:51.620Z", choices);

        optional = Optional.of(qp);
        optionalNotFound = Optional.empty();
        List<QuestionPoll> list = new ArrayList<>();
        list.add(qp);
        questionIterable =  list;

        qr = new QuestionRequest();
        qr.setQuestion("What is your favourite colour?");
        qr.setChoices(Arrays.asList(new String[]{"Red", "Orange", "Blue"}));
    }

    @Test
    public void testViewPollQuestions() {

        when(questionPollRepository.findAll()).thenReturn(questionIterable);
        ResponseEntity<List<Question>> response = questionService.viewPollQuestions();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody());
    }

    @Test
    public void testViewPollQuestionsNotFound() {

        when(questionPollRepository.findAll()).thenReturn(null);
        ResponseEntity<List<Question>> response = questionService.viewPollQuestions();
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateQuestionPoll() {
        ResponseEntity<String> response = questionService.createQuestionPoll(qr);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testViewPollQuestion() {

        when(questionPollRepository.findById("1")).thenReturn(optional);
        ResponseEntity<Question> response = questionService.viewPollQuestion("1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testViewPollQuestionNotFound() {

        when(questionPollRepository.findById("999")).thenReturn(optionalNotFound);
        ResponseEntity<Question> response = questionService.viewPollQuestion("999");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}