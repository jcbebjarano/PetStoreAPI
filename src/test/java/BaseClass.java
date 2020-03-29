import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;
import org.testng.annotations.Test;



import java.io.IOException;

public class BaseClass {

    CloseableHttpClient client;
    CloseableHttpResponse response;

    protected static final String BASE_ENDPOINT = "https://petstore.swagger.io/v2/store";

    @BeforeMethod
    public void setup(Method testMethod){

        String desc = testMethod.getAnnotation(Test.class).description();
        System.out.println("Starting test: " +testMethod.getName() +
                " with description: " + desc);
        client  = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        client.close();
        response.close();
    }

}
