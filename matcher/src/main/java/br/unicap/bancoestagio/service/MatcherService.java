package br.unicap.bancoestagio.service;


import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.repository.VacancyRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MatcherService {

    VacancyRepository vacancyRepository =  new VacancyRepository();
    IServiceStudent studentService = new StudentService();
    public List<Vacancy> findVacanciesForStudent(Long idStudent) {
        Student student = studentService.get(idStudent);
        List<Skill> skills = student.getSkills();
        List<Vacancy> matchVacancies = new ArrayList<Vacancy>();

        skills.forEach(s -> {
            List<Vacancy> list = vacancyRepository
                    .find("select v from Vacancy v join fetch v.skills s where s.id = ?1", s.id).list();
            matchVacancies.addAll(list);
        });

        return matchVacancies;
    }
}
