import listeners.Retry;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import static interfaces.TestPriority.*;
import static interfaces.TimeOut.*;


public class DeleteAndPost extends BaseClass {


    @Test(groups = "POST", priority = HIGH, dataProvider = "validPostJson", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify place an order for a pet 200 status code by POST method", retryAnalyzer= Retry.class)
    public void createOrder200(String postJson)throws IOException {

        getStatusForPOSTResponse(postJson, 200);

    }

    @Test(dataProvider = "invalidPostJson", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify place an invalid order 400 status code, CASE_1: Order with duplicated ID / CASE_2: Order without ID / CASE_3: Order with negative id / CASE_4: Order with non-integer id", retryAnalyzer= Retry.class)
    public void createInvalidOrder400(String postJson)throws IOException {

        getStatusForPOSTResponse(postJson, 400);

    }


    @Test(dataProvider = "invalidPostJson2", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify place an invalid order 500 status code,  CASE_1: Order with wrong date format / CASE_2: Order with incomplete data", retryAnalyzer= Retry.class)
    public void createOrder500(String postJson)throws IOException {

        getStatusForPOSTResponse(postJson, 500);

    }



    @Test(groups = "DELETE", priority = HIGH, dataProvider = "validOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify delete an order 200 status code by DELETE method", retryAnalyzer= Retry.class)
    public void deleteIsSuccessful(String orderNumber) throws IOException {

        getStatusForDeleteResponse(orderNumber,  200);

    }

    @Test(dataProvider = "invalidOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify delete an order 400 status code with invalid ID supplied, CASE_1: Negative ID / CASE_2: 0 ID / CASE_3: non-integer", retryAnalyzer= Retry.class)
    public void deleteInvalidIDSupplied(String orderNumber) throws IOException {

        getStatusForDeleteResponse(orderNumber, 400);

    }

    @Test(/*dependsOnGroups = {"POST","DELETE"},*/ dataProvider = "OrderNotFound", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description = "Verify delete an order 404 status code when the order not found", retryAnalyzer= Retry.class)
    public void deleteOrderNotFound(String orderNumber) throws IOException {

        getStatusForDeleteResponse(orderNumber, 404);

    }

    private void getStatusForPOSTResponse(String postJson, int expectedStatusCode) throws IOException{

        // Create an HttpPost with a valid Endpoint
        HttpPost request = new HttpPost(BASE_ENDPOINT + "/order");

        // Define Json to Post and set as Entity
        request.setEntity(new StringEntity(postJson, ContentType.APPLICATION_JSON));

        // Send
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();

        if(actualStatusCode != expectedStatusCode){
            throw new IOException("Basic criteria failed," +
                    "was expecting code "+expectedStatusCode+", but got: " + actualStatusCode);
        }

        Assert.assertEquals(actualStatusCode, expectedStatusCode);


    }

    private void getStatusForDeleteResponse(String orderNumber, int expectedStatusCode) throws IOException {

        HttpDelete request = new HttpDelete(BASE_ENDPOINT + "/order/" + orderNumber);

        response = client.execute(request);
        int actualStatusCode = response.getStatusLine().getStatusCode();

        if(actualStatusCode != expectedStatusCode){
            throw new IOException("Basic criteria failed," +
                    "was expecting code "+expectedStatusCode+", but got: " + actualStatusCode);
        }

        Assert.assertEquals(actualStatusCode, expectedStatusCode);

    }


}
