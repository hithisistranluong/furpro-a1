/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;
import java.text.*;

public class Owner extends Person {
    private List<Property> ownedProperties;
    private List<Host> hosts;
    private List<RentalAgreement> rentalAgreements;

    // Define Constructors
    public Owner() {
        super();
        this.ownedProperties = new ArrayList<Property>();
        this.hosts = new ArrayList<Host>();
        this.rentalAgreements = new ArrayList<RentalAgreement>();
    }

    public Owner(String id, String fullName, String dobStr, String contact) throws ParseException {
        super(id, fullName, dobStr, contact);
        this.ownedProperties = new ArrayList<Property>();
        this.hosts = new ArrayList<Host>();
        this.rentalAgreements = new ArrayList<RentalAgreement>();
    }

    // Getters and setters
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }
    public void addOwnedProperties(Property Property) {
        this.ownedProperties.add(Property);
    }

    public List<Host> getHosts() {
        return hosts;
    }
    public void addHosts(Host Host) {
        this.hosts.add(Host);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }
    public void addRentalAgreements(RentalAgreement RentalAgreement) {
        this.rentalAgreements.add(RentalAgreement);
    }

    @Override
    public String toString() {
        String result = "Owner {" + "\n" +
                "\tId: " + getId() + "\n" +
                "\tName: " + getFullName() + "\n" +
                "\tDoB: " + getDob() + "\n" +
                "\tContact: " + getContact() + " \n";

        if (getOwnedProperties() != null) {
            result += "\tProperties: ";
            for (Property property : getOwnedProperties()) {
                if (property != null) {
                    result += property.getId() + ", ";
                }
            }
            result += "\n";
        }

        if (getHosts() != null) {
            result += "\tCooperating Hosts: ";
            for (Host host : getHosts()) {
                if (host != null) {
                    result += host.getFullName() + " (" + host.getId() + "), ";
                }
            }
            result += "\n";
        }

        if (getRentalAgreements() != null) {
            result += "\tRental Agreements: ";
            for (RentalAgreement rentalAgreement : getRentalAgreements()) {
                if (rentalAgreement != null) {
                    result += rentalAgreement.getAgreementId() + ", ";
                }
            }
            result += "\n";
        }

        result += "}";

        return result;
    }
}