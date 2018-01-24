package dao;

import models.Business;
import models.Charity;

import java.util.List;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oCharityDao implements CharityDao {

    @Override
    public void add(Charity charity) {

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
    public void deleteById(int id) {

    }
}
