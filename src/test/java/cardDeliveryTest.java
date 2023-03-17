import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SetValueOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class cardDeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldRequest() {
        LocalDate date = LocalDate.now();
        date = date.plusDays(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id=date] input").setValue(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        $("[data-test-id=name] input").setValue("Петров Олег");
        $("[data-test-id=phone] input").setValue("+79965898790");
        $("[data-test-id=agreement]").click();
        String planningDate = generateDate(3);
        $("[data-test-id=date] input").setValue(planningDate);
        $$("button").find(exactText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(14));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
