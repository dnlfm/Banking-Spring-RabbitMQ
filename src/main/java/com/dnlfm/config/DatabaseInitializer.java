package com.dnlfm.config;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;

@Component
public class DatabaseInitializer {

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void initializeDatabase() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setSendFullScript(true);
            runner.setAutoCommit(true);
            runner.setStopOnError(true);
            try (Reader script = new InputStreamReader(new ClassPathResource("schema.sql").getInputStream())) {
                runner.runScript(script);
            }
        }
    }
}
