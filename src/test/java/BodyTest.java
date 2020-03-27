import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import entities.NotFound;
import java.io.IOException;

import  entities.Order;
import static org.testng.Assert.assertEquals;

public class BodyTest extends BaseClass {

    @DataProvider
    private Object[][] orderNumber(){
        //For valid response try integer IDs with positive integer value. Negative or non-integer values will generate API errors
        return new Object[][]{
                {1}
        };
    }


    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderId(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order idValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(idValue.getId(), 1);
    }
    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderPetid(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order petidValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(petidValue.getPetId(), 1);
    }
    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderQuantity(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order quantityValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(quantityValue.getQuantity(), 1);
    }
    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderShipdate(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order shipdateValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(shipdateValue.getShipDate(), 1);
    }
    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderStatus(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order statusValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(statusValue.getStatus(), 1);
    }
    @Test(dataProvider = "orderNumber")
    public void returnsCorrectOrderComplete(Integer orderNumber) throws IOException {

        response = getValueFor(orderNumber);

        Order completeValue = ResponseUtils.unmarshallGeneric(response, Order.class);

        assertEquals(completeValue.getComplete(), 1);
    }


    @Test
    public void notFoundMessageIsCorrect() throws IOException {

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/order/10");

        response = client.execute(get);

        NotFound notFoundMessage = ResponseUtils.unmarshallGeneric(response, NotFound.class);

        assertEquals(notFoundMessage.getMessage(), "Order not found");
    }

    private CloseableHttpResponse getValueFor(Integer orderNumber) throws IOException{

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/order/"+ orderNumber);

        response = client.execute(get);

        return response;
    }


}
