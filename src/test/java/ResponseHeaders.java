import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

import static interfaces.TestPriority.HIGH;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ResponseHeaders extends BaseClass {



    @Test(priority = HIGH, dataProvider = "endpoints", dataProviderClass = CommonApiDataProviders.class,  description = "Validate response header content type is Json")
    public void contentTypeIsJson(String endpoints) throws IOException {

       response = getResponse(endpoints);

       Header contentType = response.getEntity().getContentType();
       assertEquals(contentType.getValue(), "application/json");

       ContentType ct = ContentType.getOrDefault(response.getEntity());
       assertEquals(ct.getMimeType(), "application/json");
    }

    @Test(priority = HIGH, dataProvider = "endpoints", dataProviderClass = CommonApiDataProviders.class,  description = "Validate response header server is Jetty")
    public void serverIsJetty(String endpoints) throws IOException {

        response = getResponse(endpoints);

        String headerValue = ResponseUtils.getHeader(response, "Server");

        assertEquals(headerValue, "Jetty(9.2.9.v20150224)");
    }

    @Test(priority = HIGH, dataProvider = "endpoints", dataProviderClass = CommonApiDataProviders.class,  description = "Validate response header date field is present")
    public void dateIsPresent(String endpoints) throws IOException {

        response = getResponse(endpoints);

        boolean dateIsPresent = ResponseUtils.headerIsPresent(response, "date");

        assertTrue(dateIsPresent);

    }

    public CloseableHttpResponse getResponse(String endpoints) throws IOException {

        HttpGet request = new HttpGet(BASE_ENDPOINT + endpoints);

        response = client.execute(request);

        return response;
    }


}
