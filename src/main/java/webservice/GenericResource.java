/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import util.JSONtranslate;
import entities.Person;
import exceptions.PersonNotFoundException;
import exceptions.ValidationErrorException;
import persons.Facade;
import persons.IFacade;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Martin
 */
@Path("person")
public class GenericResource {

    private final IFacade facade = new Facade();
    private JSONtranslate jsonT = new JSONtranslate();
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PersonResource
     */
    public GenericResource(){
        facade.addEntityManagerFactory(Persistence.createEntityManagerFactory("persistenceunit"));
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getText() {
        return "Reached api/person";
    }

    @GET
    @Path("{id : \\d+}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPersonById(@PathParam("id") int id) throws PersonNotFoundException {
        try {
            Person person = facade.getPerson(id);
            return jsonT.PersonToJson(person);
        } catch (NoResultException ex) {
            throw new PersonNotFoundException("No person with provided id found");
        }
    }

    @GET
    @Path("{all}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPeople() {
        List<Person> people = facade.getPeople();
        return jsonT.ListToJson(people);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(String jsonPerson) throws ValidationErrorException {
        Person person = jsonT.JsonToPerson(jsonPerson);
        
        if (person.getFname().isEmpty() || person.getLname().isEmpty()) {
            throw new ValidationErrorException("Missing first name or last name");
        }
        
        Person p = facade.addPerson(person);
        return jsonT.PersonToJson(p);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String editPerson(String jsonPerson) throws PersonNotFoundException, ValidationErrorException {

        try {
            Person person = jsonT.JsonToPerson(jsonPerson);
            if (person.getFname().isEmpty() || person.getLname().isEmpty()) {
                throw new ValidationErrorException("First name or last name is missing");
            }
            Person p = facade.editPerson(jsonT.JsonToPerson(jsonPerson));
            return jsonT.PersonToJson(p);
        } catch (NoResultException ex) {
            throw new PersonNotFoundException("No person with provided id found");
        }
    }
    @DELETE
    @Path("{id}")
    //@Produces({ MediaType.TEXT_PLAIN})
    public void deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        try {
        Person p = facade.getPerson(id);
        if (p == null) {
            throw new PersonNotFoundException("Could not delete. No person wiht provided id exists");
        }
        facade.deletePerson(id);
        } catch (NoResultException ex) {
            throw new PersonNotFoundException("No person with provided id found");
        }
    }

}