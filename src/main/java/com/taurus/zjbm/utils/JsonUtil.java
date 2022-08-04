package com.taurus.zjbm.utils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JSON处理工具类
 */
@Slf4j
public class JsonUtil {

    /**
     * 通用ObjectMapper
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // json->bean ,忽略bean中不存在的属性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // map->json ,忽略为null的key
        // MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        // 解析器支持解析单引号
        MAPPER.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        // 解析器支持解析特殊字符
        MAPPER.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * Bean->JSON bytes
     *
     * @param bean
     * @return JSON bytes
     */
    public static byte[] toJsonBytes(final Object bean) {

        try {
            return MAPPER.writeValueAsBytes(bean);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * Bean->JSON
     *
     * @param bean
     * @return JSON
     */
    public static String toJson(final Object bean) {

        if (bean == null) {
            return null;
        }

        try {
            return MAPPER.writeValueAsString(bean);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * JSON bytes->BEAN
     *
     * @param json  bytes
     * @param clazz
     * @return bean
     */
    public static <T> T toBean(final byte[] json, final Class<T> clazz) {

        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * JSON->BEAN
     *
     * @param json
     * @param clazz
     * @return bean
     */
    public static <T> T toBean(final String json, final Class<T> clazz) {

        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    /**
     * JSON->Bean
     *
     * @param json
     * @param clazz
     * @return bean
     */
    public static <T> T toBean(final String json, final Class<?> clazz, final Class<?>... parameterClasses) {

        if (null == json) {
            return null;
        }
        try {
            // return mapper.readValue(json,
            //         mapper.getTypeFactory().constructParametrizedType(clazz, clazz, parameterClasses));
            return MAPPER.readValue(json,
                    MAPPER.getTypeFactory().constructParametricType(clazz, parameterClasses));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * JSON->List
     *
     * @param json
     * @param clazz
     * @return list
     */
    public static <T> List<T> toList(final String json, final Class<T> clazz) {

        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readValue(json, MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Bean->Bean
     *
     * @param source
     * @param target
     * @return target bean
     */
    public static <T> T convertBean(final Object source, final Class<T> target) {

        return toBean(toJson(source), target);
    }

    /**
     * JSON->JsonNode
     *
     * @param json
     * @return JsonNode
     */
    public static JsonNode toJsonNode(final String json) {

        if (null == json) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * @param lsit
     * @param <T>
     * @return
     */
    public static <T> String list2json(final List<T> lsit) {

        if (lsit == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(lsit);
        } catch (final JsonProcessingException e) {
            log.info(e.getMessage());
        }
        return null;
    }

    /**
     * 遍历JsonNode的全部节点,并将节点信息放入到MAP中，忽略同名节点
     *
     * @param node
     */
    public static void allNodeToMap(final Map<String, String> nodeMap, final JsonNode node, final String nodeName) {

        if (node.isValueNode()) {
            if (!StringUtils.isEmpty(node.asText()) && !nodeMap.containsKey(nodeName)) {
                nodeMap.put(nodeName, node.asText());
            }
        }

        if (node.isObject()) {
            final Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                final Map.Entry<String, JsonNode> entry = it.next();
                allNodeToMap(nodeMap, entry.getValue(), entry.getKey());
            }
        }

        if (node.isArray()) {
            final Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()) {
                allNodeToMap(nodeMap, it.next(), null);
            }
        }
    }
}
