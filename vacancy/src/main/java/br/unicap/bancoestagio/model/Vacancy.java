package br.unicap.bancoestagio.model;

import java.util.List;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "vacancy")
public class Vacancy extends PanacheEntity implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private String email;
    private int minSemester;
    @ManyToMany
    private List<Skill> skills;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMinSemester() {
        return minSemester;
    }

    public void setMinSemester(int minSemester) {
        this.minSemester = minSemester;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

}
