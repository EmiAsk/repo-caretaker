package util;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.tinkoff.edu.java.scrapper.configuration.database.JdbcAccessConfiguration;


@ContextConfiguration(classes = JdbcAccessConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@JdbcTest
public class JdbcIntegrationEnvironment extends IntegrationEnvironment {
//    @DynamicPropertySource
//    static void registerProperties(DynamicPropertyRegistry registry) {
//        registry.add("app.database-access-type", () -> ApplicationProperties.AccessType.JDBC);
//    }
}
