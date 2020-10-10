package br.unicap.bancoestagio.service;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.unicap.bancoestagio.model.Skill;
import br.unicap.bancoestagio.repository.SkillRepository;
import br.unicap.bancoestagio.service.serviceInterface.IServiceSkill;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SkillService implements IServiceSkill {
    PanacheRepository<Skill> skillRepository = new SkillRepository();

    @Override
    public void save(Skill s) {
        skillRepository.persist(s);
    }

    @Override
    public void update(Skill s) {
        Skill skill = s.findById(s.id);
        skill.setDescription(s.getDescription());
    }

    @Override
    public void delete(Long id) {
        if(skillRepository.isPersistent(get(id))){
            skillRepository.deleteById(id);
        }
    }

    @Override
    public List<Skill> list() {
        return skillRepository.listAll();
    }

    @Override
    public Skill get(Long id) {
        return skillRepository.findById(id);
    }

    @Override
    public Skill get(String description) {
        return skillRepository.find("description", description).singleResult();
    }
    
}
