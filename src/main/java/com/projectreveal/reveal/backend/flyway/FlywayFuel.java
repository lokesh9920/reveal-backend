package com.projectreveal.reveal.backend.flyway;


import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.migration.JavaMigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayFuel implements CommandLineRunner{

	@Autowired
	R__1_PLACEHOLDER r__1_placeholder;

	@Autowired
	private DataSource dataSource;

	private final Logger logger = LogManager.getLogger(FlywayFuel.class);
	
	@Override
	public void run(String... args) throws Exception {
		
		JavaMigration[] migrationClasses = {r__1_placeholder};
		Flyway flyway = Flyway.configure()
				.javaMigrations(migrationClasses)
				.dataSource(dataSource)
				.baselineOnMigrate(true)
				.ignoreFutureMigrations(true)   // check documentation
				.load();
		logger.info("Starting flyway migrations");
		flyway.migrate();
	}
	

}
