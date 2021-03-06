package controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import models.User;
import play.libs.Json;
import play.*;
import play.mvc.*;

import views.html.*;
import java.util.HashMap;


public class Application extends Controller {

    static HashMap<Long, User> map = new HashMap<Long,User>();
    
    public Result index() {
        return ok(index.render("Hello RESTful Exercise!"));
    }

    public Result getUsers() {
        return ok(Json.toJson(map.values()));
    }



    //Helper
    private User getUserFromRequest(Http.Request r) {
         try {
             User user = Json.fromJson(request().body().asJson(), User.class);
             return (user.getId()==null || user.getName()==null) ? null : user;
         } catch (Exception e) {
            return null;
         }
    }

}
