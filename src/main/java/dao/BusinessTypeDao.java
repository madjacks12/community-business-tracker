package dao;

import models.Business;
import models.BusinessType;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public interface BusinessTypeDao {

    //create
    void add(BusinessType businessType);
    public void addBusinessTypeToBusiness(BusinessType businessType, Business business);

    //read
    List<BusinessType> getAll();
    List<Business> getAllBusinessesForBusinessType(int id);

    //update
    void update(int id, String businessTypeName);

    //delete
    void deleteById(int id);

}
