package ru.itpark.soa;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ilavrentev on 25.11.2016.
 */
public class JSONUtils {


    /*public static void main(String[] args) throws IOException, XMLStreamException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Status", 0L);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> it = new HashMap<String, Object>();
        it.put("ID", 1000);
        it.put("Name", "vzcxv");
        it.put("SomeDate", new Date());
        list.add(it);
        map.put("Result", list);

        System.out.println(createJson(map));
    }*/

    private static String QUOTE = "\"";
    private static String DELIM = ":";
    private static String COMMA = ",";
    private static String NULL = "null";
    private static String DFPTR = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; //like 2016-11-25T03:00:00.000+0300

    /**
     * Creates JSON structure from Map
     */
    public static String createJSON(Map<String, Object> data) {
        return createJson(data);
    }

    /**
     * Creates Map structure from JSON string
     */
    public static Map<String, Object> parseJSON(String jsonStr) {
        return parseJson(jsonStr);
    }


    //inner logic:

    private static Map<String, Object> parseJson(String json) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (json == null || json.isEmpty()) {
            return result;
        }
        try {
            JSONObject ob = new JSONObject(json);
            result = getAllObjs(ob);
            Iterator<Map.Entry<String, Object>> it = result.entrySet().iterator();
            //режем ключи с null значениями
            while(it.hasNext()){
                Map.Entry<String, Object> en = it.next();
                if(en.getValue().equals(JSONObject.NULL)){
                    it.remove();
                    continue;
                }
            }
        } catch (Exception x) {
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getAllObjs(JSONObject ob) {
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            Iterator<String> it = ob.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                if(!getJsonObjByKey(key, ob).equals(JSONObject.NULL)) {
                    res.put(key, getJsonObjByKey(key, ob));
                }
            }
        } catch (Exception x) {
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DFPTR);
        for(Map.Entry<String, Object> en : res.entrySet()){
            if(en.getValue()!=null && en.getValue().toString().startsWith("#dt#")){
                try {
                    Date date = sdf.parse(en.getValue().toString().substring(4,en.getValue().toString().length()));
                    en.setValue(date);
                } catch (Exception x){
                }
            }
        }
        return res;
    }

    private static Object getJsonObjByKey(String key, JSONObject parent) {
        try {
            Object obj = parent.get(key);
            if (obj instanceof JSONArray) {
                JSONArray arr = (JSONArray) obj;
                List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < arr.length(); i++) {
                    Object arr_i = arr.get(i);
                    if (arr_i instanceof JSONObject) {
                        lst.add(getAllObjs((JSONObject) arr_i));
                    }
                }
                return lst;
            } else if (obj instanceof String) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("'#dt#'" + DFPTR);
                    obj = sdf.parse((String) obj);
                    return obj;
                } catch (Exception x) {
                    return deCodeEscapeSymbols((String) obj);
                }
            } else if(obj instanceof JSONObject){
                JSONObject o = (JSONObject)obj;
                return parseJSON(o.toString());
            } else {
                return parent.get(key);
            }
        } catch (Exception x) {
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static String createJson(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder("");
        Integer c = 0;
        Integer n = data.keySet().size();
        for (String key : data.keySet()) {
            if (data.get(key) == null) {
                sb.append(QUOTE).append(key).append(QUOTE).append(DELIM)
                        .append(NULL);
            } else {
                if (data.get(key) instanceof List) {
                    sb.append(QUOTE).append(key).append(QUOTE).append(DELIM)
                            .append(createJson((List) data.get(key)));
                } else if (data.get(key) instanceof Map) {
                    sb.append(QUOTE).append(key).append(QUOTE).append(DELIM)
                            .append(createJson((Map) data.get(key)));
                } else {
                    sb.append(QUOTE).append(key).append(QUOTE).append(DELIM)
                            .append(createJson(data.get(key)));
                }
            }
            if (!n.equals(c + 1)) {
                sb.append(COMMA);
            }
            c++;
        }
        return "{" + sb.toString() + "}";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static String createJson(List data) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < (data).size(); i++) {
            if (data.get(i) == null) {
                sb.append(NULL);
            } else {
                if (data.get(i) instanceof Map) {
                    sb.append(createJson((Map) data.get(i)));
                } else if (data.get(i) instanceof List) {
                    sb.append(createJson((List) data.get(i)));
                } else {
                    sb.append(createJson(data.get(i)));
                }
            }
            if (!new Integer((data).size() - 1).equals(i)) {
                sb.append(COMMA);
            }
        }
        return "[" + sb.toString() + "]";
    }

    private static String createJson(Object o) {
        if (o == null) {
            return NULL;
        }
        if (o instanceof Number) {
            return o.toString();
        }
        if (o instanceof String) {
            return QUOTE + codeEscapeSymbols(o.toString()) + QUOTE;
        }
        if (o instanceof Boolean) {
            return o.toString();
        }
        if (o instanceof Date) {
            Date d = (Date) o;
            DateFormat df = new SimpleDateFormat(DFPTR);
            return "\"#dt#" + df.format(d) + "\"";
        }
        return NULL;
    }

    private static String codeEscapeSymbols(String src) {
        src = src.replace("\\", "\\\\");
        src = src.replace("\n", "\\n");
        src = src.replace("\r", "\\r");
        src = src.replace("\b", "\\b");
        src = src.replace("\t", "\\t");
        src = src.replace("\"", "\\\"");
        src = src.replace("\'", "\\'");
        return src;
    }

    private static String deCodeEscapeSymbols(String src) {
        src = src.replace("\\\\", "\\");
        src = src.replace("\\n", "\n");
        src = src.replace("\\r", "\r");
        src = src.replace("\\b", "\b");
        src = src.replace("\\t", "\t");
        src = src.replace("\\\"", "\"");
        src = src.replace("\\\'", "\'");
        return src;
    }

}
