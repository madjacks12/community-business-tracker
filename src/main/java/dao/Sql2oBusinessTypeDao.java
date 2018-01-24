package dao;

import models.Business;
import models.BusinessType;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oBusinessTypeDao implements BusinessTypeDao {

    private final Sql2o sql2o;

    public Sql2oBusinessTypeDao (Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(BusinessType businessType) {
    }

    @Override
    public void addBusinessTypeToBusiness(BusinessType businessType, Business business){
        String sql = "INSERT INTO businesses_businessTypes (businessid, businesstypeid) VALUES (:businessId, :businessTypeId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("businessId", business.getId())
                    .addParameter("businessTypeId", businessType.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<BusinessType> getAll() {
        return null;
    }

    @Override
    public List<Business> getAllBusinessesForBusinessType(int id) {
        return null;
    }

    @Override
    public void update(int id, String businessTypeName) {

    }

    @Override
    public void deleteById(int id) {

    }
}
