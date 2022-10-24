package com.codeflex.springboot;
import com.codeflex.springboot.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

@SpringBootApplication(scanBasePackages={"com.codeflex.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan combined

public class SpringbootRestOnlineStore implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(SpringbootRestOnlineStore.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("NamedParameterJdbcBookRepository")  // Test NamedParameterJdbcTemplate
	private ProductRepository productRepository;

	@Bean
	public LobHandler lobHandler() {
		return new DefaultLobHandler();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRestOnlineStore.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
