package tests;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextBoxTests extends TestBase {
    @BeforeEach
    void openTextBox() {
        open("/text-box");
  }
    @Test
    void allFieldsFilledSuccessfullyTest() {
        $("#userName").setValue("Harry Potter");
        $("#userEmail").setValue("harry.potter@gmail.com");
        $("#currentAddress").setValue("4 Privet Drive, Little Whinging, Surrey");
        $("#permanentAddress").setValue("Hogwarts School of Witchcraft and Wizardry");
        $("#submit").scrollTo().click();
        $("#output").shouldBe(visible);
        $("#output").shouldBe(visible);
        $("#output #name").shouldHave(text("Harry Potter"));
        $("#output #email").shouldHave(text("harry.potter@gmail.com"));
        $("#output #currentAddress").shouldHave(text("4 Privet Drive, Little Whinging, Surrey"));
        $("#output #permanentAddress").shouldHave(text("Hogwarts School of Witchcraft and Wizardry"));

    }
    @Test
    void nameWithSpecialCharactersIsSavedTest() {
        $("#userName").setValue(". ");
        $("#submit").scrollTo().click();
        $("#output").shouldBe(visible);
        $("#output #name").shouldHave(text("Name:. "));

    }
    @Test
    void onlyNameFilledSuccessfullyTest() {
        $("#userName").setValue("Harry Potter");
        $("#submit").scrollTo().click();
        $("#output").shouldBe(visible);
        $("#output #name").shouldHave(text("Name:Harry Potter"));
        $("#output").shouldNotHave(text("Email:"));
        $("#output").shouldNotHave(text("Current Address :"));
    }

    @Test
    void invalidEmailShowsMsgTest() {
        $("#userEmail").setValue("invalid-email.com");
        $("#submit").scrollTo().click();
        $("#userEmail").shouldHave(cssClass("field-error"));
        $("#output").shouldNotBe(visible);
    }
}
