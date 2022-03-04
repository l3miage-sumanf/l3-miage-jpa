package fr.uga.im2ag.l3.miage.db.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.ManyToAny;
@Entity
@Table(name="Student")
@NamedQueries({
    @NamedQuery(name="Student.getAll", query="SELECT s from Student s "),
    @NamedQuery(name="Student.findStudentHavingGradeAverageAbove", query="SELECT s from Student s JOIN s.grades g Group By s.id HAVING sum(g.value*g.weight)/sum(g.weight) >= :minAverage  ")
})
// TODO ajouter une named query pour une des requêtes à faire dans le repository
public class Student extends Person {

    @ManyToOne
    private GraduationClass belongTo;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Grade> grades;

    public GraduationClass getBelongTo() {
        return belongTo;
    }

    public Student setBelongTo(GraduationClass belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Student setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }
}
