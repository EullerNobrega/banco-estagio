package br.unicap.bancoestagio.controller;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
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

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;

@Path("/students")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {
    @Inject
    IServiceStudent studentService;

    @GET
    public List<Student> fetchAll() {
        return studentService.list();
    }

    @GET
    @Path("/{registration}")
    public Response get(@PathParam("registration") Long id) {
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
    @Path("/{registration}")
    @Transactional
    public Response update(@PathParam("registration") String registration, Student student) {
        studentService.update(student);
        return Response.status(Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{registration}")
    @Transactional
    public Response delete(@PathParam("registration") Long id) {
        studentService.delete(id);
        return Response.status(Status.ACCEPTED).build();
    }

    @GET
    @Path("/{registration}/vacancies")
    public List<Vacancy> findVacanciesForStudent(@PathParam("registration") Long id) {
        return null;
    }

}
