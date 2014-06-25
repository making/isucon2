package isucon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

@Service
public class Isucon2Initializer {
    @Autowired
    DataSource dataSource;

    public void init() {
        EncodedResource resource = new EncodedResource(new ClassPathResource("data.sql"), StandardCharsets.UTF_8);
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            ScriptUtils.executeSqlScript(connection, resource);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
