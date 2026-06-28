package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TextBoxTests extends TestBase {
    @BeforeEach
    void setUp() {
        open("https://demoqa.com/automation-practice-form");
    }

    @Test
    void fullFieldsFormTest() {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("#userEmail").setValue("harry.potter@gmail.com");
        $(byText("Male")).click();
        $("#userNumber").setValue("7778889999");
        $("#dateOfBirthInput").click();
        $("select.react-datepicker__month-select").selectOptionByValue("5");
        $("select.react-datepicker__year-select").selectOptionByValue("2003");
        $(".react-datepicker__day--015").click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("input[type='file']").uploadFile(new File("src/test/resources/kitten.jpg"));
        $("#currentAddress").setValue("4 Privet Drive, Little Whinging, Surrey");
        $("#state").scrollTo().click();
        $("#state").scrollTo().$(byText("NCR")).click();
        $("#city").click();
        $("#city").scrollTo().$(byText("Delhi")).click();
        $("#submit").scrollTo().click();
            $(".modal-content").shouldBe(visible);
            $(".table-responsive").shouldHave(
                    text("Harry Potter"),
                    text("harry.potter@gmail.com"),
                    text("2003")
            );
    }

    @Test
    void requiredFieldsFormTest () {
       $("#firstName").setValue("Harry");
       $("#lastName").setValue("Potter");
       $(byText("Male")).click();
       $("#userNumber").setValue("7004778833");
       $("#submit").scrollTo().click();
            $(".modal-content").shouldBe(visible);
    }

    @Test
    void invalidEmailTest () {
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
    void dependentStateCityDropdownValidationTest () {
        $("#firstName").setValue("Harry");
        $("#lastName").setValue("Potter");
        $("input[id='gender-radio-1'][value='Male']").click();
        $("[id=userNumber]").setValue("7004778833");
        $("#state").scrollTo().click();
        $(byText("Haryana")).click();
        $("#city").scrollTo().click();
        $("#city").shouldNotHave(text("Delhi"));
        $(byText("Karnal")).click();
        $("[id='submit'][type='submit']").click();
            $(".table-responsive").shouldBe(visible);
            $(".table-responsive").shouldHave(text("Karnal"));
}
}

