package cc.home.util;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by thinkpad on 2016/6/24.
 */
public class MapUtils {

    private static final Logger log = LoggerFactory.getLogger(MapUtils.class);

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private static void setFieldValueByName(String fieldName, Object o, Object value) {
        try {
            if (value == null) {
                return;
            }
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "set" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{value.getClass()});
            method.invoke(o, new Object[]{value});
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
    }

    public static <T> Map toMap(T t) {
        if (t == null) {
            return null;
        }
        Map map = new HashMap();
        Field[] fields = t.getClass().getDeclaredFields();
        if (t.getClass().getSuperclass() != null) {
            Field[] superfields = t.getClass().getSuperclass().getDeclaredFields();
            fields =  ArrayUtils.addAll(fields, superfields);
        }
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Object value;

            if (field.getGenericType().toString().equals(
                    "class java.util.Date")) {
                value = getFieldValueByName(field.getName(), t);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (value != null && value instanceof Date) {
                    value = format.format(value);
                }

            } else {
                value = getFieldValueByName(field.getName(), t);
            }
            if (value != null) {
                map.put(field.getName(), value);
            }

        }
        return map;
    }

    public static <T> Map toSqlMap(T t) {
        if (t == null) {
            return null;
        }
        Map map = new HashMap();
        Field[] fields = t.getClass().getDeclaredFields();
        if (t.getClass().getSuperclass() != null) {
            Field[] superfields = t.getClass().getSuperclass().getDeclaredFields();
            fields = (Field[]) ArrayUtils.addAll(fields, superfields);
        }
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Object value;

            if (field.getGenericType().toString().equals(
                    "class java.util.Date")) {
                value = getFieldValueByName(field.getName(), t);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (value != null && value instanceof Date) {
                    value = format.format(value);
                }

            } else {
                value = getFieldValueByName(field.getName(), t);
            }

            if (value != null) {
                map.put(field.getName(), value);
            }

        }
        return map;
    }



    public static <T> T formMap(Map map, T t) {
        if (map.size() <= 0) {
            return null;
        }
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("serialVersionUID")) {
                continue;
            }
            Object value = map.get(field.getName());
            setFieldValueByName(field.getName(), t, value);
        }
        return t;
    }

    public static <T> List<Map> toMap(List<T> ts) {
        if (ts == null) {
            return null;
        }
        List<Map> maps = new ArrayList<>();
        for (T t : ts) {
            Map map = toMap(t);
            maps.add(map);
        }
        return maps;
    }
}
