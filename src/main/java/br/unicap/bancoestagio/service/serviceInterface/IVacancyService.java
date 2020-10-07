package br.unicap.bancoestagio.service.serviceInterface;

import java.util.List;

import br.unicap.bancoestagio.model.Vacancy;

public interface IVacancyService {

   public void save(Vacancy v);

   public void update(Vacancy v);

   public void delete(Long id);

   public List<Vacancy> list();

   public Vacancy get(Long id);

   public List<Vacancy> findVacanciesForStudent(Long idStudent);
}
