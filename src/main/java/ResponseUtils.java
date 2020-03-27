import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Order;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ResponseUtils {


    public static String getHeader(CloseableHttpResponse response, String headerName) {

        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

        Header matchedHeader = httpHeaders.stream()
                                .filter(header -> headerName.equalsIgnoreCase(header.getName()))
                                .findFirst().orElseThrow(() -> new RuntimeException("Didn't find the header" + headerName));

        return matchedHeader.getValue();
    }

    public static boolean headerIsPresent(CloseableHttpResponse response, String headerName) {
        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());

        return httpHeaders.stream()
                .anyMatch(header -> header.getName().equalsIgnoreCase(headerName));
    }


    public static <T> T unmarshallGeneric(CloseableHttpResponse response, Class<T> clazz) throws IOException {

        String jsonBody = EntityUtils.toString(response.getEntity());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(jsonBody, clazz);
    }
}
