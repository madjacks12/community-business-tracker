package models;

/**
 * Created by Guest on 1/24/18.
 */
public class Business {
    private String businessName;
    private String address;
    private String email;
    private String phone;
    private int id;

    public Business(String businessName, String address, String email, String phone) {
        this.businessName = businessName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        if (id != business.id) return false;
        if (!businessName.equals(business.businessName)) return false;
        if (!address.equals(business.address)) return false;
        if (email != null ? !email.equals(business.email) : business.email != null) return false;
        return phone != null ? phone.equals(business.phone) : business.phone == null;
    }

    @Override
    public int hashCode() {
        int result = businessName.hashCode();
        result = 31 * result + address.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}
