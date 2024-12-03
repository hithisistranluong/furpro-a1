/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;

public interface RentalManager {
    RentalManagement management = new RentalManagement();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Load initial data
            management.loadFiles();
            System.out.println("Data successfully loaded from files!");

            // Main menu loop
            boolean running = true;
            while (running) {
                showMainMenu();
                int choice = getIntInput();
                switch (choice) {
                    case 1 -> viewData();
                    case 2 -> addData();
                    case 3 -> updateData();
                    case 4 -> deleteData();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.println("Data saved successfully. Goodbye!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void showMainMenu() {
        System.out.println("\n--- Rental System Menu ---");
        System.out.println("1. View Data");
        System.out.println("2. Add Data");
        System.out.println("3. Update Data");
        System.out.println("4. Delete Data");
        System.out.println("5. Exit");
    }

    // View data options
    private static void viewData() {
        System.out.println("\n--- View Data Menu ---");
        System.out.println("1. View all Rental Agreements");
        System.out.println("2. View all Tenants (Default order)");
        System.out.println("3. View all Tenants (ID order)");
        System.out.println("4. View all Hosts (Default order)");
        System.out.println("5. View all Hosts (ID order)");
        System.out.println("6. View all Owners (Default order)");
        System.out.println("7. View all Owners (ID order)");
        System.out.println("8. View all Residential Properties (Default order)");
        System.out.println("9. View all Residential Properties (ID order)");
        System.out.println("10. View all Commercial Properties (Default order)");
        System.out.println("11. View all Commercial Properties (ID order)");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> viewByCategory();
            case 2 -> management.getTenants().values().forEach(System.out::println);
            case 3 -> management.getTenantsOrder().values().forEach(System.out::println);
            case 4 -> management.getHosts().values().forEach(System.out::println);
            case 5 -> management.getHostsOrder().values().forEach(System.out::println);
            case 6 -> management.getOwners().values().forEach(System.out::println);
            case 7 -> management.getOwnersOrder().values().forEach(System.out::println);
            case 8 -> management.getResidentialProperties().values().forEach(System.out::println);
            case 9 -> management.getResidentialPropertiesOrder().values().forEach(System.out::println);
            case 10 -> management.getCommercialProperties().values().forEach(System.out::println);
            case 11 -> management.getCommercialPropertiesOrder().values().forEach(System.out::println);
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void viewByCategory() {
        System.out.println("\n--- View By Category ---");
        System.out.println("1. View all Rental Agreements (Default order)");
        System.out.println("2. View all Rental Agreements (ID order)");
        System.out.println("3. View By Owner Name");
        System.out.println("4. View By Property Address");
        System.out.println("5. View By Status");
        int choice = getIntInput();
        scanner.nextLine();
        switch (choice) {
            case 1 -> management.getRentalAgreements().values().forEach(System.out::println);
            case 2 -> management.getRentalAgreementsOrder().values().forEach(System.out::println);
            case 3 -> {
                System.out.print("Enter Owner Name: ");
                String name = scanner.nextLine();
                management.getByOwnerName(name).values().forEach(System.out::println);
            }
            case 4 -> {
                System.out.print("Enter Property Address: ");
                String address = scanner.nextLine();
                management.getByPropertyAddress(address).values().forEach(System.out::println);
            }
            case 5 -> {
                System.out.print("Enter Status: ");
                String status = scanner.nextLine();
                management.getByStatus(RentalAgreement.RentalStatus.valueOf(status.toUpperCase())).values().forEach(System.out::println);
            }
        }
    }

    // Add data
    private static void addData() throws Exception {
        System.out.println("\n--- Add Data Menu ---");
        System.out.println("1. Add Rental Agreement");
        int choice = getIntInput();
        if (choice == 1) {
            addRentalAgreement();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Update data
    private static void updateData() {
        System.out.println("\n--- Update Data Menu ---");
        System.out.println("1. Update Rental Agreement");
        int choice = getIntInput();
        if (choice == 1) {
            updateRentalAgreement();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Delete data
    private static void deleteData() {
        System.out.println("\n--- Delete Data Menu ---");
        System.out.println("1. Delete Rental Agreement");
        int choice = getIntInput();
        if (choice == 1) {
            deleteRentalAgreement();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Method to save new rental agreements to CSV
    private static void saveAddData() {
        try {
            System.out.println("Saving new in progress...");

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Public/rental_agreement.csv", true));

            for (RentalAgreement agreement : management.getRentalAgreements().values()) {
                if (!isAgreementAlreadyInCSV(agreement.getAgreementId())) {
                    writer.write(formatAgreementForCSV(agreement, "add"));
                }
            }

            writer.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Method to save update to CSV
    private static void saveUpdateData(String agreementIdToUpdate) {
        try {
            System.out.println("Updating in progress...");

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Public/rental_agreement.csv"));

            boolean firstLine = true;
            for (RentalAgreement agreement : management.getRentalAgreements().values()) {
                if (firstLine) {
                    if (!agreement.getAgreementId().equals(agreementIdToUpdate)) {
                        writer.write(formatAgreementForCSV(agreement, "update"));
                    } else {
                        writer.write(formatAgreementForCSV(agreement, "update"));
                    }
                    firstLine = false;
                } else {
                    writer.newLine();
                    if (!agreement.getAgreementId().equals(agreementIdToUpdate)) {
                        writer.write(formatAgreementForCSV(agreement, "update"));
                    } else {
                        writer.write(formatAgreementForCSV(agreement, "update"));
                    }
                }
            }

            writer.close();
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    // Method to save deletion to csv
    private static void saveDeleteData(String agreementIdToDelete) {
        try {
            System.out.println("Deleting in progress...");

            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Public/rental_agreement.csv"));

            boolean firstLine = true;
            for (RentalAgreement agreement : management.getRentalAgreements().values()) {
                if (!agreement.getAgreementId().equals(agreementIdToDelete)) {
                    if (firstLine) {
                        writer.write(formatAgreementForCSV(agreement, "delete"));
                        firstLine = false;
                    } else {
                        writer.newLine();
                        writer.write(formatAgreementForCSV(agreement, "delete"));
                    }
                }
            }

            writer.close();
            System.out.println("Deletion successful. Data saved.");
        } catch (IOException e) {
            System.err.println("Error during deletion and saving data: " + e.getMessage());
        }
    }

    // Helper method to check if an agreement is already in the CSV file (based on agreement ID)
    private static boolean isAgreementAlreadyInCSV(String agreementId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Public/rental_agreement.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String existingAgreementId = line.split(",")[0];
                if (existingAgreementId.equals(agreementId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return false;
    }

    // Helper method to format a RentalAgreement into a CSV row
    private static String formatAgreementForCSV(RentalAgreement agreement, String type) {
        String agreementId = agreement.getAgreementId();
        String propertyId = agreement.getProperty().getId();
        String ownerId = agreement.getOwner().getId();
        String hostIds = String.join(";", agreement.getHost().stream().map(Host::getId).toArray(String[]::new));
        String mainTenantId = agreement.getMainTenant().getId();
        String subTenantIds = String.join(";", agreement.getSubTenants().stream().map(Tenant::getId).toArray(String[]::new));
        String period = agreement.getPeriod();
        String startDate = agreement.getStartDate();
        String endDate = agreement.getEndDate();
        String contractDate = agreement.getContractDate();
        double rent = agreement.getFee();
        String status = agreement.getRentalStatus().toString();

        if (type.equalsIgnoreCase("add")) {
            return String.format("\n%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s",
                    agreementId, propertyId, ownerId, hostIds, mainTenantId, subTenantIds,
                    period, startDate, endDate, contractDate, rent, status);
        }
        else {
            return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s",
                    agreementId, propertyId, ownerId, hostIds, mainTenantId, subTenantIds,
                    period, startDate, endDate, contractDate, rent, status);
        }
    }

    private static int getIntInput() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input.\n" + "Re-enter your choice: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void addRentalAgreement() throws Exception {
        System.out.print("Enter Agreement ID: ");
        String agreementId = scanner.next();

        // Check if this ID is already in use
        if (management.getRentalAgreements().containsKey(agreementId)) {
            System.out.println("Agreement ID " + agreementId + " already exists!");
            return;
        }

        System.out.print("Enter Property ID (from P1 to P20): ");
        String propertyId = scanner.next();
        while (!management.getResidentialProperties().containsKey(propertyId)) {
            System.out.println("Property ID " + propertyId + " not found!");
            System.out.print("Please re-enter a valid ID: ");
            propertyId = scanner.next();
        }

        System.out.print("Enter Owner ID (from Ow01 to Ow20): ");
        String ownerId = scanner.next();
        while (!management.getOwners().containsKey(ownerId)) {
            System.out.println("Owner ID " + ownerId + " not found!");
            System.out.print("Please re-enter a valid ID: ");
            ownerId = scanner.next();
        }

        System.out.print("Enter Host ID (from H1 to H20): ");
        String hostId = scanner.next();
        while (!management.getHosts().containsKey(hostId)) {
            System.out.println("Host ID " + hostId + " not found.");
            System.out.print("Please re-enter a valid ID: ");
            hostId = scanner.next();
        }

        System.out.print("Enter Main Tenant ID (from T1 to T20): ");
        String mainTenantId = scanner.next();
        while (!management.getTenants().containsKey(mainTenantId)) {
            System.out.println("Main Tenant ID " + mainTenantId + " not found.");
            System.out.print("Please re-enter a valid ID: ");
            mainTenantId = scanner.next();
        }

        System.out.print("Enter Rental Period (DAILY, WEEKLY, FORTNIGHTLY, MONTHLY): ");
        String period = scanner.next().toUpperCase();
        while (!Arrays.asList("DAILY", "WEEKLY", "FORTNIGHTLY", "MONTHLY").contains(period)) {
            System.out.println("Invalid rental period.");
            System.out.print("Please re-enter a valid period: ");
            period = scanner.next().toUpperCase();
        }

        LocalDate startDate = getValidatedDate("Enter Start Date (yyyy-MM-dd): ");
        LocalDate endDate = getValidatedDate("Enter End Date (yyyy-MM-dd): ");
        LocalDate contractDate = getValidatedDate("Enter Contract Date (yyyy-MM-dd): ");

        System.out.print("Enter Rent Amount: ");
        double rent = scanner.nextDouble();

        System.out.print("Enter Rental Status (NEW, ACTIVE, COMPLETED): ");
        String status = scanner.next().toUpperCase();
        while (!Arrays.asList("NEW", "ACTIVE", "COMPLETED").contains(status)) {
            System.out.println("Invalid rental status.");
            System.out.print("Please re-enter a valid status: ");
            status = scanner.next().toUpperCase();
        }

        // Retrieve objects for Property, Owner, Host, and Tenant
        Property property = management.getResidentialProperties().get(propertyId);
        Owner owner = management.getOwners().get(ownerId);
        Host host = management.getHosts().get(hostId);
        Tenant mainTenant = management.getTenants().get(mainTenantId);

        if (property == null || owner == null || host == null || mainTenant == null) {
            System.out.println("Cannot create rental agreement. Missing either Property, Owner, Host, or Tenant.");
        } else {
            RentalAgreement rentalAgreement = new RentalAgreement(
                    agreementId, property, owner, mainTenant, period,
                    startDate.toString(), endDate.toString(), contractDate.toString(),
                    rent, status
            );
            rentalAgreement.addHosts(host);
            management.getRentalAgreements().put(agreementId, rentalAgreement);
            management.getRentalAgreementsOrder().put(agreementId, rentalAgreement);
            saveAddData();
            System.out.println("Rental Agreement added successfully!");
        }
    }

    // Update rental agreement
    private static void updateRentalAgreement() {
        System.out.print("Enter Agreement ID to update: ");
        String agreementId = scanner.next();
        RentalAgreement agreement = management.getRentalAgreements().get(agreementId);

        if (agreement == null) {
            System.out.println("Rental Agreement not found.");
            return;
        }

        System.out.println("Updating Rental Agreement: " + agreementId);
        System.out.println("Type \"No\" to keep the current value.");

        System.out.print("Enter new Rent Amount (current: " + agreement.getFee() + "): ");
        String newRent = scanner.next();
        if (!newRent.isEmpty()) {
            try {
                if (!newRent.equalsIgnoreCase("no")) {
                    double rent = Double.parseDouble(newRent);
                    agreement.setFee(rent);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid rent amount. Update aborted.");
                return;
            }
        }

        System.out.print("Enter new Rental Status (NEW, ACTIVE, COMPLETED): ");
        String newStatus = scanner.next();
        if (!newStatus.isEmpty() && !newStatus.equalsIgnoreCase("no")) {
            agreement.setRentalStatus(newStatus.toUpperCase());
        }

        saveUpdateData(agreementId);
        System.out.println("Rental Agreement " + agreementId + " updated successfully!");
    }

    // Delete a Rental Agreement
    private static void deleteRentalAgreement() {
        System.out.print("Enter Agreement ID to delete: ");
        String agreementId = scanner.next();

        if (!management.getRentalAgreements().containsKey(agreementId)) {
            System.out.println("Agreement ID " + agreementId + " not found!");
        } else {
            management.getRentalAgreements().remove(agreementId);
            management.getRentalAgreementsOrder().remove(agreementId);
            System.out.println("Agreement ID " + agreementId + " deleted successfully!");

            saveDeleteData(agreementId);
        }
    }

    // Check date format
    private static LocalDate getValidatedDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = null;

        while (date == null) {
            System.out.print(prompt);
            String input = scanner.next();
            try {
                date = LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in yyyy-MM-dd format.");
            }
        }
        return date;
    }
}