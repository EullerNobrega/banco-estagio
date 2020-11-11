package br.unicap.bancoestagio.controller;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.service.SkillService;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
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

    @POST
    public Response create(Skill skill) {
        skillService.save(skill);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Response update(Skill skill) {
        skillService.update(skill);
        return Response.status(Response.Status.ACCEPTED).build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        skillService.delete(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
