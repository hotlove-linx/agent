package com.hotlove.agent.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toJsonObject(String str, Class<T> clazz) {
        if (Objects.isNull(str)) {
            return null;
        }

        try {
            return objectMapper.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json parse error", e);
        }
    }
}
