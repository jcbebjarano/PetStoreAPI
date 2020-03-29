import org.testng.annotations.DataProvider;


public class CommonApiDataProviders {

    @DataProvider
    private Object[][] validPostJson(){
        //For valid response try integer IDs with positive integer value.
        return new Object[][]{
                { "{\"id\": 1,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}
        };
    }

    @DataProvider
    private Object[][] invalidPostJson(){
        // Negative or non-integer values will generate API errors
        return new Object[][]{
                {"{\"id\": 1,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //duplicated ID
                {"{\"id\": ,\"petId\": ,\"quantity\": ,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //without ID
                {"{\"id\": -12,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //Negative id
                {"{\"id\": 3.5,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"2020-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"} //non-integer id
        };
    }

    @DataProvider
    private Object[][] invalidPostJson2(){
        return new Object[][]{
                {"{\"id\": 2,\"petId\": 1,\"quantity\": 5,\"shipDate\": \"-03-20T21:39:50.300Z\",\"status\": \"placed\",\"complete\": true }"}, //Wrong date format
                {"{\"petId\": 1,\"shipDate\": \"-03-20T21:39:50.300Z\",\"complete\": true }"} // Incomplete data
        };
    }

    @DataProvider
    private Object[][] validOrderNumber(){
        //For valid response try integer IDs with positive integer value.
        return new Object[][]{
                {"1"},
        };
    }

    @DataProvider
    private Object[][] invalidOrderNumber(){
        // Negative or non-integer values will generate API errors
        return new Object[][]{
                {"-1"},
                {"0"},
                {"3.5"},
                {"11"}
        };
    }

    @DataProvider
    private Object[][] OrderNotFound(){
        return new Object[][]{
                {"1"}
        };
    }

    @DataProvider
    private Object[][] endpoints(){
        return new Object[][]{
                {"/inventory"},
                {"/order/1"}
        };
    }
}
