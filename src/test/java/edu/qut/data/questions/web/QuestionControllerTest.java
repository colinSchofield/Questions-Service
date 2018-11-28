package edu.qut.data.questions.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.qut.data.questions.model.json.Choice;
import edu.qut.data.questions.model.json.Question;
import edu.qut.data.questions.model.json.QuestionRequest;
import edu.qut.data.questions.model.json.validation.QuestionValidator;
import edu.qut.data.questions.service.QuestionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionValidator questionValidator;

    private ResponseEntity<List<Question>> questionListResponse;

    private String listResponseAsString;

    private ResponseEntity<Question> questionResponse;

    private String questionResponseAsString;

    private ResponseEntity<Question> questionNotFoundResponse;

    private QuestionRequest questionRequest;

    private ResponseEntity<String> createResponse;

    private String postContent = "        {\n"+
            "            \"question\": \"Favourite programming language?\",\n"+
            "            \"choices\": [\n"+
            "                \"Swift\",\n"+
            "                \"Python\",\n"+
            "                \"Objective-C\",\n"+
            "                \"Ruby\"\n"+
            "            ]\n"+
            "        }\n";

    @Before
    public void setup() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<Choice> choices = new ArrayList<>();
        choices.add(new Choice("Red", 2));
        choices.add(new Choice("Blue", 3));
        Question question = new Question("/questions/1", "What is your favourite colour?", "2015-08-05T08:40:51.620Z", choices);
        questionResponse = ResponseEntity.status(HttpStatus.OK).body(question);
        questionResponseAsString = mapper.writeValueAsString(question);

        List<Question> responseList = new ArrayList<>();
        responseList.add(question);
        questionListResponse = ResponseEntity.status(HttpStatus.OK).body(responseList);
        listResponseAsString = mapper.writeValueAsString(responseList);
        questionNotFoundResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        QuestionRequest questionRequest = new QuestionRequest();
        questionRequest.setQuestion("What is your favourite Fruit?");
        questionRequest.setChoices(Arrays.asList(new String[]{"Apples", "Pears", "Oranges"}));

        questionNotFoundResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        createResponse = ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @Test
    public void viewPollQuestionsTest() throws Exception {

        when(questionService.viewPollQuestions()).thenReturn(questionListResponse);
        mvc.perform(get("/questions")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(listResponseAsString));
    }

    @Test
    public void viewPollQuestionTest() throws Exception {

        when(questionService.viewPollQuestion("1")).thenReturn(questionResponse);
        mvc.perform(get("/questions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(questionResponseAsString));
    }

    @Test
    public void viewPollQuestionNotFoundTest() throws Exception {

        when(questionService.viewPollQuestion("999")).thenReturn(questionNotFoundResponse);
        mvc.perform(get("/questions/999")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
    public void createQuestionPollTest() throws Exception {

        when(questionService.createQuestionPoll(questionRequest)).thenReturn(createResponse);
        mvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postContent)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}