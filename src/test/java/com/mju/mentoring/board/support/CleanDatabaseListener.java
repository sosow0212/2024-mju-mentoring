package com.mju.mentoring.board.support;

import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import java.util.List;

public class CleanDatabaseListener extends AbstractTestExecutionListener {

    private static final String GET_ALL_TABLES_NAME = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
    private static final String SET_REFERENTIAL_INTEGRITY_FALSE = "SET REFERENTIAL_INTEGRITY FALSE";
    private static final String TRUNCATE = "TRUNCATE TABLE ";
    private static final String RESET_PK = " RESTART IDENTITY;";
    private static final String SET_REFERENTIAL_INTEGRITY_TRUE = "SET REFERENTIAL_INTEGRITY TRUE";

    @Override
    public void afterTestMethod(final TestContext testContext) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        List<String> tableNames = getTableNames(jdbcTemplate);
        truncateTables(jdbcTemplate, tableNames);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        return applicationContext.getBean(JdbcTemplate.class);
    }

    private List<String> getTableNames(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList(GET_ALL_TABLES_NAME, String.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate, final List<String> tableNames) {
        for (String table : tableNames) {
            jdbcTemplate.execute(SET_REFERENTIAL_INTEGRITY_FALSE);
            jdbcTemplate.execute(TRUNCATE + table + RESET_PK);
            jdbcTemplate.execute(SET_REFERENTIAL_INTEGRITY_TRUE);
        }
    }
}
