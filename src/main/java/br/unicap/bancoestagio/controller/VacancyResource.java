package br.unicap.bancoestagio.controller;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;
import br.unicap.bancoestagio.service.serviceInterface.IServiceVacancy;


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

    @Inject
    @Channel("vacancy-create")
    Emitter<Vacancy> createEmitter;
    @POST
    public Response create(Vacancy vacancy) {
        LOGGER.infof("Sending Vacancy %s to Kafka", vacancy);
        createEmitter.send(vacancy);
        return Response.status(Status.CREATED).build();
    }

    @Inject
    @Channel("vacancy-update")
    Emitter<Vacancy> updateEmitter;
    @PUT
    public Response update(Vacancy vacancy) {
        LOGGER.infof("Sending Update vacancy %s to Kafka", vacancy);
        updateEmitter.send(vacancy);
        return Response.status(Status.ACCEPTED).build();
    }

    @Inject
    @Channel("vacancy-delete")
    Emitter<Long> deleteEmitter;
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.infof("Sending id %s delete  to Kafka", id);
        deleteEmitter.send(id);
        return Response.status(Status.ACCEPTED).build();
    }

    

}