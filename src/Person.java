/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;
import java.text.*;

public class Person {
    public String id;
    public String fullName;
    public Date dob;
    public String contact;

    // Define Constructors
    public Person() {
        this.id = null;
        this.fullName = null;
        this.dob = null;
        this.contact = null;
    }

    public Person(String id, String fullName, String dobStr, String contact) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = df.parse(dobStr);

        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.contact = contact;
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Date getDob() {
        return dob;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Person {" + "\n" +
                "\tId: " + getId() + "\n" +
                "\tName: " + getFullName() + "\n" +
                "\tDoB: " + getDob() + "\n" +
                "\tContact: " + getContact() + " \n" +
                "}";
    }
}