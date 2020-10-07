package br.unicap.bancoestagio.repository;

import javax.enterprise.context.ApplicationScoped;

import br.unicap.bancoestagio.model.Vacancy;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class VacancyRepository implements PanacheRepository<Vacancy> {

}