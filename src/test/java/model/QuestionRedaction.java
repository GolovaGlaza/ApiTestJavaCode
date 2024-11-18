package model;

public class QuestionRedaction {
    private String name;
    private String desc;
    private String question;
    private String questionId;

    public QuestionRedaction(String question, String desc, String name, String questionId){
        this.name = name;
        this.desc = desc;
        this.question = question;
        this.questionId = questionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
