package com.via.rv.resources;

import com.codahale.dropwizard.hibernate.UnitOfWork;
import com.codahale.dropwizard.jersey.params.LongParam;
import com.via.rv.core.Person;
import com.via.rv.db.PersonDAO;
import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/people/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PersonDAO peopleDAO;

    public PersonResource(PersonDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GET
    @UnitOfWork
    public Person getPerson(@PathParam("personId") LongParam personId) {
        final Optional<Person> person = peopleDAO.findById(personId.get());
        if (!person.isPresent()) {
            throw new NotFoundException("No such user.");
        }
        return person.get();
    }

}
