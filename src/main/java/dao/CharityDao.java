package dao;

import models.Business;
import models.Charity;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public interface CharityDao {

    void add(Charity charity);
    void addCharityToBusiness(Charity charity, Business business);

    List<Charity> getAll();
    List<Business> getAllBusinessesForACharity(int id);


    void update(int id, String charityName);


    void deleteById(int id);
}
