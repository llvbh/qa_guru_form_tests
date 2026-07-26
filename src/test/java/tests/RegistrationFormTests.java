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
        $x("//td[text()='Student Name']/following-sibling::td").shouldHave(text("Harry Potter"));
        $x("//td[text()='Student Email']/following-sibling::td").shouldHave(text("harry.potter@gmail.com"));
        $x("//td[text()='Gender']/following-sibling::td").shouldHave(text("Male"));
        $x("//td[text()='Mobile']/following-sibling::td").shouldHave(text("7778889999"));
        $x("//td[text()='Date of Birth']/following-sibling::td").shouldHave(text("15 June,2003"));
        $x("//td[text()='Subjects']/following-sibling::td").shouldHave(text("Maths"));
        $x("//td[text()='Hobbies']/following-sibling::td").shouldHave(text("Reading"));
        $x("//td[text()='Picture']/following-sibling::td").shouldHave(text("kitten.jpg"));
        $x("//td[text()='Address']/following-sibling::td").shouldHave(text("4 Privet Drive, Little Whinging, Surrey"));
        $x("//td[text()='State and City']/following-sibling::td").shouldHave(text("NCR Delhi"));
    }

    @Test
    void requiredFieldsSubmitSuccessfullyTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#genterWrapper").$(byText("Male")).click();
        $("#userNumber").setValue("7004778833");
        $("#submit").scrollTo().click();
        $(".modal-content").shouldBe(visible);
        $x("//td[text()='Student Name']/following-sibling::td").shouldHave(text("Harry Potter"));
        $x("//td[text()='Gender']/following-sibling::td").shouldHave(text("Male"));
        $x("//td[text()='Mobile']/following-sibling::td").shouldHave(text("7004778833"));
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

