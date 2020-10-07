package br.unicap.bancoestagio.repository;

import javax.enterprise.context.ApplicationScoped;

import br.unicap.bancoestagio.model.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {   
    
}
