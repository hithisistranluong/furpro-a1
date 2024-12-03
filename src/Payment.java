/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;
import java.text.*;

public class Payment {
    private String id;
    private Tenant tenant;
    private double amount;
    private Date date;
    private String method;

    public Payment() {
        this.id = null;
        this.tenant = null;
        this.amount = 0.0;
        this.date = null;
        this.method = null;
    }
    public Payment(String id, Tenant tenant, double amount, String dateStr, String method) throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date = df.parse(dateStr);

        this.id = id;
        this.tenant = tenant;
        this.amount = amount;
        this.date = date;
        this.method = method;
    }

    // Getters
    public String getId() {
        return id;
    }
}