package dao;

import models.Business;
import models.BusinessType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;

import org.sql2o.Connection;

import static org.junit.Assert.*;


public class Sql2oBusinessTypeDaoTest {

    private Connection conn;
    private Sql2oBusinessTypeDao businessTypeDao;
    private Sql2oBusinessDao businessDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        businessTypeDao = new Sql2oBusinessTypeDao(sql2o);
        businessDao = new Sql2oBusinessDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void add() throws Exception {
        BusinessType testBusinessType = setUpBusinessType();
        businessTypeDao.add(testBusinessType);
        assertEquals(1, testBusinessType.getId());
    }

    @Test
    public void getAll() throws Exception {
        BusinessType testBusinessType = setUpBusinessType();
        BusinessType testBusinessTypeOther = setUpBusinessTypeOther();
        businessTypeDao.add(testBusinessType);
        businessTypeDao.add(testBusinessTypeOther);
        assertEquals(2, businessTypeDao.getAll().size());
    }

    @Test
    public void deleteById() throws Exception {
        BusinessType testBusinessType = setUpBusinessType();
        businessTypeDao.add(testBusinessType);
        businessTypeDao.deleteById(testBusinessType.getId());
        assertEquals(0, businessTypeDao.getAll().size());
    }

    @Test
    public void addBusinessTypeToBusiness() throws Exception {
        Business testBusiness = setUpBusiness();
        Business testBusinessOther = setUpBusinessOther();
        businessDao.add(testBusiness);
        businessDao.add(testBusinessOther);
        BusinessType testBusinessType = setUpBusinessType();
        businessTypeDao.add(testBusinessType);

        businessDao.addBusinessToBusinessType(testBusiness, testBusinessType);
        businessDao.addBusinessToBusinessType(testBusinessOther, testBusinessType);

        assertEquals(2, businessTypeDao.getAllBusinessesForBusinessType(testBusinessType.getId()).size());

    }

    @Test
    public void getAllBusinessesForBusinessType() throws Exception {
        Business testBusiness = setUpBusiness();
        Business testBusinessOther = setUpBusinessOther();
        businessDao.add(testBusiness);
        businessDao.add(testBusinessOther);
        BusinessType testBusinessType = setUpBusinessType();
        businessTypeDao.add(testBusinessType);
        businessDao.addBusinessToBusinessType(testBusiness, testBusinessType);
        businessDao.addBusinessToBusinessType(testBusinessOther, testBusinessType);

        assertEquals(2, businessTypeDao.getAllBusinessesForBusinessType(testBusinessType.getId()).size());
    }



    public BusinessType setUpBusinessType() {
        return new BusinessType("Restaurant");
    }

    public BusinessType setUpBusinessTypeOther() {
        return new BusinessType("Store");
    }

    public Business setUpBusiness() {
        return new Business("BusinessName", "address", "email", "phone");
    }

    public Business setUpBusinessOther() {
        return new Business("OtherName", "OtherAddress", "OtherEmail", "OtherPhone");
    }


}