package br.unicap.bancoestagio.service;

import br.unicap.bancoestagio.model.Student;
import br.unicap.bancoestagio.model.Vacancy;

import java.util.List;

public interface IServiceStudent {

    public void save(Student student);

    public void update(Student s);

    public void delete(Long id);

    public List<Student> list();

    public Student get(Long id);

}
