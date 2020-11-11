package br.unicap.bancoestagio.controller;

import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.IServiceVacancy;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;


@Path("/vacancies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VacancyResource {
    @Inject
    IServiceVacancy vacancyService;

    private static final Logger LOGGER = Logger.getLogger("VacancyResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vacancy> fetchAll() {
        return vacancyService.list();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Vacancy v = vacancyService.get(id);
        return Response.ok().entity(v).build();
    }

    @POST
    public Response create(Vacancy vacancy) {
        vacancyService.save(vacancy);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    public Response update(Vacancy vacancy) {
        vacancyService.update(vacancy);
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        vacancyService.delete(id);
        return Response.status(Status.ACCEPTED).build();
    }

    

}