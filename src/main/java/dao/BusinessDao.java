package dao;

import models.Business;
import models.BusinessType;
import models.Charity;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public interface BusinessDao {


    //create
    void add (Business business); //J
    void addBusinessToCharity(Business business, Charity charity); //D & E
    void addBusinessToBusinessType(Business business, BusinessType businessType);

    //read
    List<Business> getAll(); //A
    List<Charity> getAllCharitiesForABusiness(int businessId); //D & E - we will implement this soon.

    Business findById(int id); //B & C

    //update
    void update(int id, String businessName, String address, String email, String phone); //L

    //delete
    void deleteById(int id); //K

}
