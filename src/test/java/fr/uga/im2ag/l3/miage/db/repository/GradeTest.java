package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GradeTest extends Base {

    GradeRepository gradeRepository;
    SubjectRepository subjectRepository;

    @BeforeEach
    void before() {
        gradeRepository = daoFactory.newGradeRepository(entityManager);
        subjectRepository = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveGrade() {
        final var subject = Fixtures.createSubject();
        subjectRepository.save(subject);

        final var grade = Fixtures.createGrade(subject);

        entityManager.getTransaction().begin();
        gradeRepository.save(grade);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade).isNotNull().isNotSameAs(grade);
        assertThat(pGrade.getSubject()).isEqualTo(grade.getSubject());
    }

    @Test
    void shouldFailUpgradeGrade() {
        // TODO, ici tester que la mise à jour n'a pas eu lieu.
    }

    @Test
    void shouldFindHighestGrades() {
        final var subject = Fixtures.createSubject();
        subjectRepository.save(subject);

        final var grade1 = Fixtures.createGrade(subject);
        grade1.setValue(13f);

        final var grade2 = Fixtures.createGrade(subject);
        grade2.setValue(11f);

        final var grade3 = Fixtures.createGrade(subject);
        grade3.setValue(12f);

        final var grade4 = Fixtures.createGrade(subject);
        grade4.setValue(13f);

        final var grade5 = Fixtures.createGrade(subject);
        grade5.setValue(12f);

        entityManager.getTransaction().begin();
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        gradeRepository.save(grade5);
        entityManager.getTransaction().commit();
        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);
        entityManager.detach(grade5);


        var pLGrade = gradeRepository.findHighestGrades(12);
        assertThat(pLGrade.size()).isEqualTo(4);
    }

    @Test
    void shouldFindHighestGradesBySubject() {
        final var subject = Fixtures.createSubject();
        subjectRepository.save(subject);
        final var subject2 = Fixtures.createSubject();
        subjectRepository.save(subject2);

        final var grade1 = Fixtures.createGrade(subject);
        grade1.setValue(13f);

        final var grade2 = Fixtures.createGrade(subject);
        grade2.setValue(11f);

        final var grade3 = Fixtures.createGrade(subject2);
        grade3.setValue(12f);

        final var grade4 = Fixtures.createGrade(subject2);
        grade4.setValue(13f);

        final var grade5 = Fixtures.createGrade(subject);
        grade5.setValue(12f);

        entityManager.getTransaction().begin();
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        gradeRepository.save(grade5);
        entityManager.getTransaction().commit();
        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);
        entityManager.detach(grade5);


        var pLGrade = gradeRepository.findHighestGradesBySubject(12, subject);
        assertThat(pLGrade.size()).isEqualTo(2);
        var pLGrade2 = gradeRepository.findHighestGradesBySubject(11, subject2);
        assertThat(pLGrade2.size()).isEqualTo(2);
    }

}
