import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.testng.Assert.assertEquals;

public class DeleteAndPost extends BaseClass {


    @DataProvider
    private Object[][] postJson(){
        //For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors
        return new Object[][]{
                { "{\"id\": 1,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}
        };
    }
    @Test(dataProvider = "postJson")
    public void createOrder200(String postJson)throws IOException {

        // Create an HttpPost with a valid Endpoint
        HttpPost request = new HttpPost(BASE_ENDPOINT + "/order");


        // Define Json to Post and set as Entity
        request.setEntity(new StringEntity(postJson, ContentType.APPLICATION_JSON));

        // Send
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatusCode, 200);
    }

    @DataProvider
    private Object[][] postJson2(){
        //For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors
        return new Object[][]{
                {"{\"id\": -12,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //Wrong id
                {"{\"id\": 2,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //Wrong date format
                {"{\"petId\": 1,\"shipDate\": \"-03-20T21:39:50.300Z\",\"complete\": true }"} // Incomplete data
        };
    }
    @Test(dataProvider = "postJson2")
    public void createOrder500(String postJson2)throws IOException {

        // Create an HttpPost with a valid Endpoint
        HttpPost request = new HttpPost(BASE_ENDPOINT + "/order");


        // Define Json to Post and set as Entity
        request.setEntity(new StringEntity(postJson2, ContentType.APPLICATION_JSON));

        // Send
        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(actualStatusCode, 500);
    }


    @DataProvider
    private Object[][] orderNumber(){
        //For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors
        return new Object[][]{
                {1},
                {-1}
        };
    }
    @Test(dataProvider = "orderNumber")
    public void deleteIsSuccessful(Integer orderNumber) throws IOException {

        HttpDelete request = new HttpDelete(BASE_ENDPOINT + "/order/" + orderNumber);

        response = client.execute(request);

        int actualStatusCode = response.getStatusLine().getStatusCode();

        if (orderNumber >= 1 && orderNumber <= 10 ){
            assertEquals(actualStatusCode, 200);
        }else {
            assertEquals(actualStatusCode, 404); // NEGATIVE TEST
        }
    }

}
