import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class Get200 extends BaseClass{

    @Test
    public void inventoryReturns200() throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/inventory");

        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 200);
    }

    @DataProvider
    private Object[][] orderNumber(){
        return new Object[][]{
                //For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions
                {1},
                {11} // invalid value
        };
    }
    @Test(dataProvider = "orderNumber")
    public void searchOrderByID200(Integer orderNumber) throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/order/" + orderNumber);

        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        if (orderNumber >= 1 && orderNumber <= 10 ){
            assertEquals(actualStatus, 200);
        }else {
            assertEquals(actualStatus, 404); // NEGATIVE TEST
        }


    }
}
