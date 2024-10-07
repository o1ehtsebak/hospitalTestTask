package com.department.hospital.component.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.ai.autoconfigure.vectorstore.mongo.MongoDBAtlasVectorStoreAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.*;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;
import org.testcontainers.utility.DockerImageName;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;


@Testcontainers
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = MongoDBAtlasVectorStoreAutoConfiguration.class)
public abstract class ContainerizedTestEnvironment {

	private static final int LOCAL_SMTP_PORT = 3030;

	public static GreenMail GREEN_MAIL;

	@Container
	static MongoDBAtlasLocalContainer MONGODB_ATLAS_CONTAINER = new MongoDBAtlasLocalContainer(
			"mongodb/mongodb-atlas-local:7.0.9").waitingFor(Wait.forHealthcheck());

	@Container
	static MySQLContainer MY_SLQ_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"))
			.waitingFor(Wait.forHealthcheck());

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
	}

	@BeforeAll
	public static void setup() {
		// Start GreenMail SMTP server
		GREEN_MAIL = new GreenMail(new ServerSetup(LOCAL_SMTP_PORT, null, "smtp"))
				.withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
		GREEN_MAIL.start();
	}

	@AfterAll
	public static void tearDown() {
		GREEN_MAIL.stop();
	}
}
