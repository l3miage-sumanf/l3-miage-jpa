package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GraduationClassTest extends Base {

    GraduationClassRepository classRepository;

    @BeforeEach
    void before() {
        classRepository = daoFactory.newGraduationClassRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveClass() {
        
        final var graduationClass = Fixtures.createClass();

        entityManager.getTransaction().begin();
        classRepository.save(graduationClass);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);

        var pgraduationClass = classRepository.findById(graduationClass.getId());
        assertThat(pgraduationClass).isNotNull().isNotSameAs(graduationClass);
        assertThat(pgraduationClass.getName()).isEqualTo(graduationClass.getName());
    }


    @Test
    void shouldFindByYearAndName() {
        final var graduationClass = Fixtures.createClass();
        graduationClass.setName("SPC");
        graduationClass.setYear(2020);

        entityManager.getTransaction().begin();
        classRepository.save(graduationClass);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);

        var pgraduationClass = classRepository.findByYearAndName(2020,"SPC");
        assertThat(pgraduationClass).isNotNull().isNotSameAs(graduationClass);
        assertThat(pgraduationClass.getName()).isEqualTo(graduationClass.getName());
    }

}
