import com.google.gson.Gson;
import com.sun.media.sound.AiffFileReader;
import models.*;
import dao.Sql2oBusinessTypeDao;
import dao.Sql2oBusinessDao;
import dao.Sql2oCharityDao;
import org.sql2o.Connection;
import exceptions.ApiException;
import org.sql2o.Sql2o;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oBusinessTypeDao businessTypeDao;
        Sql2oBusinessDao businessDao;
        Sql2oCharityDao charityDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        businessDao = new Sql2oBusinessDao(sql2o);
        businessTypeDao = new Sql2oBusinessTypeDao(sql2o);
        charityDao = new Sql2oCharityDao(sql2o);
        conn = sql2o.open();

        //c
        post("/businesses/new", "application/json", (request, response) ->  {
            Business business = gson.fromJson(request.body(), Business.class);
            businessDao.add(business);
            response.status(201);
            return gson.toJson(business);
        });

        get("/businesses", "application/json", (request, response) -> {
            return gson.toJson(businessDao.getAll());
        });

        post("/charities/new", "application/json", (request, response) ->  {
            Charity charity = gson.fromJson(request.body(), Charity.class);
            charityDao.add(charity);
            response.status(201);
            return gson.toJson(charity);
        });

        get("/charities", "application/json", (request, response) -> {
            return gson.toJson(charityDao.getAll());
        });

        get("/charity/:id", "application/json", (request, response) -> {
            int charityId = Integer.parseInt(request.params("id"));

            Charity charityToFind = charityDao.findById(charityId);

            if (charityToFind == null) {
                throw new ApiException(404, String.format("No charity with id: \"%s\" exists", request.params("id")));
            }
            return gson.toJson(charityToFind);

        });

        get("/charities/:id/businesses", "application/json", (request, response) -> {
            int charityId = Integer.parseInt(request.params("id"));
            Charity charityToFInd = charityDao.findById(charityId);
            if (charityToFInd == null){
                throw new ApiException(404, String.format("No cahrities with the id: \"%s\" exists", request.params("id")));
            }
            else if (charityDao.getAllBusinessesForACharity(charityId).size() ==0){
                return "{\"message\"\"I'm sorry, but no businesses are lists for this Charity.\"}";
            }return gson.toJson(charityDao.getAllBusinessesForACharity(charityId));
        });

        post("/businesses/:businessId/charities/:charityId", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("businessId"));
            int charityId = Integer.parseInt(request.params("charityId"));
            Business business = businessDao.findById(businessId);
            Charity charity = charityDao.findById(charityId);

            if (business != null && charity != null) {
                charityDao.addCharityToBusiness(charity, business);
                response.status(201);
                return gson.toJson(String.format("Charity '%s' and Business '%s' have been associated", charity.getCharityName(), business.getBusinessName()));
            }
            else {
                throw new ApiException(404, String.format("Business or Charity does not exist."));
            }
        });

        get("/businesses/:id", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("id"));

            Business businessToFind = businessDao.findById(businessId);

            if (businessToFind == null) {
                throw new ApiException(404, String.format("No restaurant with id: \"%s\" exists", request.params("id")));
            }
            return gson.toJson(businessToFind);

        });

        get("/businesses/:id/businestypes", "application/json", (request, response) -> {
            int businessTypeId = Integer.parseInt(request.params("id"));
            BusinessType businessTypeToFind = businessTypeDao.findById(businessTypeId);
            if (businessTypeToFind == null){
                throw new ApiException(404, String.format("No foodtype with the id: \"%s\" exists", request.params("id")));
            }
            else if (businessTypeDao.getAllBusinessesForBusinessType(businessTypeId).size() ==0){
                return "{\"message\"\"I'm sorry, but no businesses are lists for this business type.\"}";
            }return gson.toJson(businessTypeDao.getAllBusinessesForBusinessType(businessTypeId));
        });

        post("/businesstypes/new", "application/json", (request, response) -> {
            BusinessType businessType = gson.fromJson(request.body(), BusinessType.class);
            businessTypeDao.add(businessType);
            response.status(201);
            return gson.toJson(businessType);
        });

        get("/businesstypes", "application/json", (request, response) -> {
            return gson.toJson(businessTypeDao.getAll());
        });

        post("/businesses/:businessId/businessType/:businessTypeId", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("businessId"));
            int businessTypeId = Integer.parseInt(request.params("businessTypeId"));
            Business business = businessDao.findById(businessId);
            BusinessType businessType = businessTypeDao.findById(businessTypeId);

            if (business != null && businessType != null) {
                businessTypeDao.addBusinessTypeToBusiness(businessType, business);
                response.status(201);
                return gson.toJson(String.format("BusinessType '%s' and Business '%s' have been associated", businessType.getBusinessTypeName(), business.getBusinessName()));
            }
            else {
                throw new ApiException(404, String.format("Business or Business type does not exist."));
            }
        });

        get("/businesses/:id/businesstypes", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("id"));
            Business businessToFind = businessDao.findById(businessId);
            if (businessToFind == null){
                throw new ApiException(404, String.format("No business with that ID: \"%s\" esists", request.params("id")));
            }
            else if (businessTypeDao.getAllBusinessesForBusinessType(businessId).size()==0){
                return "{\"message\":\"I'm sorry, but no businesstypes are listed for this business.\"}";
            }
            else {
                    return gson.toJson(businessTypeDao.getAllBusinessesForBusinessType(businessId));
                }
        });

        get("businessTypes/:id/businesses", "application/json" , (request, response) -> {
            int businesstypeId = Integer.parseInt(request.params("id"));
            BusinessType businessTypeToFind = businessTypeDao.findById(businesstypeId);
            if (businessTypeToFind == null){
                throw new ApiException(404, String.format("No businesstype with the id: \"%s\" exists", request.params("id")));
            }
            else if (businessTypeDao.getAllBusinessesForBusinessType(businesstypeId).size()==0){
                return "{\"message\":\"I'm sorry, but no businesses are listed for this businesstype.\"}";
            }
            else {
                return gson.toJson(businessTypeDao.getAllBusinessesForBusinessType(businesstypeId));
            }
        });

        exception(ApiException.class, (exception, request, response) -> {
            ApiException err = (ApiException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });

        after((request, response) -> {
            response.type("application/json");
        });
    }
}
