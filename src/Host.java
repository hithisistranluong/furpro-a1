/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;
import java.text.*;

public class Host extends Person {
    private List<Property> managedProperties;
    private List<Owner> cooperatingOwners;
    private List<RentalAgreement> rentalAgreements;

    // Define Constructors
    public Host() {
        super();
        this.managedProperties = new ArrayList<Property>();
        this.cooperatingOwners = new ArrayList<Owner>();
        this.rentalAgreements = new ArrayList<RentalAgreement>();
    }

    public Host(String id, String fullName, String dobStr, String contact) throws ParseException {
        super(id, fullName, dobStr, contact);
        this.managedProperties = new ArrayList<Property>();
        this.cooperatingOwners = new ArrayList<Owner>();
        this.rentalAgreements = new ArrayList<RentalAgreement>();
    }

    // Getters and setters
    public List<Property> getManagedProperties() {
        return managedProperties;
    }
    public void addManagedProperties(Property managedProperty) {
        this.managedProperties.add(managedProperty);
    }

    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }
    public void addRentalAgreements(RentalAgreement rentalAgreement) {
        this.rentalAgreements.add(rentalAgreement);
    }

    public List<Owner> getCooperatingOwners() {
        return cooperatingOwners;
    }
    public void addCooperatingOwners(Owner cooperatingOwner) {
        this.cooperatingOwners.add(cooperatingOwner);
    }

    @Override
    public String toString() {
        String result = "Host {" + "\n" +
                "\tId: " + getId() + "\n" +
                "\tName: " + getFullName() + "\n" +
                "\tDoB: " + getDob() + "\n" +
                "\tContact: " + getContact() + " \n";

        if (getManagedProperties() != null) {
            result += "\tProperties: ";
            for (Property property : getManagedProperties()) {
                if (property != null) {
                    result += property.getId() + ", ";
                }
            }
            result += "\n";
        }

        if (getCooperatingOwners() != null) {
            result += "\tCooperating Owners: ";
            for (Owner owner : getCooperatingOwners()) {
                if (owner != null) {
                    result += owner.getFullName() + " (" + owner.getId() + "), ";
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