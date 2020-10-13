package br.unicap.bancoestagio.controller;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
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

import org.jboss.resteasy.annotations.SseElementType;

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;
import br.unicap.bancoestagio.service.serviceInterface.IServiceVacancy;

@Path("/students")
@Produces(MediaType.SERVER_SENT_EVENTS)
@Consumes(MediaType.SERVER_SENT_EVENTS)
@SseElementType("application/json")
public class StudentResource {
    @Inject
    IServiceStudent studentService;
    @Inject
    IServiceVacancy vacancyService;

    @GET
    @SseElementType("application/json")
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
    @Transactional
    public Response create(Student student) {
        studentService.save(student);

        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Student student) {
        studentService.update(id, student);
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        studentService.delete(id);
        return Response.status(Status.ACCEPTED).build();
    }

    @GET
    @Path("/{id}/vacancies")
    public List<Vacancy> findVacanciesForStudent(@PathParam("id") Long id) {
        return vacancyService.findVacanciesForStudent(id);
    }

}
