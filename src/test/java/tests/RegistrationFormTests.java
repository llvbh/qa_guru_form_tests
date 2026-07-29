package tests;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;


public class RegistrationFormTests extends TestBase {
    @BeforeEach
    void openPracticeForm() {
        open("/automation-practice-form");
    }

    @Test
    void allFieldsFilledSuccessfullyTest() {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#userEmail").setValue("harry.potter@gmail.com");
        $("#genterWrapper").$(byText("Male")).click();
        $("#subjectsInput").setValue("Maths").pressEnter();
        $("#userNumber").setValue("7778889999");
        $("#dateOfBirthInput").click();
        $("select.react-datepicker__month-select").selectOptionByValue("5");
        $("select.react-datepicker__year-select").selectOptionByValue("2003");
        $(".react-datepicker__day--015").click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#uploadPicture").uploadFromClasspath("kitten.jpg");
        $("#currentAddress").setValue("4 Privet Drive, Little Whinging, Surrey");
        $("#state").scrollTo().click();
        $("#state").scrollTo().$(byText("NCR")).click();
        $("#city").click();
        $("#city").scrollTo().$(byText("Delhi")).click();
        $("#subjectsInput").setValue("Maths").pressEnter();
        executeJavaScript("arguments[0].click();", $("#submit"));
        $(".modal-content").shouldBe(visible);
        $(".modal-content").shouldBe(visible);
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Harry Potter"));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text("harry.potter@gmail.com"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("7778889999"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("15 June,2003"));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text("Maths"));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text("Reading"));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text("kitten.jpg"));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text("4 Privet Drive, Little Whinging, Surrey"));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text("NCR Delhi"));
    }

    @Test
    void requiredFieldsSubmitSuccessfullyTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("7004778833");
        $("#submit").scrollTo().click();
        $(".modal-content").shouldBe(visible);
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Harry Potter"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("7004778833"));
    }

    @Test
    void invalidEmailShowsErrorTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#gender-radio-2").click();
        $("#userNumber").setValue("7004778833");
        $("#userEmail").setValue("эмейл");
        $("#submit").scrollTo().click();
        $("#userEmail").shouldHave(Condition.cssValue("border-color", "rgb(220, 53, 69)"));
    }

    @Test
    void phoneWithOneDigitReturnsErrorTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#gender-radio-1").click();
        $("#userNumber").setValue("1");
        $("#submit").scrollTo().click();
        $("#userForm").shouldHave(cssClass("was-validated"));
        $(".modal-content").shouldNotBe(visible);
    }

    @Test
    void cityDependsOnStateTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("input[id='gender-radio-1'][value='Male']").click();
        $("[id=userNumber]").setValue("7004778833");
        $("#state").scrollTo().click();
        $("#state").$(byText("Haryana")).click();
        $("#city").scrollTo().click();
        $("#city").shouldNotHave(text("Delhi"));
        $("#city").$(byText("Karnal")).click();
        $("[id='submit'][type='submit']").click();
        $(".table-responsive").shouldBe(visible);
        $x("//td[text()='State and City']/following-sibling::td").shouldHave(text("Haryana Karnal"));
}
}

