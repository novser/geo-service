import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    @ParameterizedTest
    @MethodSource("data")
    public void test_locale(Country country, String expect) {
        LocalizationServiceImpl testObj = new LocalizationServiceImpl();

        String result = testObj.locale(country);

        Assertions.assertEquals(expect, result);

    }

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome")
        );
    }

}
