import listeners.Retry;
import org.apache.http.client.methods.HttpGet;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static interfaces.TestPriority.HIGH;
import static interfaces.TimeOut.SLOW;
import static org.testng.Assert.assertEquals;

public class Get200 extends BaseClass{

    @Test(priority = HIGH, timeOut = SLOW, description ="Verify inventory web service returns 200 status code")
    public void inventoryReturns200() throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/inventory");

        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 200);
    }


    @Test(priority = HIGH,  dataProvider = "validOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description ="Verify 200 status code for GET method in order to find purchase order by ID", retryAnalyzer= Retry.class)
    public void searchOrderByID200(String orderNumber) throws IOException {

        getStatusFotGetResponse(orderNumber, 200);
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
