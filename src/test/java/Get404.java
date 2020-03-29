import org.apache.http.client.methods.HttpGet;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.io.IOException;

import static interfaces.TestPriority.HIGH;
import static interfaces.TimeOut.SLOW;
import static org.testng.Assert.assertEquals;

public class Get404 extends BaseClass{

    @Test(timeOut = SLOW, description ="Verify 404 status code for GET method for a nonexisting URL")
    public void nonExistingUrlReturns404() throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/nonexistingurl");

        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 404);
    }

    @Test(dataProvider = "OrderNotFound", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description ="Verify 404 status code for GET method for order not found")
    public void searchOrderByID404(String orderNumber) throws IOException {

        getStatusFotGetResponse(orderNumber, 404);
    }

    @Test(dataProvider = "invalidOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description ="Verify 400 status code for GET method with Invalid ID supplied")
    public void searchOrderByID400(String orderNumber) throws IOException {

        getStatusFotGetResponse(orderNumber, 400);
    }

    public void getStatusFotGetResponse(String orderNumber, int expectedStatusCode) throws IOException{

        HttpGet request = new HttpGet(BASE_ENDPOINT + "/order/" + orderNumber);

        response = client.execute(request);
        int actualStatusCode = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatusCode, expectedStatusCode);

        if(actualStatusCode != expectedStatusCode){
            throw new SkipException("Basic criteria failed," +
                    "was expecting code "+expectedStatusCode+", but got: " + actualStatusCode);
        }
    }
}
