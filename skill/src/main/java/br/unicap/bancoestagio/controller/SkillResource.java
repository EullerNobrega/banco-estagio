package br.unicap.bancoestagio.controller;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.service.SkillService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/skills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SkillResource {
    @Inject
    SkillService skillService;

    private static final Logger LOGGER = Logger.getLogger("StudentResource");

    @GET
    public List<Skill> fetchAll() {
        return skillService.list();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Skill s = skillService.get(id);
        return Response.ok().entity(s).build();
    }

    @Inject
    @Channel("skill-create")
    Emitter<Skill> createEmitter;

    @POST
    public Response create(Skill skill) {
        LOGGER.infof("Sending Skill %s to Kafka", skill);
        createEmitter.send(skill);
        return Response.status(Response.Status.CREATED).build();
    }

    @Inject
    @Channel("skill-update")
    Emitter<Skill> updateEmitter;

    @PUT
    public Response update(Skill skill) {
        LOGGER.infof("Sending Update skill %s to Kafka", skill);
        updateEmitter.send(skill);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @Inject
    @Channel("skill-delete")
    Emitter<Long> deleteEmitter;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.infof("Sending id %d delete  to Kafka", id);
        deleteEmitter.send(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
