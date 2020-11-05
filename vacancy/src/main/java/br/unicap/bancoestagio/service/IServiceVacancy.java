package br.unicap.bancoestagio.service;

import java.util.List;

import br.unicap.bancoestagio.model.Vacancy;

public interface IServiceVacancy {

   public void save(Vacancy v);

   public void update(Vacancy v);

   public void delete(Long id);

   public List<Vacancy> list();

   public Vacancy get(Long id);

}
