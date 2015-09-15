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


    public Result getUser(Long id) {
        User user = map.get(id);
        return user == null ? notFound() : ok(Json.toJson(user));
    }

    public Result createUser() {
        User user = getUserFromRequest(request());
        if(user==null)
            return badRequest("Bad parameter");

        map.put(user.getId(), user);
        return created(Json.toJson(user));
    }

    public Result updateUser(Long id) {
        if(map.get(id)==null)
            return notFound();

        try {
            String name = request().body().asFormUrlEncoded().get("name")[0];
            if(name.length()==0)
                return badRequest("Bad Parameter");
            User user = new User(id, name);
            User updated = map.replace(id, user);
            return ok(Json.toJson(user));
        } catch (NullPointerException e){
            return badRequest("Bad Parameter");
        }
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
