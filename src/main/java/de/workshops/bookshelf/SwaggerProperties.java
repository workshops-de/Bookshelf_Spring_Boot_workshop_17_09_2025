package de.workshops.bookshelf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("swagger.config")
record SwaggerProperties(String title, String version, int capacity, License license) {}

