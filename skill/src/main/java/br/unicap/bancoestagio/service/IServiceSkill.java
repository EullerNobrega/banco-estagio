package br.unicap.bancoestagio.service;

import java.util.List;

import br.unicap.bancoestagio.model.Skill;

public interface IServiceSkill {

    public void save(Skill s);

    public void update(Skill s);

    public void delete(Long id);

    public List<Skill> list();

    public Skill get(Long id);
    
    public Skill get(String description);
}
