package edu.qut.data.questions.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** The response value to poll question queries */
public class Question {

    private String url;
    private String question;
    @JsonProperty("published_at")
    private String publishedAt;
    private List<Choice> choices;

    public Question() {
        // Need a dummy constructor for Jackson
    }

    public Question(String url, String question, String publishedAt, List<Choice> choices) {
        this.url = url;
        this.question = question;
        this.publishedAt = publishedAt;
        this.choices = choices;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}