package tests;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class SecondFormTests extends TestBase {

  @BeforeEach
    void setUp() {
        open("https://demoqa.com/text-box");
  }
    @Test
    void allFieldsFormTest() {
        $("#userName").setValue("Harry Potter");
        $("#userEmail").setValue("harry.potter@gmail.com");
        $("#currentAddress").setValue("4 Privet Drive, Little Whinging, Surrey");
        $("#permanentAddress").setValue("Hogwarts School of Witchcraft and Wizardry");
        $("#submit").scrollTo().click();

        $("#output").shouldBe(visible);
        $("#output").shouldHave(
            text("Harry Potter"),
            text("harry.potter@gmail.com"),
            text("4 Privet Drive, Little Whinging, Surrey"),
            text("Hogwarts School of Witchcraft and Wizardry")
    );
    }
    @Test
    void specialCharactersNameFormTest() {
        $("#userName").setValue(". ");
        $("#submit").scrollTo().click();

        $("#output").shouldBe(visible);
        $("#output").shouldHave(
                text("Name:. ")
        );
    }
    @Test
    void requiredFileldFormTest() {
        $("#userName").setValue("Harry Potter");
        $("#submit").scrollTo().click();$("#output").shouldBe(visible);

        $("#output").shouldHave(text("Name:Harry Potter"));
        $("#output").shouldNotHave(text("Email:"), text("Current Address :"));
    }

    @Test
    void shouldShowErrorWhenEmailIsInvalid() {
        $("#userEmail").setValue("invalid-email.com");
        $("#submit").scrollTo().click();

        $("#userEmail").shouldHave(cssClass("field-error"));
        $("#output").shouldNotBe(visible);
    }

}