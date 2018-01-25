import com.google.gson.Gson;
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


        post("/businesses/new", "application/json", (request, response) ->  {
            Business business = gson.fromJson(request.body(), Business.class);
            businessDao.add(business);
            response.status(201);
            return gson.toJson(business);
        });

        get("/businesses", "application/json", (request, response) -> {
            return gson.toJson(businessDao.getAll());
        });

        get("/businesses/:id", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("id"));

            Business businessToFind = businessDao.findById(businessId);

            if (businessToFind == null) {
                throw new ApiException(404, String.format("No restaurant with id: \"%s\" exists", request.params("id")));
            }
            return gson.toJson(businessToFind);

        });

        post("/businesses/:businessId/businessType/:businessTypeId", "application/json", (request, response) -> {
            int businessId = Integer.parseInt(request.params("businessId"));
            int businessTypeId = Integer.parseInt(request.params("businessTypeId"));
            Business business = businessDao.findById(businessId);
            BusinessType businessType = businessTypeDao.findById(businessTypeId);


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
