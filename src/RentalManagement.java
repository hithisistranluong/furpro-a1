/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.io.*;
import java.text.*;
import java.util.*;

public class RentalManagement {
    private final Map<String, RentalAgreement> rentalAgreements = new HashMap<>();
    private final Map<String, RentalAgreement> rentalAgreementsOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(2)))
    );

    private final Map<String, RentalAgreement> byOwnerName = new HashMap<>();
    private final Map<String, RentalAgreement> byPropertyAddress = new HashMap<>();
    private final Map<String, RentalAgreement> byStatus = new HashMap<>();

    private final Map<String, Tenant> tenants = new HashMap<>();
    private final Map<String, Tenant> tenantsOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(1)))
    );

    private final Map<String, Host> hosts = new HashMap<>();
    private final Map<String, Host> hostsOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(1)))
    );

    private final Map<String, Owner> owners = new HashMap<>();
    private final Map<String, Owner> ownersOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(2)))
    );

    private final Map<String, ResidentialProperty> residentialProperties = new HashMap<>();
    private final Map<String, ResidentialProperty> residentialPropertiesOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(1)))
    );

    private final Map<String, CommercialProperty> commercialProperties = new HashMap<>();
    private final Map<String, CommercialProperty> commercialPropertiesOrder = new TreeMap<>(
            Comparator.comparingInt(id -> Integer.parseInt(id.substring(1)))
    );


    // Load all data from files
    public void loadFiles() throws Exception {
        loadTenants();
        loadHosts();
        loadOwners();
        loadProperties();
        loadRentalAgreements();
    }

    // Load Tenants from CSV
    private void loadTenants() throws Exception {
        System.out.println("Loading tenants...");
        BufferedReader reader = new BufferedReader(new FileReader("src/Public/tenant.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Tenant tenant = new Tenant(data[0], data[1], data[2], data[3]);

            // Process rental agreements (data[4])
            if (!data[4].isEmpty()) {
                String[] rentalAgreementIds = data[4].split(";");
                for (String agreementId : rentalAgreementIds) {
                    RentalAgreement agreement = rentalAgreements.get(agreementId);
                    if (agreement != null) {
                        tenant.addRentalAgreements(agreement);
                    }
                }
            }

            // Process rental properties (data[6])
            if (!data[6].isEmpty()) {
                String[] managedPropertyIds = data[6].split(";");
                for (String propertyId : managedPropertyIds) {
                    Property property = residentialProperties.get(propertyId);
                    if (property == null) {
                        property = commercialProperties.get(propertyId);
                    }
                    if (property != null) {
                        tenant.addRentalProperties(property);
                    }
                }
            }

            // Add the tenant to the tenants map
            tenants.put(tenant.getId(), tenant);
            tenantsOrder.put(tenant.getId(), tenant);
        }
        reader.close();
        System.out.println("Done Loading tenants...");
    }

    // Load Hosts from CSV
    private void loadHosts() throws IOException, ParseException {
        System.out.println("Loading hosts...");
        BufferedReader reader = new BufferedReader(new FileReader("src/Public/host.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Host host = new Host(data[0], data[1], data[2], data[3]);

            // Process managed properties (data[4])
            if (!data[4].isEmpty()) {
                String[] managedPropertyIds = data[4].split(";");
                for (String propertyId : managedPropertyIds) {
                    Property property = residentialProperties.get(propertyId);
                    if (property == null) {
                        property = commercialProperties.get(propertyId);
                    }
                    if (property != null) {
                        host.addManagedProperties(property);
                    }
                }
            }

            // Process rental agreements (data[5])
            if (!data[5].isEmpty()) {
                String[] rentalAgreementIds = data[5].split(";");
                for (String agreementId : rentalAgreementIds) {
                    RentalAgreement agreement = rentalAgreements.get(agreementId);
                    if (agreement != null) {
                        host.addRentalAgreements(agreement);
                    }
                }
            }

            // Process cooperating owners (data[6])
            if (!data[6].isEmpty()) {
                String[] cooperatingHostIds = data[6].split(";");
                for (String cooperatingHostId : cooperatingHostIds) {
                    Owner owner = owners.get(cooperatingHostId);
                    if (owner != null) {
                        host.addCooperatingOwners(owner);
                    }
                }
            }

            // Add the host to the hosts map
            hosts.put(host.getId(), host);
            hostsOrder.put(host.getId(), host);
        }
        reader.close();
        System.out.println("Done Loading hosts...");
    }

    // Load Owners from CSV
    private void loadOwners() throws IOException, ParseException {
        System.out.println("Loading owners...");
        BufferedReader reader = new BufferedReader(new FileReader("src/Public/owner.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Owner owner = new Owner(data[0], data[1], data[2], data[3]);

            // Process owned properties (data[4])
            if (!data[4].isEmpty()) {
                String[] managedPropertyIds = data[4].split(";");
                for (String propertyId : managedPropertyIds) {
                    Property property = residentialProperties.get(propertyId);
                    if (property == null) {
                        property = commercialProperties.get(propertyId);
                    }
                    if (property != null) {
                        owner.addOwnedProperties(property);
                    }
                }
            }

            // Process cooperating hosts (data[5])
            if (!data[5].isEmpty()) {
                String[] hostIds = data[5].split(";");
                for (String hostId : hostIds) {
                    Host host = hosts.get(hostId.substring(1));
                    owner.addHosts(host);
                }
            }

            // Process rental agreements (data[6])
            if (!data[6].isEmpty()) {
                String[] rentalAgreementIds = data[6].split(";");
                for (String agreementId : rentalAgreementIds) {
                    RentalAgreement agreement = rentalAgreements.get(agreementId);
                    if (agreement != null) {
                        owner.addRentalAgreements(agreement);
                    }
                }
            }

            owners.put(owner.getId(), owner);
            ownersOrder.put(owner.getId(), owner);
        }
        reader.close();
        System.out.println("Done Loading owners...");
    }

    // Load Properties from CSV
    private void loadProperties() throws IOException {
        System.out.println("Loading properties...");
        BufferedReader reader = new BufferedReader(new FileReader("src/Public/property.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String propertyId = data[0];
            String address = data[1];
            double price = Double.parseDouble(data[2]);
            String status = data[3];
            String ownerId = data[4];
            Owner owner = owners.get(ownerId);
            String hostIds = data[5];
            Host host;

            // Residential Property
            if (data[6] != null && !data[6].isEmpty()) {
                int bedrooms = Integer.parseInt(data[6]);
                boolean hasGarden = data[7].equalsIgnoreCase("yes");
                boolean petFriendly = data[8].equalsIgnoreCase("yes");

                ResidentialProperty residentialProperty = new ResidentialProperty(
                        propertyId, address, price, status, owner,
                        bedrooms, hasGarden, petFriendly
                );

                if (hostIds != null && ownerId != null) {
                    for (String hostId : hostIds.split(";")) {
                        host = hosts.get(hostId);

                        residentialProperty.addHost(host);
                        residentialProperty.setOwner(owner);

                        host.addManagedProperties(residentialProperty);
                        owner.addOwnedProperties(residentialProperty);

                        host.addCooperatingOwners(owner);
                        owner.addHosts(host);
                    }
                }

                // Add properties to maps
                residentialProperties.put(residentialProperty.getId(), residentialProperty);
                residentialPropertiesOrder.put(residentialProperty.getId(), residentialProperty);
            }
            // Commercial Property
            else {
                String businessType = data[9];
                int parkingSpaces = Integer.parseInt(data[10]);
                double squareFootage = Double.parseDouble(data[11]);

                CommercialProperty commercialProperty = new CommercialProperty(
                        propertyId, address, price, status, owner,
                        businessType, parkingSpaces, squareFootage
                );

                if (hostIds != null && ownerId != null) {
                    for (String hostId : hostIds.split(";")) {
                        host = hosts.get(hostId);

                        commercialProperty.addHost(host);
                        commercialProperty.setOwner(owner);

                        host.addManagedProperties(commercialProperty);
                        owner.addOwnedProperties(commercialProperty);

                        host.addCooperatingOwners(owner);
                        owner.addHosts(host);
                    }
                }

                // Add properties to maps
                commercialProperties.put(commercialProperty.getId(), commercialProperty);
                commercialPropertiesOrder.put(commercialProperty.getId(), commercialProperty);
            }
        }
        reader.close();
        System.out.println("Done Loading properties...");
    }

    // Load Rental Agreements from CSV
    private void loadRentalAgreements() throws IOException, ParseException {
        System.out.println("Loading rental agreements...");
        BufferedReader reader = new BufferedReader(new FileReader("src/Public/rental_agreement.csv"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String agreementId = data[0];
            String propertyId = data[1];
            String ownerId = data[2];
            String hostIds = data[3];
            String mainTenantId = data[4];
            String subTenantIds = data[5];
            String period = data[6];
            String startDate = data[7];
            String endDate = data[8];
            String contractDate = data[9];
            double rent = Double.parseDouble(data[10]);
            String status = data[11];

            // Retrieve corresponding data
            Property property = residentialProperties.get(propertyId);
            if (property == null) {
                property = commercialProperties.get(propertyId);
            }
            Owner owner = owners.get(ownerId);
            Tenant mainTenant = tenants.get(mainTenantId);

            if (property != null && owner != null && hostIds != null && mainTenant != null) {
                RentalAgreement agreement = new RentalAgreement(
                        agreementId, property, owner, mainTenant,
                        period, startDate, endDate, contractDate, rent, status
                );

                String[] hostId = hostIds.split(";");
                for (String hId : hostId) {
                    Host host = hosts.get(hId);

                    agreement.addHosts(host);
                    agreement.setOwner(owner);

                    host.addRentalAgreements(agreement);
                    owner.addRentalAgreements(agreement);
                }

                agreement.setMainTenant(mainTenant);
                mainTenant.addRentalProperties(property);

                String[] subTenantId = subTenantIds.split(";");
                for (String Id : subTenantId) {
                    Tenant subTenant = tenants.get(Id);
                    if (subTenant != null) {
                        agreement.addSubTenants(subTenant);
                        subTenant.addRentalAgreements(agreement);
                        subTenant.addRentalProperties(property);
                    }

                    mainTenant.addRentalAgreements(agreement);
                }

                rentalAgreements.put(agreement.getAgreementId(), agreement);
                rentalAgreementsOrder.put(agreement.getAgreementId(), agreement);
            }
        }
        reader.close();
        System.out.println("Done Loading rental agreements...");
    }

    // Methods to retrieve all Rental Agreements
    public Map<String, RentalAgreement> getRentalAgreements() {
        return rentalAgreements;
    }
    public Map<String, RentalAgreement> getRentalAgreementsOrder() {
        return rentalAgreementsOrder;
    }

    // Method to retrieve Rental Agreements by owner's name
    public Map<String, RentalAgreement> getByOwnerName(String ownerName) {
        for (RentalAgreement agreement : rentalAgreements.values()) {
            if (agreement.getOwner().getFullName().equalsIgnoreCase(ownerName)) {
                byOwnerName.put(agreement.getAgreementId(), agreement);
            }
        }
        return byOwnerName;
    }

    // Method to retrieve Rental Agreements by property address
    public Map<String, RentalAgreement> getByPropertyAddress(String propertyAddress) {
        for (RentalAgreement agreement : rentalAgreements.values()) {
            if (agreement.getProperty().getAddress().equalsIgnoreCase(propertyAddress)) {
                byPropertyAddress.put(agreement.getAgreementId(), agreement);
            }
        }
        return byPropertyAddress;
    }

    // Method to retrieve Rental Agreements by rental status
    public Map<String, RentalAgreement> getByStatus(RentalAgreement.RentalStatus status) {
        for (RentalAgreement agreement : rentalAgreements.values()) {
            if (agreement.getRentalStatus() == status) {
                byStatus.put(agreement.getAgreementId(), agreement);
            }
        }
        return byStatus;
    }

    // Methods to retrieve all Tenants
    public Map<String, Tenant> getTenants() {
        return tenants;
    }
    public Map<String, Tenant> getTenantsOrder() {
        return tenantsOrder;
    }

    // Methods to retrieve all Hosts
    public Map<String, Host> getHosts() {
        return hosts;
    }
    public Map<String, Host> getHostsOrder() {
        return hosts;
    }

    // Methods to retrieve all Owners
    public Map<String, Owner> getOwners() {
        return owners;
    }
    public Map<String, Owner> getOwnersOrder() {
        return ownersOrder;
    }

    // Methods to retrieve all Residential Properties
    public Map<String, ResidentialProperty> getResidentialProperties() {
        return residentialProperties;
    }
    public Map<String, ResidentialProperty> getResidentialPropertiesOrder() {
        return residentialPropertiesOrder;
    }

    // Methods to retrieve all Commercial Properties
    public Map<String, CommercialProperty> getCommercialProperties() {
        return commercialProperties;
    }
    public Map<String, CommercialProperty> getCommercialPropertiesOrder() {
        return commercialPropertiesOrder;
    }
}