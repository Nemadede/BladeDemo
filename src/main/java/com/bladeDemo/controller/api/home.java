package com.bladeDemo.controller.api;

import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;

@Path
public class home {

    @GetRoute(value = "./")
    public void hello1(Request request, Response response){
        response.render("index.html");
    }

}
