package edu.qut.data.questions.model.json;

public class Choice {

    public Choice() {
        // Need a dummy constructor for Jackson
    }

    public Choice(String choice, Integer votes) {
        this.choice = choice;
        this.votes = votes;
    }

    private String choice;
    private Integer votes;

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
