package ru.itpark.soa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MapConvertUtils {
    public static boolean isEmpty(Map<String, Object> data, String par){
        if(data.get(par) == null){
            return true;
        }
        if(data.get(par).toString().trim().equals("")){
            return true;
        }
        return false;
    }

    public static void getFromOldMapIsEmpty(Map<String, Object> src, Map<String, Object> old, String... params){
        if(params!=null){
            int count = params.length;
            for(int i = 0; i < count; i++){
                String parName = params[i];
                if(src.get(parName) == null || src.get(parName).equals("")){
                    src.put(parName, old.get(parName));
                }
            }
        }
    }

    public static void truncateEmptyParams(Map<String, Object> data) {
        ArrayList<String> keys = new ArrayList<String>();
        keys.addAll(data.keySet());
        for (String key : keys) {
            if(data.get(key) == null){
                data.remove(key);
            }
            if (data.get(key) != null && String.valueOf(data.get(key)).length() == 0) {
                data.remove(key);
            }
        }
    }

    /**
     * ����� ���������� ������� ������� ������, � ������� ������ �����������
     * ������ ���� �������
     */
    public static <T> T getSingle(List<T> list) {
        if (list.size() != 1) {
            throw new IllegalArgumentException("Actual size of list: " + list.size());
        }
        return list.get(0);
    }

    /**
     * ����� ���������� ������� ������� ������
     */
    public static <T> T getFirst(List<T> list) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Actual size of list: " + list.size());
        }
        return list.get(0);
    }

    /**
     * ���������� ��������, ������� �������� �������
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> asList(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (!(value instanceof List)) {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a list");
        }
        return (List<Map<String, Object>>) value;
    }

    /**
     * ���������� ��������, ������� �������� ������
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> asMap(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (!(value instanceof Map)) {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a map");
        }
        return (Map<String, Object>) value;
    }

    /**
     * ���������� ��������, ������� �������� �����
     */
    public static Date asDate(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof Date) {
            return (Date) value;
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a date");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Long
     */
    public static Long asLong(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return Long.valueOf((String) value);
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a number");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Long
     */
    public static Long asLong(Map<String, Object> map, String name, Long defaultValue) {
        Long value = defaultValue;
        try {
            value = asLong(map, name);
        } catch (Exception e) {
            ///do nothing
        }
        return value;
    }

    /**
     * ���������� ��������, ������� ����� ��� Integer
     */
    public static Integer asInteger(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Double.valueOf((String) value).intValue();
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a number");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Double
     */
    public static Double asDouble(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a number");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� BigDecimal
     */
    public static BigDecimal asBigDecimal(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Long || value instanceof Integer) {
            return BigDecimal.valueOf(((Number) value).longValue());
        } else if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a number");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� BigDecimal ��� ��������
     * ��-���������
     */
    public static BigDecimal asBigDecimal(Map<String, Object> map, String name, BigDecimal defaultValue) {
        try {
            return asBigDecimal(map, name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Number
     */
    public static Number asNumber(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        } else if (value instanceof Number) {
            return (Number) value;
        } else {
            throw new IllegalArgumentException("Value by key '" + name + "' is not a number");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Number ��� �������� ��-���������
     */
    public static Integer asInteger(Map<String, Object> map, String name, Integer defaultValue) {
        try {
            return asInteger(map, name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Boolean
     */
    public static boolean asBoolean(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            int intValue = ((Number) value).intValue();
            switch (intValue) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new IllegalArgumentException("Can not convert '" + value + "' to boolean");
            }
        } else {
            throw new IllegalArgumentException("Can not convert '" + value + "' to boolean");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Boolean
     */
    public static boolean asBoolean(Map<String, Object> map, String name) {
        Object value = map.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Value by key '" + name + "' is null");
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else if (value instanceof Number) {
            int intValue = ((Number) value).intValue();
            switch (intValue) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new IllegalArgumentException("Can not convert '" + value + "' to boolean");
            }
        } else {
            throw new IllegalArgumentException("Can not convert '" + value + "' to boolean");
        }
    }

    /**
     * ���������� ��������, ������� ����� ��� Boolean ��� �������� ��-���������
     */
    public static boolean asBoolean(Map<String, Object> map, String name, boolean defaultValue) {
        try {
            return asBoolean(map, name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * ���������� ��������, ��� �� ����, ���� ������� ��-���������, ���� ��� ���
     * � �����
     */
    public static Object getDefault(Map<String, Object> map, String name, Object defaultValue) {
        Object value = map.get(name);
        return value == null ? defaultValue : value;
    }

    public static String asString(Map<String, Object> map, String name) {
        if (!map.containsKey(name) || map.get(name) == null) {
            return "";
        } else {
            return map.get(name).toString();
        }
    }

    /**
     * ���������� �������� ��� ������, ��� �� ����, ���� ������� ��-���������,
     * ���� ��� ��� � �����
     */
    public static String asStringDefault(Map<String, Object> map, String name, Object defaultValue) {
        if (!map.containsKey(name) || map.get(name) == null) {
            return defaultValue.toString();
        } else {
            return map.get(name).toString();
        }
    }

    public static Integer asInteger(Object obj, Integer defaultValue) {
        Integer result = defaultValue;
        if (obj != null) {

            try {
                result = ((Number) obj).intValue();
            } catch (Exception e) {

            }
        }
        return result;
    }


    public static void generateCallStatus(Map<String, Object> data, Number code){
        if(data!=null){
            data.put("Status", code);
        }
    }

    public static boolean isParamEmpty(Map<String, Object> data, String parameter){
        if(data.get(parameter) instanceof String){
            return data.get(parameter)==null || "".equals(data.get(parameter));
        } else {
            return data.get(parameter)==null;  //?
        }
    }
}
