package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.TeacherRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubjectTest extends Base {

    SubjectRepository subjectRepository;
    GraduationClassRepository gradRepository;
    StudentRepository studentRepository;
    TeacherRepository teacherRepository;

    @BeforeEach
    void before() {
        subjectRepository = daoFactory.newSubjectRepository(entityManager);
        gradRepository = daoFactory.newGraduationClassRepository(entityManager);
        studentRepository = daoFactory.newStudentRepository(entityManager);
        teacherRepository = daoFactory.newTeacherRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveSubject() {

        final var subject = Fixtures.createSubject();

        entityManager.getTransaction().begin();
        subjectRepository.save(subject);
        entityManager.getTransaction().commit();
        entityManager.detach(subject);

        var pSubject = subjectRepository.findById(subject.getId());
        assertThat(pSubject).isNotNull().isNotSameAs(subject);
        assertThat(pSubject.getName()).isEqualTo(subject.getName());

    }

    @Test
    void shouldFindTeachersForSubject() {
        
        final var subject = Fixtures.createSubject();
        final var subject2= Fixtures.createSubject();


        final var graduationClass1 = Fixtures.createClass();
        final var graduationClass2 = Fixtures.createClass();
        final var graduationClass3 = Fixtures.createClass();


        final var student1 = Fixtures.createStudent(graduationClass1);
        final var student2 = Fixtures.createStudent(graduationClass2);
        final var student3 = Fixtures.createStudent(graduationClass3);


        final var teacher1 = Fixtures.createTeacher(subject, graduationClass1, student1);
        final var teacher2 = Fixtures.createTeacher(subject2, graduationClass2, student2);
        final var teacher3 = Fixtures.createTeacher(subject2, graduationClass3, student3);


        entityManager.getTransaction().begin();
        subjectRepository.save(subject);
        subjectRepository.save(subject2);

        gradRepository.save(graduationClass1);
        gradRepository.save(graduationClass2);
        gradRepository.save(graduationClass3);

        studentRepository.save(student1);
        studentRepository.save(student2);
        studentRepository.save(student3);

        teacherRepository.save(teacher1);
        teacherRepository.save(teacher2);
        teacherRepository.save(teacher3);

        entityManager.getTransaction().commit();

        entityManager.detach(subject);

        var pTeachers1 = subjectRepository.findTeachers(subject.getId());
        assertThat(pTeachers1.size()).isEqualTo(1);

        var pTeachers2= subjectRepository.findTeachers(subject2.getId());
        assertThat(pTeachers2.size()).isEqualTo(2);
    }

}
