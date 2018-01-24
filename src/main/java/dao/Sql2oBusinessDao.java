package dao;

import models.Business;
import models.Charity;
import models.BusinessType;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oBusinessDao implements BusinessDao {

    private final Sql2o sql2o;

    public Sql2oBusinessDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Business business) {
        String sql = "INsERT INTO businesses (businessName, address, phone, email) VALUES (:businessName, :address, :phone, :email)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(business)
                    .executeUpdate()
                    .getKey();
            business.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Business> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM businesses")
                    .executeAndFetch(Business.class);
        }
    }

    @Override
    public void addBusinessToCharity(Business business, Charity charity) {
        String sql = "INSERT INTO businesses_charities (businessId, charityId) VALUES (:businessId, :charityId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("businessId", business.getId())
                    .addParameter("charityId", charity.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addBusinessToBusinessType(Business business, BusinessType businessType) {
        String sql = "INSERT INTO businesses_businessTypes (businessId, businessTypeId) VALUES (:businessId, :businessTypeId)";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("businessId", business.getId())
                    .addParameter("businessTypeId", businessType.getId())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
    @Override
    public List<Charity> getAllCharitiesForABusiness(int businessId) {
        ArrayList<Charity> charities = new ArrayList<>();

        String joinQuery = "SELECT charityid FROM businesses_charities WHERE businessid = :businessid";

        try (Connection con = sql2o.open()) {
            List<Integer> allCharitiesIds = con.createQuery(joinQuery)
                    .addParameter("businessId", businessId)
                    .executeAndFetch(Integer.class);
            for (Integer charityId : allCharitiesIds){
                String charityQuery = "SELECT * FROM charities WHERE id = :charityId";
                charities.add(
                        con.createQuery(charityQuery)
                        .addParameter("charityId", charityId)
                        .executeAndFetchFirst(Charity.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return charities;
    }

    @Override
    public Business findById(int id) {
        String sql = "SELECT * FROM businesses WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Business.class);
        }

    }

    @Override
    public void update(int id, String name, String address, String email, String phone) {
        String sql = "UPDATE businesses SET name = :name, address = :address, phone = :phone";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                .addParameter("name", name)
                .addParameter("address", address)
                .addParameter("phone", phone)
                .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM businesses WHERE id = :id";
        String deleteJoin = "DELETE from businesses_charities WHERE businessid = :businessId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("restaurantId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}



//String sql = ""
//try(Connection con = sql2o.open())