package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest extends Base {

    TeacherRepository teacherRepository;
    GraduationClassRepository classRepository;
    StudentRepository studentRep;
    SubjectRepository subjRep;

    @BeforeEach
    void before() {
        teacherRepository = daoFactory.newTeacherRepository(entityManager);
        classRepository = daoFactory.newGraduationClassRepository(entityManager);
        studentRep = daoFactory.newStudentRepository(entityManager);
        subjRep = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveTeacher()  {

        final var graduationClass = Fixtures.createClass();

        final var student1 = Fixtures.createStudent(graduationClass);
        final var student2 = Fixtures.createStudent(graduationClass);

        final var subject = Fixtures.createSubject();

        final var teacher = Fixtures.createTeacher(subject, graduationClass, student1,student2);

        entityManager.getTransaction().begin();
        classRepository.save(graduationClass);;
        studentRep.save(student1);
        studentRep.save(student2);
        subjRep.save(subject);
        teacherRepository.save(teacher);
        entityManager.getTransaction().commit();
        entityManager.detach(teacher);

        var pTeacher = teacherRepository.findById(teacher.getId());
        assertThat(pTeacher).isNotNull().isNotSameAs(teacher);
        assertThat(pTeacher.getFirstName()).isEqualTo(teacher.getFirstName());
        assertThat(pTeacher.getLastName()).isEqualTo(teacher.getLastName());
    }

    @Test
    void shouldFindHeadingGraduationClassByYearAndName() {
        final var graduationClass = Fixtures.createClass();
        graduationClass.setName("Terminal 3");
        graduationClass.setYear(2020);

        final var student1 = Fixtures.createStudent(graduationClass);
        final var student2 = Fixtures.createStudent(graduationClass);

        final var subject = Fixtures.createSubject();

        final var teacher = Fixtures.createTeacher(subject, graduationClass, student1,student2);

        entityManager.getTransaction().begin();
        classRepository.save(graduationClass);
        studentRep.save(student1);
        studentRep.save(student2);
        subjRep.save(subject);
        teacherRepository.save(teacher);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);
        entityManager.detach(student1);
        entityManager.detach(student2);
        entityManager.detach(subject);
        

        var pTeacher = teacherRepository.findHeadingGraduationClassByYearAndName(2020,"Terminal 3");
        assertThat(pTeacher.getId()).isEqualTo(teacher.getId());

    }

}
