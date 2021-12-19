package ca.jrvs.apps.trading.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  /**
   * Convert Java Object to JSON string
   *
   * @param object            Object you want to parse
   * @param prettyJson        displays indented json object
   * @param includeNullValues include null values
   * @return JSON String
   * @throws JsonProcessingException Exception
   */
  public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    if (!includeNullValues) {
      mapper.setSerializationInclusion(Include.NON_NULL);
    }
    if (prettyJson) {
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    return mapper.writeValueAsString(object);
  }

  /**
   * Parse JSON string to an object
   *
   * @param json  JSON string
   * @param clazz object class
   * @param <T>   Type
   * @return Object
   * @throws IOException
   */
  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return (T) mapper.readValue(json, clazz);
  }

  /**
   * Prints Json in readable form
   * @param object model object you want to print in json format
   */
  public static void print(Object object) {
    try {
      System.out.println(toJson(object, true, true));
    } catch (JsonProcessingException e) {
      System.out.println("Exception");
    }
  }
}