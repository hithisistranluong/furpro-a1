/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;

public class CommercialProperty extends Property {
    private String businessType;
    private int parkingSpaces;
    private double squareFootage;

    // Define Constructors
    public CommercialProperty() {
        super();
        this.businessType = "Commercial";
        this.parkingSpaces = 0;
        this.squareFootage = 0.0;
    }

    public CommercialProperty(String id, String address, double pricing, String status, Owner ownerId) {
        super(id, address, pricing, status, ownerId);
        this.businessType = "Commercial";
        this.parkingSpaces = 0;
        this.squareFootage = 0.0;
    }

    public CommercialProperty(String id, String address, double pricing, String status, Owner owner,
                              String businessType, int parkingSpaces, double squareFootage) {
        super(id, address, pricing, status, owner);
        this.businessType = businessType;
        this.parkingSpaces = parkingSpaces;
        this.squareFootage = squareFootage;
    }

    // Getters and setters
    public String getBusinessType() {
        return businessType;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public double getSquareFootage() {
        return squareFootage;
    }

    @Override
    public String toString() {
        String result = "Commercial Property {" + "\n" +
                "\tId: " + getId() + "\n" +
                "\tAddress: " + getAddress() + "\n" +
                "\tPricing: " + getPrice() + "\n" +
                "\tStatus: " + getStatus() + " \n";

        if (getOwner() != null) {
            result += "\tOwner: " + getOwner().getFullName() + " (" + getOwner().getId() + ")\n";
        }

        if (getHosts() != null) {
            result += "\tHosts: ";
            for (Host host : getHosts()) {
                if (host != null) {
                    result += host.getFullName() + " (" + host.getId() + "), ";
                }
            }
            result += "\n";
        }

        result += "\tBussiness Type: " + getBusinessType() + "\n" +
                "\tParking Spaces: " + getParkingSpaces() + "\n" +
                "\tSquare Footage: " + getSquareFootage() + "\n" +
                "}";

        return result;
    }
}