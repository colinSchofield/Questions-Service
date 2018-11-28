package edu.qut.data.questions.model.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class QuestionChoice {

    private String choice;
    private Integer votes;

    public QuestionChoice() {
    }

    public QuestionChoice(String choice) {
        this.choice = choice;
        this.votes = 0;
    }

    public QuestionChoice(String choice, Integer votes) {
        this.choice = choice;
        this.votes = votes;
    }

    @DynamoDBAttribute(attributeName = "Choice")
    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    @DynamoDBAttribute(attributeName = "Votes")
    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
