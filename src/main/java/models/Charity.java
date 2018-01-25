package models;

/**
 * Created by Guest on 1/24/18.
 */
public class Charity {
    private String charityName;
    private int id;

    public Charity(String charityName) {

        this.charityName = charityName;
    }

    public String getCharityName() {

        return charityName;
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

        Charity charity = (Charity) o;

        if (id != charity.id) return false;
        return charityName.equals(charity.charityName);
    }

    @Override
    public int hashCode() {
        int result = charityName.hashCode();
        result = 31 * result + id;
        return result;
    }
}
