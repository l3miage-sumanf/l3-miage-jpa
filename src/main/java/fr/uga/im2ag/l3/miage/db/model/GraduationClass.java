package fr.uga.im2ag.l3.miage.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@Entity
@Table(name="GraduationClass")
// TODO ajouter une named query pour une des requêtes à faire dans le repository
@NamedQueries({
    @NamedQuery(name="GraduationClass.getAll", query="SELECT g from GraduationClass g "),
    @NamedQuery(name="GraduationClass.findByYearAndName", query="SELECT g from GraduationClass g WHERE g.name = :name and year = :year")
    //@NamedQuery(name="Subject.findTeacher", query="select t from Teacher t where t.teaching = :subject")
})
public class GraduationClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer year;

    @OneToMany(mappedBy = "belongTo")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GraduationClass setId(Long id) {
        this.id = id;
        return this;
    }

    public GraduationClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public GraduationClass setYear(Integer year) {
        this.year = year;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public GraduationClass setStudents(List<Student> students) {
        this.students = students;
        return this;
    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }
}
