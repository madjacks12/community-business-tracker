package dao;

import models.Business;
import models.Charity;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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

    }

    @Override
    public List<Charity> getAll() {
        return null;
    }

    @Override
    public List<Business> getAllBusinessesForACharity(int id) {
        return null;
    }

    @Override
    public void update(int id, String charityName) {

    }

    @Override
    public void deleteById(int id) {

    }

}
