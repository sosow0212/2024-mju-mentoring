package com.mju.mentoring.global;

import java.util.Arrays;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class DatabaseCleanListener extends AbstractTestExecutionListener {

    private static String SELECT_TABLE_NAMES_QUERY = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";
    private static String INVALIDATE_CONSTRAINT_QUERY = "SET REFERENTIAL_INTEGRITY FALSE";
    private static String VALIDATE_CONSTRAINT_QUERY = "SET REFERENTIAL_INTEGRITY TRUE";
    private static String RESET_INCREMENT_QUERY = "RESTART IDENTITY";
    private static String TRUNCATE_QUERY = "TRUNCATE TABLE";

    @Override
    public void afterTestMethod(final TestContext testContext) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate(testContext);
        List<String> tables = getTables(jdbcTemplate);
        String queries = getQueries(tables);
        truncateTables(jdbcTemplate, queries);
    }

    private String getQueries(final List<String> tables) {
        StringBuilder queries = new StringBuilder();
        tables.forEach(table -> {
            queries.append(String.join(" ", TRUNCATE_QUERY, table, RESET_INCREMENT_QUERY))
                .append(";\n");
        });
        return queries.toString();
    }

    private List<String> getTables(final JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForList(
            SELECT_TABLE_NAMES_QUERY,
            String.class);
    }

    private JdbcTemplate getJdbcTemplate(final TestContext testContext) {
        return testContext.getApplicationContext()
            .getBean(JdbcTemplate.class);
    }

    private void truncateTables(final JdbcTemplate jdbcTemplate,
        final String truncateQueries) {
        executeQueries(jdbcTemplate, INVALIDATE_CONSTRAINT_QUERY, truncateQueries,
            VALIDATE_CONSTRAINT_QUERY);
    }

    private void executeQueries(final JdbcTemplate jdbcTemplate, final String... query) {
        Arrays.stream(query)
            .forEach(jdbcTemplate::execute);
    }
}
