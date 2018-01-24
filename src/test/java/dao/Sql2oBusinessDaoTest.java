package dao;

import models.Business;
import models.BusinessType;
import models.Charity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

/**
 * Created by Guest on 1/24/18.
 */
public class Sql2oBusinessDaoTest {

    private Connection conn;
    private Sql2oBusinessDao businessDao;
    private Sql2oBusinessTypeDao businessTypeDao;
    private Sql2oCharityDao charityDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        businessDao = new Sql2oBusinessDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingBusinessSetsId() throws Exception {
        Business testBusiness = setupBusiness();
        int originalBusinessId = testBusiness.getId();
        businessDao.add(testBusiness);
        assertEquals(1, testBusiness.getId());
    }

    @Test
    public void getAll() throws Exception {
        Business testBusinessOne = setupBusiness();
        Business testBusinessTwo = setupBusiness();
        businessDao.add(testBusinessOne);
        businessDao.add(testBusinessTwo);
        assertEquals(2, businessDao.getAll().size());
    }

    @Test
    public void findById() throws Exception {
        Business testBusinessOne = setupBusiness();
        Business testBusinessTwo = setupBusiness();
        Business testBusinessThree = setupBusiness();
        businessDao.add(testBusinessOne);
        businessDao.add(testBusinessTwo);
        businessDao.add(testBusinessThree);
        assertEquals(4, businessDao.findById(3).getId());
    }


    @Test
    public void addBusinessToCharity() throws Exception {
        Business testBusinessOne = setupBusiness();
        Business testBusinessTwo = setupBusinessAlt();

        businessDao.add(testBusinessOne);
        businessDao.add(testBusinessTwo);

        Charity testCharity = setupCharity();

        charityDao.add(testCharity);

        charityDao.addCharityToBusiness(testCharity, testBusinessOne);
        charityDao.addCharityToBusiness(testCharity, testBusinessTwo);

        assertEquals(2, charityDao.getAllBusinessesForACharity(testCharity.getId()).size());
    }

    @Test
    public void addBusinessToBusinessType() throws Exception {
        Business testBusinessOne = setupBusiness();
        Business testBusinessTwo = setupBusinessAlt();

        businessDao.add(testBusinessOne);
        businessDao.add(testBusinessTwo);

        BusinessType testBusinessType = setupBusinessType();

        businessTypeDao.add(testBusinessType);

        businessTypeDao.addBusinessTypeToBusiness(testBusinessType, testBusinessOne);
        businessTypeDao.addBusinessTypeToBusiness(testBusinessType, testBusinessTwo);

        assertEquals(2, businessTypeDao.getAllBusinessesForBusinessType(testBusinessType.getId()).size());
    }

    @Test
    public void getAllCharitiesForABusiness() throws Exception {
        Business testBusinessOne = setupBusiness();
        Business testBusinessTwo = setupBusinessAlt();

        businessDao.add(testBusinessOne);
        businessDao.add(testBusinessTwo);

        Charity testCharity = setupCharity();

        charityDao.add(testCharity);

        charityDao.addCharityToBusiness(testCharity, testBusinessOne);
        charityDao.addCharityToBusiness(testCharity, testBusinessTwo);

        assertEquals(2, charityDao.getAllBusinessesForACharity(testCharity.getId()).size());
    }


    @Test
    public void deleteById() throws Exception {
    }

    public Business setupBusiness() {
        return new Business("BusinessName", "address", "email", "phone");
    }

    public Business setupBusinessAlt() {
        return new Business("NewBusiness", "NewAddress", "NewEmail", "NewPhone");
    }

    public Charity setupCharity() {
        return new Charity("CharityName");
    }
    public BusinessType setupBusinessType() {
        return new BusinessType("businesstype");
    }
}