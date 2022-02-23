package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest extends Base {

    StudentRepository studentRepository;
    GraduationClassRepository graduationClassRep;

    @BeforeEach
    void before() {
        studentRepository = daoFactory.newStudentRepository(entityManager);
        graduationClassRep = daoFactory.newGraduationClassRepository(entityManager);
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
        // TODO
    }

}
