package com.department.hospital.component.config;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.*;
import org.springframework.test.context.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;
import org.testcontainers.utility.DockerImageName;


@ActiveProfiles("test")
public abstract class ContainerizedTestEnvironment {

	private static final int LOCAL_SMTP_PORT = 3030;
	@Container
	static MySQLContainer MY_SLQ_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));
	@Container
	static MongoDBAtlasLocalContainer MONGODB_ATLAS_CONTAINER = new MongoDBAtlasLocalContainer(
			"mongodb/mongodb-atlas-local:7.0.9");

	static GreenMail greenMail;

	@DynamicPropertySource
	static void populateTestProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", () -> MY_SLQ_CONTAINER.getJdbcUrl());
		registry.add("spring.datasource.driverClassName", () -> MY_SLQ_CONTAINER.getDriverClassName());
		registry.add("spring.datasource.username", () -> MY_SLQ_CONTAINER.getUsername());
		registry.add("spring.datasource.password", () -> MY_SLQ_CONTAINER.getPassword());
		registry.add("spring.sql.init.mode", () -> "never");
		registry.add("spring.data.mongodb.uri", () -> MONGODB_ATLAS_CONTAINER.getConnectionString());
		registry.add("spring.mail.host", () -> "localhost");
		registry.add("spring.mail.port", () -> LOCAL_SMTP_PORT);
		registry.add("spring.mail.properties.mail.smtp.auth", () -> false);
		registry.add("spring.mail.properties.mail.smtp.starttls.enable", () -> false);
	}

	@BeforeAll
	public static void setup() {
		// Start GreenMail SMTP server
		greenMail = new GreenMail(new ServerSetup(LOCAL_SMTP_PORT, null, "smtp"));
		greenMail.start();
	}

	@AfterAll
	public static void tearDown() {
		greenMail.stop();
	}
}
