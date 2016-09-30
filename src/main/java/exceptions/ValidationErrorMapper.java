/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.internal.util.ExceptionUtils;

/**
 *
 * @author xboxm
 */
@Provider
public class ValidationErrorMapper implements ExceptionMapper<ValidationErrorException> {
    
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    ServletContext context;

    @Override
    public Response toResponse(ValidationErrorException ex) {
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("status", 400);
        job.addProperty("msg", ex.getMessage());
        job.addProperty("stackTrace", ExceptionUtils.exceptionStackTraceAsString(ex));
        return Response.status(400).entity(gson.toJson(job)).build();
    }
    
}