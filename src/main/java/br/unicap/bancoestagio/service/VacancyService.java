package br.unicap.bancoestagio.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.repository.VacancyRepository;
import br.unicap.bancoestagio.service.serviceInterface.IVacancyService;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class VacancyService implements IVacancyService {
    PanacheRepository<Vacancy> vacancyRepository = new VacancyRepository();
    @Inject
    SkillService skillService;
    
    @Override
    public void save(Vacancy v) {
        List<Skill> skills = new ArrayList();
        v.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
        v.setSkills(skills);
        vacancyRepository.persist(v);
    }

    @Override
    public void update(Vacancy v) {
        Vacancy vacancy = vacancyRepository.findById(v.id);
        vacancy.setDescription(v.getDescription());
        vacancy.setEmail(v.getEmail());
        vacancy.setTitle(v.getTitle());
        vacancy.setMinSemester(v.getMinSemester());
        vacancy.setSkills(v.getSkills());
    }

    @Override
    public void delete(Long id) {
        vacancyRepository.deleteById(id);

    }

    @Override
    public List<Vacancy> list() {
        return vacancyRepository.listAll(Sort.by("minSemester"));
    }

    @Override
    public Vacancy get(Long id) {
        return vacancyRepository.findById(id);
    }

    @Override
    public List<Vacancy> findVacanciesForStudent(Long idStudent) {
        // TODO Auto-generated method stub
        return null;
    }

}