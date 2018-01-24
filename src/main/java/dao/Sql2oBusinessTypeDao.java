package dao;

import models.Business;
import models.BusinessType;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oBusinessTypeDao implements BusinessTypeDao {

    @Override
    public void add(BusinessType businessType) {

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
