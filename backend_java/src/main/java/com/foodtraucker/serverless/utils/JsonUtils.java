package com.foodtraucker.serverless.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

/**
 * @author palmithor
 * @since 2017-09-14
 */
public class JsonUtils {

    private static final Logger logger = Logger.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private JsonUtils() {

    }


    public static String toJson(final Object o) {
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            logger.error("Error writing JSON", e);
            return "{}";
        }
    }

    public static <T> Optional<T> fromJson(final String json, final Class<T> clazz) {
        try {
            return Optional.of(mapper.readValue(json, clazz));
        } catch (IOException e) {
            logger.error("Error reading JSON", e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> fromJson(final String json, final TypeReference<T> clazz) {
        try {
            return Optional.of(mapper.readValue(json, clazz));
        } catch (IOException e) {
            logger.error("Unable to read JSON", e);
            return Optional.empty();
        }
    }

}
