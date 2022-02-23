package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@Table(name="Grade")
@NamedQueries({
    @NamedQuery(name="Grade.getAll", query="SELECT g from Grade g "),
    @NamedQuery(name="Grade.findHighestGrades", query="SELECT g from Grade g WHERE g.value >= :limite"),
    @NamedQuery(name="Grade.findHighestGradesBySubject", query="SELECT g from Grade g WHERE g.value >= :limite and subject = :subject")
    //@NamedQuery(name="Subject.findTeacher", query="select t from Teacher t where t.teaching = :subject")
})
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Subject subject;
    @Column(name = "grade")
    private Float value;
    private Float weight;

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public Grade setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Float getValue() {
        return value;
    }

    public Grade setValue(Float value) {
        this.value = value;
        return this;
    }

    public Float getWeight() {
        return weight;
    }

    public Grade setWeight(Float weight) {
        this.weight = weight;
        return this;
    }
}
