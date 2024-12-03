/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;

public class Tenant extends Person {
    private List<RentalAgreement> rentalAgreements;
    private List<Payment> paymentRecords;
    private List<Property> rentalProperties;

    // Define Constructors
    public Tenant() {
        super();
        this.rentalAgreements = new ArrayList<RentalAgreement>();
        this.paymentRecords = new ArrayList<Payment>();
        this.rentalProperties = new ArrayList<Property>();
    }

    public Tenant(String id, String fullName, String dobStr, String contact) throws Exception {
        super(id, fullName, dobStr, contact);
        this.rentalAgreements = new ArrayList<RentalAgreement>();
        this.paymentRecords = new ArrayList<Payment>();
        this.rentalProperties = new ArrayList<Property>();
    }

    // Getters and setters
    public List<RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }
    public void addRentalAgreements(RentalAgreement rentalAgreement) {
        this.rentalAgreements.add(rentalAgreement);
    }

    public List<Payment> getPaymentTransactions() {
        return paymentRecords;
    }

    public List<Property> getRentalProperties() {
        return rentalProperties;
    }
    public void addRentalProperties(Property property) {
        this.rentalProperties.add(property);
    }

    @Override
    public String toString() {
        String result = "Tenant {" + "\n" +
                "\tId: " + getId() + "\n" +
                "\tName: " + getFullName() + "\n" +
                "\tDoB: " + getDob() + "\n" +
                "\tContact: " + getContact() + " \n";

        if (getRentalProperties() != null) {
            result += "\tProperties: ";
            for (Property property : getRentalProperties()) {
                if (property != null) {
                    result += property.getId() + ", ";
                }
            }
            result += "\n";
        }

        if (getPaymentTransactions() != null) {
            result += "\tPayment transactions: ";
            for (Payment payment : getPaymentTransactions()) {
                if (payment != null) {
                    result += payment.getId() + ", ";
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