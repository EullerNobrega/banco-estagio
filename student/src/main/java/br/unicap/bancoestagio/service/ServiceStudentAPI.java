package br.unicap.bancoestagio.service;

import br.unicap.bancoestagio.model.Vacancy;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
@RegisterRestClient(configKey = "student-api")
public interface ServiceStudentAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/vacancies")
    public List<Vacancy> vacanciesForStudent(@PathParam("id") Long id);
}
