import org.apache.http.Header;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ResponseHeaders extends BaseClass {


    @DataProvider
    private Object[][] endpoints(){
        return new Object[][]{
                {"/inventory"},
                {"/order/1"}
        };
    }
    @Test(dataProvider = "endpoints")
    public void contentTypeIsJson(String endpoints) throws IOException {

       HttpGet get = new HttpGet(BASE_ENDPOINT + endpoints);

       response = client.execute(get);

       Header contentType = response.getEntity().getContentType();
       assertEquals(contentType.getValue(), "application/json");

       ContentType ct = ContentType.getOrDefault(response.getEntity());
       assertEquals(ct.getMimeType(), "application/json");
    }

    @Test(dataProvider = "endpoints")
    public void serverIsJetty(String endpoints) throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + endpoints);

        response = client.execute(get);

        String headerValue = ResponseUtils.getHeader(response, "Server");

        assertEquals(headerValue, "Jetty(9.2.9.v20150224)");
    }

    @Test
    public void dateIsPresent() throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        boolean dateIsPresent = ResponseUtils.headerIsPresent(response, "date");

        assertTrue(dateIsPresent);

    }

}
