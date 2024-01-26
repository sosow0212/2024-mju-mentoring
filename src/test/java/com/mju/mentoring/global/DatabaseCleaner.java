package com.mju.mentoring.global;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles("test")
public class DatabaseCleaner implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;
    private List<String> tables;

    @Override
    public void afterPropertiesSet() {
        this.tables = entityManager.getMetamodel()
            .getEntities()
            .stream()
            .filter(e -> e.getJavaType().getAnnotation(Entity.class) != null)
            .map(EntityType::getName)
            .toList();
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE");
        tables.forEach(this::truncateTable);
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE");
    }

    public void truncateTable(final String table) {
        entityManager.createNativeQuery("TRUNCATE TABLE " + table)
            .executeUpdate();
    }
}
