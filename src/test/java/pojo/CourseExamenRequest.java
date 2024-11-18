package pojo;

public class CourseExamenRequest {
    private String name;
    private int minutesStr;

    public CourseExamenRequest(String name, int minutesStr){
        this.name = name;
        this.minutesStr = minutesStr;
    }

    public int getMinutesStr() {
        return minutesStr;
    }

    public void setMinutesStr(int minuteStr) {
        this.minutesStr = minuteStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
