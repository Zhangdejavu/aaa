package com.zqw.gp.component.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zqw.gp.component.common.ErrorCode;
import com.zqw.gp.component.common.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * http请求和响应的通用处理方法
 */
@RestController
@Slf4j
public class BaseController {

    @Autowired
    private Gson gson;

    /**
     * 构建Exception的消息体，如果是serverException，那么从errorCode里面获得msg。如果不是，使用原始的msg
     *
     * @param ex
     *            异常
     * @return 响应消息
     */
    @ExceptionHandler
    @ResponseBody
    public Map<String, Object> exceptionHandle(Exception ex) {
        if (ex instanceof ServerException) {
            Map<String, Object> map = new HashMap<String, Object>();
            ServerException seEx = (ServerException) ex;
            map.put("code", seEx.getCode());
            map.put("msg", seEx.getMessage());
            return map;
        } else if (ex instanceof HttpMessageNotReadableException || ex instanceof ClientAbortException) {
            log.error("system get error http request ", ex);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", ErrorCode.HTTP_PARA_WRONG);
            map.put("msg", "http para wrong");
            return map;
        } else {
            ex.printStackTrace();
            // 将对应的堆栈打印出来
            StringBuilder sb = new StringBuilder(ex.toString()).append("-------");
            for (StackTraceElement elements : ex.getStackTrace()) {
                sb.append(elements.toString()).append("-------");
            }
            log.error("system uncatched error {} ", sb.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("code", ErrorCode.SYSTEM_ERROR);
            map.put("msg", "SYSTEM ERROR");
            return map;
        }
    }

    /**
     * 获得指定KEY的String的value
     *
     * @param query
     *            请求消息
     * @param key
     *            请求key
     * @return 消息里面的KEY对应的value
     * @throws ServerException
     *             key不存在
     */
    public String getStrParam(JsonObject query, String key) throws ServerException {
        if (query.get(key) == null) {
            throw new ServerException(ErrorCode.HTTP_PARAM_MISS, " param '" + key + "' miss");
        }
        String value = query.get(key).getAsString();
        return value;
    }

    /**
     *
     * @param query
     * @param key
     *            请求key
     * @return boolean true/false
     * @throws ServerException
     */
    public boolean getBooleanParam(JsonObject query, String key) throws ServerException {
        if (query.get(key) == null) {
            throw new ServerException(ErrorCode.HTTP_PARAM_MISS, " param '" + key + "' miss");
        }
        boolean value = query.get(key).getAsBoolean();
        return value;
    }

    /**
     * 获得指定KEY的long的value
     *
     * @param query
     *            请求消息
     * @param key
     *            请求key
     * @return 消息里面的KEY对应的value
     * @throws ServerException
     *             key不存在或者不是long类型
     */
    public long getLongParam(JsonObject query, String key) throws ServerException {
        if (query.get(key) == null) {
            throw new ServerException(ErrorCode.HTTP_PARAM_MISS, " param '" + key + "' miss");
        }
        String value = query.get(key).getAsString();
        try {
            return Long.parseLong(value);
        } catch (Exception ex) {
            throw new ServerException(ErrorCode.HTTP_PARA_WRONG, " param '" + key + "' not long format");
        }
    }

    /**
     * 获得指定KEY的float的value
     *
     * @param query
     *            请求消息
     * @param key
     *            请求key
     * @return 消息里面的KEY对应的value
     * @throws ServerException
     *             key不存在或者不是float类型
     */
    public float getFloatParam(JsonObject query, String key) throws ServerException {
        String value = query.get(key).getAsString();
        if (value == null) {
            throw new ServerException(ErrorCode.HTTP_PARAM_MISS, " param '" + key + "' miss");
        }

        try {
            return Float.parseFloat(value);
        } catch (Exception ex) {
            throw new ServerException(ErrorCode.HTTP_PARA_WRONG, " param '" + key + "' not float format");
        }
    }

    /**
     * 获得指定数值的int的value
     *
     * @param query
     *            请求消息
     * @param key
     *            请求key
     * @return 消息里面的KEY对应的value
     * @throws ServerException
     *             key不存在或者不是int类型
     */
    public int getIntParam(JsonObject query, String key) throws ServerException {
        if (null == query.get(key)) {
            throw new ServerException(ErrorCode.HTTP_PARAM_MISS, " param '" + key + "' miss");
        }

        String value = query.get(key).getAsString();
        try {
            int intValue = Integer.parseInt(value);

            return intValue;
        } catch (Exception ex) {
            throw new ServerException(ErrorCode.HTTP_PARA_WRONG, " param '" + key + "' not int format");
        }
    }

    public Integer getOptionIntParam(JsonObject query, String key, Integer defaultValue) throws ServerException {
        if (null == query.get(key) || query.get(key).isJsonNull()) {
            return defaultValue;
        }
        String value = query.get(key).getAsString();
        try {
            int intValue = Integer.parseInt(value);
            return intValue;
        } catch (Exception ex) {
            throw new ServerException(ErrorCode.HTTP_PARA_WRONG, " param '" + key + "' not int format");
        }
    }

    public String getOptionStrParam(JsonObject query, String key, String defaultValue) throws ServerException {
        if (null == query.get(key) || query.get(key).isJsonNull()) {
            return defaultValue;
        }
        String value = query.get(key).getAsString();
        return value;
    }

    /**
     * 构建成功的响应消息体
     *
     * @return 成功消息体
     */
    public Map<String, Object> success() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", ErrorCode.SUCCESS);
        result.put("msg", "ok");
        return result;
    }

    /**
     * 构建成功的响应消息体
     *
     * @param obj
     *            用户消息体
     * @return 包含用户消息体的成功消息体
     */
    public Map<String, Object> success(Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", ErrorCode.SUCCESS);
        result.put("msg", "ok");
        if (null != obj) {
            result.put("data", obj);
        }
        return result;
    }

    /**
     * 移除不需要的字段。 当前只支持第一层的移除。 如果需要移除内部的，需要自己额外的写
     *
     * @param obj
     *            用户对象
     * @param ignores
     *            需要移除的字符串key的列表
     * @return 移除对应的内容之后的用户对象
     */
    public JsonObject buildIgnore(Object obj, String... ignores) {
        if (null == obj) {
            return null;
        }
        String str = gson.toJson(obj);
        JsonObject json = gson.fromJson(str, JsonObject.class);
        for (String ignore : ignores) {
            json.remove(ignore);
        }
        return json;
    }

    /**
     * 移除列表里面不需要的字段。 当前只支持第一层的移除。 如果需要移除内部的，需要自己额外的写
     *
     * @param obj
     *            用户的列表对象
     * @param ignores
     *            需要移除的字符串key的列表
     * @return 移除对应的内容之后的用户对象
     * @throws ServerException
     *             入参非列表
     *
     */
    public List<JsonObject> buildListIgnore(Object obj, String... ignores) throws ServerException {
        if (null == obj) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR, "error to format the list to json list ");
        }
        List<?> objs = (List<?>) obj;
        List<JsonObject> jsons = new ArrayList<JsonObject>();
        for (Object o : objs) {
            JsonObject json = buildIgnore(o, ignores);
            jsons.add(json);
        }

        return jsons;
    }

    /**
     * 构建列表里面保留的字段的对象。 当前支持所有层的构建，多层级的关键字用.来隔开例如a.b.c
     *
     * @param obj
     *            用户的列表对象
     * @param contains
     *            需要保留的字符串key的列表
     * @return 保留的内容之后的用户对象
     *
     */
    public JsonObject buildContain(Object obj, String... contains) {
        // 如果包含了多个层级，那么需要将对应的string转为List;
        Map<String, List<String>> containMaps = new HashMap<>();
        for (String contain : contains) {
            String[] tmps = contain.split("\\.");
            String key = tmps[0];
            if (containMaps.containsKey(key)) {
                for (int i = 1; i < tmps.length; i++) {
                    containMaps.get(key).add(tmps[i]);
                }
            } else {
                containMaps.put(key, new ArrayList<String>());
                for (int i = 1; i < tmps.length; i++) {
                    containMaps.get(key).add(tmps[i]);
                }
            }
        }
        return buildContain(obj, containMaps);
    }

    private JsonObject buildContain(Object obj, Map<String, List<String>> contains) {
        if (null == obj) {
            return null;
        }
        String str = gson.toJson(obj);
        JsonObject json = gson.fromJson(str, JsonObject.class);
        Set<String> removeKeys = new HashSet<String>();
        for (String key : json.keySet()) {
            boolean isContain = false;
            for (String containKey : contains.keySet()) {
                List<String> containValues = contains.get(containKey);

                if (key.equals(containKey)) {
                    if (!containValues.isEmpty()) {
                        String[] nextValues = new String[containValues.size()];
                        int i = 0;
                        for (String nextValue : containValues) {
                            nextValues[i] = nextValue;
                            i++;
                        }
                        JsonObject value = buildContain(json.get(containKey), nextValues);
                        json.add(containKey, value);
                    }
                    isContain = true;
                    break;
                }

            }
            if (!isContain) {
                removeKeys.add(key);
            }
        }

        for (String removeKey : removeKeys) {
            json.remove(removeKey);
        }
        return json;
    }

    /**
     * 构建列表里面需要的字段列表。 当前支持所有层的构建，多层级的关键字用.来隔开例如a.b.c
     *
     * @param obj
     *            用户的列表对象
     * @param contains
     *            需要保留的字符串key的列表
     * @return 保留的内容之后的用户对象
     * @throws ServerException
     *             obj非列表
     */
    public List<JsonObject> buildListContain(Object obj, String... contains) throws ServerException {
        if (null == obj) {
            return null;
        }
        if (!(obj instanceof List)) {
            throw new ServerException(ErrorCode.SYSTEM_ERROR, "error to format the list to json list ");
        }
        List<?> objs = (List<?>) obj;
        List<JsonObject> jsons = new ArrayList<JsonObject>();
        for (Object o : objs) {
            JsonObject json = buildContain(o, contains);
            jsons.add(json);
        }

        return jsons;
    }

}
