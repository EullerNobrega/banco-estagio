package br.unicap.bancoestagio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;
import br.unicap.bancoestagio.service.serviceInterface.IServiceVacancy;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    IServiceStudent studentService;
    @Inject
    IServiceVacancy vacancyService;

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

    @Inject
    @Channel("student-create")
    Emitter<Student> createEmitter;
    @POST
    public Response create(Student student) {
        LOGGER.infof("Sending Student %s to Kafka", student);
        createEmitter.send(student);
        return Response.status(Status.CREATED).build();
    }

    @Inject
    @Channel("student-update")
    Emitter<Student> updateEmitter;

    @PUT
    public Response update(Student student) {
        LOGGER.infof("Sending Update student %s to Kafka", student);
        updateEmitter.send(student);
        return Response.status(Status.ACCEPTED).build();
    }

    @Inject
    @Channel("student-delete")
    Emitter<Long> deleteEmitter;

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOGGER.infof("Sending id %d delete  to Kafka", id);
        deleteEmitter.send(id);
        return Response.status(Status.ACCEPTED).build();
    }

    @GET
    @Path("/{id}/vacancies")
    public List<Vacancy> findVacanciesForStudent(@PathParam("id") Long id) {
        return vacancyService.findVacanciesForStudent(id);
    }

}
