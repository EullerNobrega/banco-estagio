package br.unicap.bancoestagio.controller;

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.IServiceStudent;
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

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    IServiceStudent studentService;

    private static final Logger LOGGER = Logger.getLogger("StudentResource");

    @GET
    public List<Student> fetchAll() {
        return studentService.list();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        Student s = studentService.get(id);
        return Response.ok().entity(s).build();
    }

    @POST
    public Response create(Student student) {
        studentService.save(student);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    public Response update(Student student) {
        studentService.update(student);
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        studentService.delete(id);
        return Response.status(Status.ACCEPTED).build();
    }


}
