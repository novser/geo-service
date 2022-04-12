import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImpTest {

    @ParameterizedTest
    @MethodSource("setHeaders")
    public void testSend(String ip, Country expect) {
        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        MessageSenderImpl sender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        Location location = new GeoServiceImpl().byIp(ip);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        sender.send(headers);

        Mockito.verify(localizationService, Mockito.times(2)).locale(expect);
    }

    static Stream<Arguments> setHeaders() {
        return Stream.of(
                Arguments.of("172.XXXXXX", Country.RUSSIA),
                Arguments.of("96.XXXXXX", Country.USA)
        );
    }

}
