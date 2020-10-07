package br.unicap.bancoestagio.service.serviceInterface;

import java.util.List;

import br.unicap.bancoestagio.model.Student;

public interface IServiceStudent {

    public void save(Student s);

    public void update(Student s);

    public void delete(Long id);

    public List<Student> list();

    public Student get(Long id);

    public List<Student> findStudentsForVacancy(Long idVacancy);

}
