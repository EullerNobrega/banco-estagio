package br.unicap.bancoestagio.controller;


import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.service.MatcherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/matcher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatcherResource {
    @Inject
    MatcherService matcherService;

    @GET
    @Path("/{id}/vacancies")
    public List<Vacancy> findVacanciesForStudent(@PathParam("id") Long id) {
        return matcherService.findVacanciesForStudent(id);
    }

}
