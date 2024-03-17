package com.mju.mentoring.board.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class DatabaseCleanup implements InitializingBean {
	@PersistenceContext
	private EntityManager entityManager;
	private List<String> tableNames;

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
		for (String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
			entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN ID RESTART WITH 1")
				.executeUpdate();
		}
		entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
	}

	@Override
	public void afterPropertiesSet() {
		tableNames = entityManager.getMetamodel().getEntities().stream()
			.filter(entity -> entity.getJavaType().getAnnotation(Entity.class) != null)
			.map(entity -> entity.getName())
			.collect(Collectors.toList());
	}
}
