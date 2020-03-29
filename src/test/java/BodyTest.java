import listeners.Retry;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.Test;
import entities.NotFound;
import java.io.IOException;
import org.testng.asserts.SoftAssert;
import  entities.Order;

import static interfaces.TestPriority.HIGH;
import static interfaces.TimeOut.SLOW;


public class BodyTest extends BaseClass {




    @Test(priority = HIGH, dataProvider = "validOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = SLOW, description ="Validate response body values", retryAnalyzer= Retry.class)
    public void returnsCorrectOrderBodyResponse(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();


        Order orderValue = getValueFor(orderNumber, Order.class);

        // Assert
        System.out.println("First assert Order ID");
        sa.assertEquals(orderValue.getId(), 1);
        System.out.println("Second assert Pet Id");
        sa.assertEquals(orderValue.getPetId(), 1);
        System.out.println("Third assert Quantity");
        sa.assertEquals(orderValue.getQuantity(), 5);
        System.out.println("Fourth assert Ship Date");
        sa.assertEquals(orderValue.getShipDate(), "2020-03-20T21:39:50.300+0000");
        System.out.println("Fifth assert Status");
        sa.assertEquals(orderValue.getStatus(), "placed");
        System.out.println("Sixth assert Complete");
        sa.assertTrue(orderValue.getComplete());

        sa.assertAll();
    }

    @Test(dataProvider = "OrderNotFound", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*not found", timeOut = SLOW, description ="Validate not found message in GET order responde body")
    public void notFoundMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueFor(orderNumber, NotFound.class);

        System.out.println("First assert Type");
        sa.assertEquals(notFoundValue.getType(), "error");
        System.out.println("Second assert Message");
        sa.assertEquals(notFoundValue.getMessage(), "Order not found");

        sa.assertAll();
    }

    @Test(dataProvider = "invalidOrderNumber", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*Invalid ID", timeOut = SLOW, description ="Validate Invalid ID supplied message in GET order responde body")
    public void invalidIDSuppliedMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueFor(orderNumber, NotFound.class);

        System.out.println("First assert Type");
        sa.assertEquals(notFoundValue.getType(), "error");
        System.out.println("Second assert Message");
        sa.assertEquals(notFoundValue.getMessage(), "Invalid ID supplied");

        sa.assertAll();
    }

    @Test(dataProvider = "OrderNotFound", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*Not found", timeOut = SLOW, description ="Validate not found message in DELETE order responde body", retryAnalyzer= Retry.class)
    public void deleteNotFoundMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueForDelete(orderNumber, NotFound.class);

        System.out.println("First assert Type");
        sa.assertEquals(notFoundValue.getType(), "error");
        System.out.println("Second assert Message");
        sa.assertEquals(notFoundValue.getMessage(), "Order Not Found");

        sa.assertAll();
    }

    @Test(dataProvider = "invalidOrderNumber", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*Not found", timeOut = SLOW, description ="Validate invalid ID supplied message in DELETE order responde body", retryAnalyzer= Retry.class)
    public void deleteInvalidIDMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueForDelete(orderNumber, NotFound.class);

        System.out.println("First assert Type");
        sa.assertEquals(notFoundValue.getType(), "error");
        System.out.println("Second assert Message");
        sa.assertEquals(notFoundValue.getMessage(), "Invalid ID supplied");

        sa.assertAll();
    }

    private <T> T getValueFor(String orderNumber, Class<T> clazz) throws IOException{

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/order/"+ orderNumber);

        response = client.execute(get);

        T classValue = ResponseUtils.unmarshallGeneric(response, clazz);

        return classValue;
    }

    private <T> T getValueForDelete(String orderNumber, Class<T> clazz) throws IOException{

        HttpDelete get = new HttpDelete(BASE_ENDPOINT + "/order/"+ orderNumber);

        response = client.execute(get);

        T classValue = ResponseUtils.unmarshallGeneric(response, clazz);

        return classValue;
    }


}
