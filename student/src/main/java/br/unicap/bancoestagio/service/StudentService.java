package br.unicap.bancoestagio.service;

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.repository.StudentRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentService implements IServiceStudent {
    PanacheRepository<Student> studentRepository = new StudentRepository();

    private static final Logger LOGGER = Logger.getLogger("StudentService");

    @Incoming("student-create")
    @Transactional
    @Override
    public void save(Student s) {
        try {
            studentRepository.persist(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Incoming("student-update")
    @Transactional
    @Override
    public void update(Student s) {
        Student student = studentRepository.find("registration", s.getRegistration()).singleResult();

        student.setName(s.getName());
        student.setEmail(s.getEmail());
        student.setSemester(s.getSemester());
        student.setSkills(s.getSkills());
    }

    @Incoming("student-delete")
    @Transactional
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
