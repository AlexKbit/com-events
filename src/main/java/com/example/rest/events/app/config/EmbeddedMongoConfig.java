package com.example.rest.events.app.config;

import com.mongodb.Mongo;
import cz.jirutka.spring.embedmongo.EmbeddedMongoBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Configuration for integration tests
 */
@Configuration
@Profile({"inttest", "heroku"})
public class EmbeddedMongoConfig extends AbstractMongoConfiguration {

    private static final String DB_NAME = "events";

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new EmbeddedMongoBuilder()
                .version("2.6.1")
                .bindIp("127.0.0.1")
                .port(10000)
                .build();
    }
}
