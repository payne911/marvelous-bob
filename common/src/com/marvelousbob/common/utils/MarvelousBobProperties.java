package com.marvelousbob.common.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@Slf4j
public class MarvelousBobProperties {

    // add properties file here
    private static final List<String> PROPERTY_FILES = List.of("config.properties");
    private static Properties props;

    private MarvelousBobProperties() {
    }

    @SneakyThrows
    public static Properties getProps() {
        if (props == null) {
            PROPERTY_FILES.forEach(MarvelousBobProperties::loadProperties);
        }
        return props;
    }

    private static void loadProperties(String propFile) {
        props = new Properties();
        try (InputStream input = MarvelousBobProperties.class.getClassLoader().getResourceAsStream(propFile)) {
            props.load(input);
            log.debug("Properties loaded from file {}", propFile);
        } catch (Exception ioException) {
            log.error("Could not load properties from file {}", propFile);
        }
    }
}
