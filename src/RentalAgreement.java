/**
 * @author Luong Ngoc Bao Tran - s4031723
 */

import java.util.*;
import java.text.*;

public class RentalAgreement {
    public enum RentalPeriod {
        DAILY,
        WEEKLY,
        FORTNIGHTLY,
        MONTHLY
    }

    public enum RentalStatus {
        NEW,
        ACTIVE,
        COMPLETED
    }

    private String id;
    private Property property;
    private Owner owner;
    private List<Host> host;
    private Tenant mainTenant;
    private List<Tenant> subTenants;
    private RentalPeriod period;
    private Date startDate;
    private Date endDate;
    private Date contractDate;
    private double fee;
    private RentalStatus status;

    // Define Constructions
    public RentalAgreement(String id, Property property, Owner owner, Tenant mainTenant,
                           String period, String startDateStr, String endDateStr, String contractDateStr,
                           double fee, String status) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = df.parse(startDateStr);
        Date endDate = df.parse(endDateStr);
        Date contractDate = df.parse(contractDateStr);

        this.id = id;
        this.property = property;
        this.owner = owner;
        this.host = new ArrayList<Host>();
        this.mainTenant = mainTenant;
        this.subTenants = new ArrayList<Tenant>();
        this.period = RentalPeriod.valueOf(period.toUpperCase());
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractDate = contractDate;
        this.fee = fee;
        this.status = RentalStatus.valueOf(status.toUpperCase());
    }

    // Getters and setters
    public String getAgreementId() {
        return id;
    }

    public Property getProperty() {
        return property;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Host> getHost() {
        return host;
    }
    public void addHosts(Host host) {
        this.host.add(host);
    }

    public Tenant getMainTenant() {
        return mainTenant;
    }
    public void setMainTenant(Tenant mainTenant) {
        this.mainTenant = mainTenant;
    }

    public List<Tenant> getSubTenants() {
        return subTenants;
    }
    public void addSubTenants(Tenant subTenant) {
        this.subTenants.add(subTenant);
    }

    public String getPeriod() {
        return period.toString();
    }

    public String getStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(startDate);
    }

    public String getEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(endDate);
    }

    public String getContractDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(contractDate);
    }

    public RentalStatus getRentalStatus() {
        return status;
    }
    public void setRentalStatus(String status) {
        this.status = RentalStatus.valueOf(status);
    }

    public double getFee() {
        return fee;
    }
    public void setFee(double fee) {
        this.fee = fee;
    };

    @Override
    public String toString() {
        String result = "Rental Agreement {\n\tID: " + getAgreementId() + "\n" +
                "\tAddress: " + property.getAddress() + "\n" +
                "\tFee: " + getFee() + "\n" +
                "\tStart Date: " + startDate + "\n" +
                "\tEnd Date: " + endDate + "\n" +
                "\tOwner: " + owner.getFullName() + " (" + owner.getId() + ")\n";

        if (getHost() != null) {
            result += "\tHosts: ";
            for (Host host : getHost()) {
                if (host != null) {
                    result += host.getFullName() + " (" + host.getId() + "), ";
                }
            }
            result += "\n";
        }

        result += "\tTenant: " + mainTenant.getFullName() + " (" + mainTenant.getId() + ")\n";

        if (getSubTenants() != null) {
            result += "\tSub-Tenants: ";
            for (Tenant tenant : getSubTenants()) {
                if (tenant != null) {
                    result += tenant.getFullName() + " (" + tenant.getId() + "), ";
                }
            }
            result += "\n";
        }

        result += "\tStatus: " + status + "\n" +
                "}";

        return result;
    }
}