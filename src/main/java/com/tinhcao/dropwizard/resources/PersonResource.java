package com.tinhcao.dropwizard.resources;

import com.tinhcao.dropwizard.db.dao.PersonDAO;
import com.tinhcao.dropwizard.db.entity.Person;
import com.tinhcao.dropwizard.db.entity.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {
    private PersonDAO personDAO;

    public PersonResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getListPerson() {
        List<Person> personList = personDAO.findAll();
        if (personList != null && personList.size() > 0) {
            return Response.ok().entity(personList).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Response getPersonById(@PathParam(value = "id") long id) {
        Person person = personDAO.findById(id);
        if (person != null) {
            return Response.ok().entity(person).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @UnitOfWork
    public Response createPerson(@Auth User user, @Valid Person person) {
        return Response.status(Response.Status.CREATED).entity(personDAO.createPerson(person)).build();
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@Auth User user, @PathParam(value = "id") long id) {
        personDAO.delete(id);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response update(@Auth User user, @PathParam(value = "id") long id, @Valid Person person) {
        person.setId(id);
        personDAO.update(person);
        return Response.ok().build();

    }
}
