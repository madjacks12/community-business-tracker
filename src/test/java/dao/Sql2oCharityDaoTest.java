package dao;

import models.Business;
import models.BusinessType;
import models.Charity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import static org.junit.Assert.*;

/**
 * Created by Guest on 1/25/18.
 */
public class Sql2oCharityDaoTest {

    private Connection conn;
    private Sql2oCharityDao charityDao;
    private Sql2oBusinessDao businessDao;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        charityDao = new Sql2oCharityDao(sql2o);
        businessDao = new Sql2oBusinessDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() throws Exception {
        Charity testCharity = setupCharity();
        charityDao.add(testCharity);
        assertEquals(1, testCharity.getId());
    }

    @Test
    public void addCharityToBusiness() throws Exception {
        Business testBusiness = setupBusiness();
        Business testBusinessOther = setupBusinessAlt();
        businessDao.add(testBusiness);
        businessDao.add(testBusinessOther);
        Charity testCharity = setupCharity();
        charityDao.add(testCharity);

        businessDao.addBusinessToCharity(testBusiness, testCharity);
        businessDao.addBusinessToCharity(testBusinessOther, testCharity);

        assertEquals(3, charityDao.getAllBusinessesForACharity(testCharity.getId()).size());
    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void getAllBusinessesForACharity() throws Exception {

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
        return new BusinessType("businessTypeName");
    }
}

