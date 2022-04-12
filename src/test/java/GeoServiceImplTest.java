import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {


    @ParameterizedTest
    @MethodSource("ipAndLocation")
    public void test_byIp(String ip, Location expect) {
        GeoServiceImpl geoService = new GeoServiceImpl();

        Location result = geoService.byIp(ip);

        try {
            Assertions.assertEquals(expect, result);
        } catch (NullPointerException e) {
            Assertions.assertEquals(expect.getCity(), result.getCity());
            Assertions.assertEquals(expect.getCountry(), result.getCountry());
        }
    }

    static Stream<Arguments> ipAndLocation() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, new Location(null, null, null, 0)),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.", new Location("New York", Country.USA, null, 0)),
                Arguments.of("XXX.X.X.X", null)
        );
    }
}
