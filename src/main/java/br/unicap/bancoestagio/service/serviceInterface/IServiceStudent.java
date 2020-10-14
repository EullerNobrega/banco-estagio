package br.unicap.bancoestagio.service.serviceInterface;

import java.util.List;

import br.unicap.bancoestagio.model.Student;

public interface IServiceStudent {

    public void save(String studentJsonSerialized);

    public void update(Long id, Student s);

    public void delete(String id);

    public List<Student> list();

    public Student get(Long id);

    // public List<Student> findStudentsForVacancy(Long idVacancy);

}
