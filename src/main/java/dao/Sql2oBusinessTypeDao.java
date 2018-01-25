package dao;

import models.Business;
import models.BusinessType;
import org.sql2o.Sql2o;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
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
        String sql = "INSERT INTO businessTypes (businessTypeName) VALUES (:businessTypeName)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .bind(businessType)
                    .executeUpdate()
                    .getKey();
            businessType.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
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
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM businessTypes")
                    .executeAndFetch(BusinessType.class);
        }
    }

    @Override
    public List<Business> getAllBusinessesForBusinessType(int businessTypeid) {
        ArrayList<Business> businesses = new ArrayList<>();

        String joinQuery = "SELECT businessid FROM businesses_businessTypes WHERE businessTypeid = :businessTypeid";

        try (Connection con = sql2o.open()) {
            List<Integer> allBusinessIds = con.createQuery(joinQuery)
                    .addParameter("businessTypeid", businessTypeid)
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

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from businessTypes WHERE id = :id";
        String deleteJoin = "DELETE from businesses_businessTypes WHERE businesstypeid = :businesstypeId";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("businesstypeId", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public BusinessType findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM businessTypes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(BusinessType.class);
        }
    }

}
