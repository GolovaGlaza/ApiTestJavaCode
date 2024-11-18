package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {

    @JsonProperty("name")
    private String name;

    @JsonProperty("answerType")
    private String answerType;

    @JsonProperty("isValid")
    private boolean isValid;


    public Question(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}