package entities;

public class Order {

    // For tests using JsonObject

    public static final String ID = "id";
    public static final String PETID = "petId";
    public static final String QUANTITY = "quantity";
    public static final String SHIPDATE = "shipDate";
    public static final String STATUS = "status";
    public static final String COMPLETE = "complete";


    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    private String status;
    private Boolean complete;


    public int getId() {
        return id;
    }

    public int getPetId() {
        return petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getComplete() {
        return complete;
    }
}
