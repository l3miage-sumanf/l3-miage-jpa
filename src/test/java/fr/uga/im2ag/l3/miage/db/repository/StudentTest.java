package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

class StudentTest extends Base {

    StudentRepository studentRepository;
    GraduationClassRepository graduationClassRep;
    GradeRepository gradRep;
    SubjectRepository subjRep;

    @BeforeEach
    void before() {
        studentRepository = daoFactory.newStudentRepository(entityManager);
        graduationClassRep = daoFactory.newGraduationClassRepository(entityManager);
        gradRep = daoFactory.newGradeRepository(entityManager);
        subjRep = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveStudent() {
        final var graduationClass = Fixtures.createClass();
        final var student = Fixtures.createStudent(graduationClass);

        entityManager.getTransaction().begin();
        graduationClassRep.save(graduationClass);
        studentRepository.save(student);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);
        entityManager.detach(student);

        var pStudent = studentRepository.findById(student.getId());
        assertThat(pStudent).isNotNull().isNotSameAs(student);
        assertThat(pStudent.getFirstName()).isEqualTo(student.getFirstName());
    }

    @Test
    void shouldFindStudentHavingGradeAverageAbove() {
        final var graduationClass = Fixtures.createClass();

        final var student1 = Fixtures.createStudent(graduationClass);
        final var student2 = Fixtures.createStudent(graduationClass);
        final var student3 = Fixtures.createStudent(graduationClass);

        final var subject1 = Fixtures.createSubject();

        final var grades11 = Fixtures.createGrade(subject1);
        grades11.setValue(12f);
        grades11.setWeight(2f);
        final var grades12 = Fixtures.createGrade(subject1);
        grades12.setValue(8f);
        grades12.setWeight(2f);
        final var grades13 = Fixtures.createGrade(subject1);
        grades13.setValue(18f);
        grades13.setWeight(2f);

        final var subject2 = Fixtures.createSubject();

        final var grades21 = Fixtures.createGrade(subject2);
        grades21.setValue(13f);
        grades21.setWeight(3f);
        final var grades22 = Fixtures.createGrade(subject2);
        grades22.setValue(8f);
        grades22.setWeight(3f);
        final var grades23 = Fixtures.createGrade(subject2);
        grades23.setValue(14f);
        grades23.setWeight(3f);

        ArrayList<Grade> gradesS1 = new ArrayList<Grade>();
        gradesS1.add(grades11);
        gradesS1.add(grades21);
        student1.setGrades(gradesS1);

        ArrayList<Grade> gradesS2 = new ArrayList<Grade>();
        gradesS2.add(grades12);
        gradesS2.add(grades22);
        student2.setGrades(gradesS2);

        ArrayList<Grade> gradesS3 = new ArrayList<Grade>();
        gradesS3.add(grades13);
        gradesS3.add(grades23);
        student3.setGrades(gradesS3);

        entityManager.getTransaction().begin();
        graduationClassRep.save(graduationClass);
        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);
        subjRep.save(subject1);
        subjRep.save(subject2);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(student3);
        

        var pStudent = studentRepository.findStudentHavingGradeAverageAbove(11d);

        assertThat(pStudent.size()).isEqualTo(2);


    }

}
