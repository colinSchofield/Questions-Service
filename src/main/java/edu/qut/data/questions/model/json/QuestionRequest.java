package edu.qut.data.questions.model.json;

import java.util.List;

/** Contains the JSON of a QuestionRequest used during a create */
public class QuestionRequest {

    private String question;
    private List<String> choices;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
