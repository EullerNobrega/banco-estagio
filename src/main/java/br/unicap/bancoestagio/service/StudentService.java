package br.unicap.bancoestagio.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.repository.StudentRepository;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class StudentService implements IServiceStudent {
    PanacheRepository<Student> studentRepository = new StudentRepository();
    @Inject
    SkillService skillService;

    @Override
    public void save(Student s) {
        List<Skill> skills = new ArrayList();
        s.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
        s.setSkills(skills);
        studentRepository.persist(s);
    }

    @Override
    public void update(Student s) {
        Student student = studentRepository.findById(s.id);
        student.setName(s.getName());
        student.setEmail(s.getEmail());
        student.setSemester(s.getSemester());
        student.setSkills(s.getSkills());
    }

    @Override
    public void delete(Long id) {
        if (studentRepository.isPersistent(get(id))) {
            studentRepository.deleteById(id);

        }

    }

    @Override
    public List<Student> list() {
        return studentRepository.listAll(Sort.by("registration"));

    }

    @Override
    public Student get(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findStudentsForVacancy(Long idVacancy) {
        // TODO Auto-generated method stub
        return null;
    }
}
