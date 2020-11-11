package br.unicap.bancoestagio.service;

import br.unicap.bancoestagio.model.Vacancy;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/vacancies")
@RegisterRestClient(configKey = "vacancy-api")
public interface ServiceVacancyAPI {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/vacancies")
    public List<Vacancy> vacanciesForStudent(@PathParam("id") Long id);
}
