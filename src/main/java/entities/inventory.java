package entities;

public class inventory {

    public static final String SOLD = "sold";
    public static final String STRING = "string";
    public static final String NONAVAILABLE = "Nonavailable";
    public static final String PENDING = "pending";
    public static final String AVALIABLE = "available";

    private int sold;
    private int string;
    private int Nonavailable;
    private int pending;
    private int available;

    public int getsold() {
        return sold;
    }

    public int getstring() {
        return string;
    }

    public int getNonavailable() {
        return Nonavailable;
    }

    public int getpending() {
        return pending;
    }

    public int getavailable() {
        return available;
    }

}
