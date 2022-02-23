package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.util.Date;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@Table(name="Subject")
@NamedQueries({
    @NamedQuery(name="Subject.getAll", query="SELECT s from Subject s")
    //@NamedQuery(name="Subject.findTeacher", query="select t from Teacher t where t.teaching = :subject")
})
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer points;
    private Float hours;
    private Date start;
    @Column(name = "end_date")
    private Date end;

    public Long getId() {
        return id;
    }

    public Subject setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subject setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPoints() {
        return points;
    }

    public Subject setPoints(Integer points) {
        this.points = points;
        return this;
    }

    public Float getHours() {
        return hours;
    }

    public Subject setHours(Float hours) {
        this.hours = hours;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public Subject setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public Subject setEnd(Date end) {
        this.end = end;
        return this;
    }
}
