package br.unicap.bancoestagio.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.repository.StudentRepository;
import br.unicap.bancoestagio.service.serviceInterface.IServiceStudent;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class StudentService implements IServiceStudent {
    @Inject
    StudentRepository studentRepository;

    @Override
    public void save(Student s) {
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
