package dao;

import models.Business;
import models.BusinessType;
import models.Charity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oCharityDao implements CharityDao {

    private final Sql2o sql2o;

    public Sql2oCharityDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(Charity charity) {
        String sql = "INSERT INTO charities (charityName) VALUES (:charityName)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(charity)
                    .executeUpdate()
                    .getKey();
            charity.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addCharityToBusiness(Charity charity, Business business) {
        String sql = "INSERT INTO businesses_charities (businessid, charityid) VALUES (:businessId, :charityId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("businessId", business.getId())
                    .addParameter("charityId", charity.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public List<Charity> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM charities")
                    .executeAndFetch(Charity.class);
        }
    }

    @Override
    public List<Business> getAllBusinessesForACharity(int charityId) {
        ArrayList<Business> businesses = new ArrayList<>();

        String joinQuery = "SELECT businessid FROM businesses_charities WHERE charityid = :charityId";
        try (Connection con = sql2o.open()) {
            List<Integer> allBusinessIds = con.createQuery(joinQuery)
                    .addParameter("charityId", charityId)
                    .executeAndFetch(Integer.class); //what is happening in the lines above?
            for (Integer businessId : allBusinessIds){
                String businessQuery = "SELECT * FROM businesses WHERE id = :businessId";
                businesses.add(
                        con.createQuery(businessQuery)
                                .addParameter("businessId", businessId)
                                .executeAndFetchFirst(Business.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return businesses;
    }

//    @Override
//    public void update(int id, String charityName) {
//
//    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from charities WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public Charity findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM charities WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Charity.class);
        }
    }
}
