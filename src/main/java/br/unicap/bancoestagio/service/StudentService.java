package br.unicap.bancoestagio.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.reactive.messaging.Incoming;

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
    @Incoming("student-create")
    public void save(String studentJsonSerialized) {
        try {
            Student s = new ObjectMapper().readValue(studentJsonSerialized, Student.class);
            List<Skill> skills = new ArrayList<Skill>();
            s.getSkills().forEach(skill -> skills.add(skillService.get(skill.getDescription())));
            
            s.setSkills(skills);
            studentRepository.persist(s);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    // @Incoming("students-reload")
    // public void reload(String reloadProduct){
    //     this.fetchAll();
    // }

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

}
