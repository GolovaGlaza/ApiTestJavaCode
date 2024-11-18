package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Quiz {

    @JsonProperty("name")
    private String name;

    @JsonProperty("answerType")
    private String answerType;

    @JsonProperty("isValid")
    private boolean isValid;

    public Quiz(String name, String answerType, boolean isValid){
        this.name = name;
        this.answerType = answerType;
        this.isValid = isValid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
