/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;

public class ResidentialProperty extends Property {
    private int bedrooms;
    private boolean hasGarden;
    private boolean petFriendly;

    // Define Constructors
    public ResidentialProperty() {
        super();
        bedrooms = 0;
        hasGarden = false;
        petFriendly = false;
    }

    public ResidentialProperty(String id, String address, double pricing, String status, Owner ownerId) {
        super(id, address, pricing, status, ownerId);
        bedrooms = 0;
        hasGarden = false;
        petFriendly = false;
    }

    public ResidentialProperty(String id, String address, double pricing, String status, Owner ownerId,
                               int bedrooms, boolean hasGarden, boolean petFriendly) {
        super(id, address, pricing, status, ownerId);
        this.bedrooms = bedrooms;
        this.hasGarden = hasGarden;
        this.petFriendly = petFriendly;
    }

    // Getters and setters
    public int getBedrooms() {
        return bedrooms;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public boolean petFriendly() {
        return petFriendly;
    }

    @Override
    public String toString() {
        String result = "Residential Property {" + "\n" +
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

        result += "\tBedrooms: " + getBedrooms() + "\n" +
                "\tHas Garden: " + hasGarden() + "\n" +
                "\tPet Friendly: " + petFriendly() + "\n" +
                "}";

        return result;
    }
}