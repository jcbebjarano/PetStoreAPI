import org.apache.http.client.methods.HttpOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class Options204 extends BaseClass {

    @Test(timeOut = 1000, description = "Function to test only the response header content.")
    public void optionsReturnsCorrectMethodsList() throws IOException {

        String header = "Access-Control-Allow-Methods";
        String expectedReply = "GET, POST, DELETE, PUT";

        HttpOptions request = new HttpOptions(BASE_ENDPOINT);
        response = client.execute(request);

        String actualValue = ResponseUtils.getHeader(response, header);

        Assert.assertEquals(actualValue, expectedReply);
    }
}
