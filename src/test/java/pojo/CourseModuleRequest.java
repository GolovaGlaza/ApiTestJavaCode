package pojo;

import java.util.List;

public class CourseModuleRequest {
    private String name;
    private List<Integer> questions;

    public CourseModuleRequest(String name, List<Integer> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }
}

