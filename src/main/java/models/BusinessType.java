package models;

/**
 * Created by Guest on 1/24/18.
 */
public class BusinessType {
    private String businessTypeName;
    private int id;

    public BusinessType(String businessTypeName) {

        this.businessTypeName = businessTypeName;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
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

        BusinessType that = (BusinessType) o;

        if (id != that.id) return false;
        return businessTypeName.equals(that.businessTypeName);
    }

    @Override
    public int hashCode() {
        int result = businessTypeName.hashCode();
        result = 31 * result + id;
        return result;
    }
}
