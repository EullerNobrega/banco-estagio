package br.unicap.bancoestagio.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger("StudentService");

    @Incoming("student-create")
    @Transactional
    @Override
    public void save(String studentJsonSerialized) {
        try {
            Student s = new ObjectMapper().readValue(studentJsonSerialized, Student.class);
            LOGGER.infof("Chegou %s", s);
            List<Skill> skills = new ArrayList<Skill>();
            s.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
            
            s.setSkills(skills);
            studentRepository.persist(s);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    // @Incoming("student-update")
    // @Transactional
    @Override
    public void update(Long id, Student s) {
        Student student = studentRepository.findById(id);
        List<Skill> skills = new ArrayList<Skill>();
        
        s.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
        s.setSkills(skills);
        
        student.setName(s.getName());
        student.setEmail(s.getEmail());
        student.setSemester(s.getSemester());
        student.setSkills(skills);
    }
    @Incoming("student-delete")
    @Transactional
    @Override
    public void delete(String id) {
        if (studentRepository.isPersistent(get(Long.parseLong(id)))) {
            studentRepository.deleteById(Long.parseLong(id));
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

}
