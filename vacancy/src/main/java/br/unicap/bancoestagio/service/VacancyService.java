package br.unicap.bancoestagio.service;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;
import br.unicap.bancoestagio.repository.VacancyRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VacancyService implements IServiceVacancy {
    PanacheRepository<Vacancy> vacancyRepository = new VacancyRepository();
    @Inject
    SkillService skillService;

    StudentService  studentService = new StudentService();

    private static final Logger LOGGER = Logger.getLogger("VacancyService");

    @Incoming("vacancy-create")
    @Transactional
    @Override
    public void save(Vacancy v) {
        List<Skill> skills = new ArrayList<Skill>();
        v.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
        v.setSkills(skills);
        vacancyRepository.persist(v);
    }

    @Incoming("vacancy-update")
    @Transactional
    @Override
    public void update(Vacancy v) {
        Vacancy vacancy = vacancyRepository.find("email", v.getEmail()).singleResult();
        List<Skill> skills = new ArrayList<Skill>();
        v.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));

        vacancy.setDescription(v.getDescription());
        vacancy.setEmail(v.getEmail());
        vacancy.setTitle(v.getTitle());
        vacancy.setMinSemester(v.getMinSemester());
        vacancy.setSkills(skills);
    }

    @Incoming("vacancy-delete")
    @Transactional
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
        VacancyRepository vacancyRepository = new VacancyRepository();
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