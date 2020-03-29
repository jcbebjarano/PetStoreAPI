import org.apache.http.client.methods.HttpGet;
import org.testng.annotations.Test;
import entities.NotFound;
import java.io.IOException;
import org.testng.asserts.SoftAssert;
import  entities.Order;

import static interfaces.TestPriority.HIGH;
import static org.testng.Assert.assertEquals;

public class BodyTest extends BaseClass {




    @Test(priority = HIGH, dependsOnGroups = "POST", dataProvider = "validOrderNumber", dataProviderClass = CommonApiDataProviders.class, timeOut = 1000, description ="Validate response body values")
    public void returnsCorrectOrderBodyResponse(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();


        Order orderValue = getValueFor(orderNumber, Order.class);

        // Assert
        System.out.println("First assert Order ID");
        sa.assertEquals(orderValue.getId(), 1);
        System.out.println("Second assert Pet Id");
        sa.assertEquals(orderValue.getPetId(), 1);
        System.out.println("Third assert Quantity");
        sa.assertEquals(orderValue.getQuantity(), 1);
        System.out.println("Fourth assert Ship Date");
        sa.assertEquals(orderValue.getShipDate(), 1);
        System.out.println("Fifth assert Status");
        sa.assertEquals(orderValue.getStatus(), 1);
        System.out.println("Sixth assert Complete");
        sa.assertEquals(orderValue.getComplete(), 1);

        sa.assertAll();
    }

    @Test(dependsOnGroups = "DELETE", dataProvider = "OrderNotFound", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*not found", timeOut = 1000, description ="Validate not found message in GET order responde body")
    public void notFoundMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueFor(orderNumber, NotFound.class);

        System.out.println("First assert Type");
        sa.assertEquals(notFoundValue.getType(), "error");
        System.out.println("Second assert Message");
        sa.assertEquals(notFoundValue.getMessage(), "Order not found");

        sa.assertAll();
    }

    @Test(dataProvider = "invalidOrderNumber", dataProviderClass = CommonApiDataProviders.class, expectedExceptionsMessageRegExp = ".*Invalid ID", timeOut = 1000, description ="Validate Invalid ID supplied message in GET order responde body")
    public void invalidIDSuppliedMessageIsCorrect(String orderNumber) throws IOException {

        SoftAssert sa = new SoftAssert();

        NotFound notFoundValue = getValueFor(orderNumber, NotFound.class);

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


}
