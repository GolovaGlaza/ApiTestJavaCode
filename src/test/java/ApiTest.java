import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.*;
import org.bson.Document;
import org.junit.jupiter.api.*;
import pojo.*;
import utils.DataBaseUtils;


import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;


public class ApiTest {
    private User user;
    private UserCreated userCreated;
    private String token;
    private static DataBaseUtils dataBaseUtils;
    private Question question;
    private static String questionId;
    private QuestionRedaction questionRedaction;
    private Quiz quiz;


    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://aqa-api.javacode.ru/api";
        String mongoUri = "mongodb://javacode:bestEducationEver@80.66.64.141:27017/estim?authSource=admin";
        dataBaseUtils = new DataBaseUtils(mongoUri);

    }

    @BeforeEach
    public void loginTest(){
        user = new User("orlov_alexey", "&vpubOt1Makv@C$");
        token = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(baseURI + "/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getString("token");

        Document userDoc = dataBaseUtils.getUserByUserName(user.getUsername());
        System.out.println(userDoc.toJson());

    }

    @Test
    public void createUserTest() {
        Faker faker = new Faker();
        UserCreated userCreated = new UserCreated(faker.name().firstName(), faker.name().lastName(), faker.internet().emailAddress(),
                faker.name().username(), faker.internet().password(8,16));

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(userCreated)
                .post(baseURI + "/user-auth1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        System.out.println("Response Body: " + response.getBody().asString());
        Document userDoc = dataBaseUtils.getUserByUserName(userCreated.getUsername());
        System.out.println(userDoc.toJson());
    }

    @Test
    public void addQuestionTest(){
        question = new Question("вопрос");

        questionId = given()
                .contentType(ContentType.JSON)
                .header("Authorization",  token)
                .body(question)
                .post(baseURI + "/theme-question")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getString("data._id");
        System.out.println(questionId);
    }

    @Test
    public void editQuestionTest() {
        question = new Question("вопрос");

        questionId = given()
                .contentType(ContentType.JSON)
                .header("Authorization",  token)
                .body(question)
                .post(baseURI + "/theme-question")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getString("data._id");
        System.out.println(questionId);

        String name = "asdsad";
        String factsName = "sdadsa";
        String desc = "ddddd";
        String useCasesName = "dsaddsa";
        String useCasesDesc = "asdsadsa";
        String answer = "ответ";

        String edit = String.format("{\n" +
                "  \"currentLTS\": \"\",\n" +
                "  \"changeKey\": \"version\",\n" +
                "  \"question\": \"%s\",\n" +
                "  \"LTP\": {\n" +
                "    \"data\": {\n" +
                "      \"jsDetails\": \"\",\n" +
                "      \"comment\": \"\",\n" +
                "      \"quizes\": [],\n" +
                "      \"hints\": [],\n" +
                "      \"type\": \"\",\n" +
                "      \"videos\": [],\n" +
                "      \"name\": \"%s\",\n" +
                "      \"hashTags\": [],\n" +
                "      \"title\": \"\",\n" +
                "      \"answer\": \"%s\",\n" +
                "      \"facts\": [\n" +
                "        {\n" +
                "          \"name\": \"%s\",\n" +
                "          \"desc\": \"%s\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"useCases\": [\n" +
                "        {\n" +
                "          \"name\": \"%s\",\n" +
                "          \"desc\": \"%s\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"originalDuplicateId\": \"\",\n" +
                "      \"questionId\": \"%s\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"versionDetails\": {\n" +
                "    \"patch\": 0,\n" +
                "    \"subVersion\": 0,\n" +
                "    \"version\": 1,\n" +
                "    \"versionStr\": \"1.0.0\"\n" +
                "  }\n" +
                "}",questionId, name, answer, factsName, desc, useCasesName, useCasesDesc, questionId);

        System.out.println(edit);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(edit)
                .post(baseURI + "/create-lts")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        System.out.println("Response Body: " + response.getBody().asString());
        JsonPath jsonPath = response.jsonPath();
        assertThat("Имя не совпадает!", jsonPath.getString("data.questionDB.name"), equalTo(name));
        assertThat("Ответ не совпадает!", jsonPath.getString("data.questionDB.answer"), equalTo(answer));
        assertThat("Факты не совпадают!", jsonPath.getString("data.questionDB.facts[0].name"), equalTo(factsName));
        assertThat("Деск не совпадает!", jsonPath.getString("data.questionDB.facts[0].desc"), equalTo(desc));
    }


    @Test
    public void QuizTest(){
        quiz = new Quiz("новый квиз", "quiz", true);
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(quiz)
                .post(baseURI + "/quiz")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        assertThat("Имя квиза не совпадает!", response.jsonPath().getString("data.name"), equalTo(quiz.getName()));
        assertThat("Тип квиза не совпадает!", response.jsonPath().getString("data.answerType"), equalTo(quiz.getAnswerType()));
        assertThat("Статус активности не совпадает!", response.jsonPath().getBoolean("data.isValid"), equalTo(quiz.isValid()));
    }

    @Test
    public void testCreateCourseModule() {
        CourseModuleRequest request = new CourseModuleRequest(
                "тест",
                Arrays.asList(1000, 1001, 1002, 1005)
        );

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(request)
                .post(baseURI + "/course-module")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        assertThat("Имя не совпадает", response.jsonPath().getString("data.name"), equalTo(request.getName()));

        List<Integer> actualQuestions = response.jsonPath().getList("data.questions", Integer.class);
        assertThat("Список вопросов не совпадает", actualQuestions, equalTo(request.getQuestions()));
    }

    @Test
    public void testCourseCreate() throws JsonProcessingException {
        CourseRequest courseRequest = new CourseRequest("Test");

        CourseRequest.Modules modules = new CourseRequest.Modules();
        modules.setModule("1000");
        modules.setName("test");
        courseRequest.setModules(List.of(modules));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(courseRequest);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(jsonRequest)
                .post(baseURI + "/course")
                .then()
                .log().all()
                .extract()
                .response();

        response.then().statusCode(200);

        response.then()
                .body("data.name", equalTo("Test"))
                .body("data.modules[0].module", equalTo("1000"))
                .body("data.modules[0].name", equalTo("test"));
    }

    @Test
    public void testExamenCreate(){
        CourseExamenRequest request = new CourseExamenRequest("Экзамен", 60);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(request)
                .post(baseURI + "/exam")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        assertThat("Название экзамена не совпадает", response.jsonPath().getString
                ("data.name"), equalTo(request.getName()));
        assertThat("minutesStr не совподает", response.jsonPath().getString
                ("data.minutesStr"), equalTo(String.valueOf(request.getMinutesStr())));
    }

    @Test
    public void testTemplate() throws JsonProcessingException {
        TemplateRequest templateRequest = new TemplateRequest("Test", "test");

        TemplateRequest.Exam exam = new TemplateRequest.Exam();
        exam.setSourceId("1005");
        templateRequest.setExams(List.of(exam));

        TemplateRequest.Course course = new TemplateRequest.Course();
        course.setSourceId("1003");
        templateRequest.setCourses(List.of(course));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(templateRequest);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(jsonRequestBody)
                .post(baseURI + "/user-hr-template")
                .then()
                .log().all()
                .extract()
                .response();
        response.then().statusCode(200);

        response.then()
                .body("data.name", equalTo("Test"))
                .body("data.desc", equalTo("test"))
                .body("data.exams[0].sourceId", equalTo("1005"))
                .body("data.courses[0].sourceId", equalTo("1003"));
    }

    @Test
    public void testAuthorizationError(){
        AuthorizationErrorRequest request = new AuthorizationErrorRequest("test", "test");

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "some_token")
                .body(request)
                .post(baseURI + "/send-meters-by-seconds")
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .response();
    }
}
