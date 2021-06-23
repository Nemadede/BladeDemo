package com.bladeDemo.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Map;

public class JSON {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSON.class);

    @Retention(RetentionPolicy.RUNTIME)
    @Target({java.lang.annotation.ElementType.FIELD})
    public static @interface FileTransient {
    }

    public static class FileExclusionPolicy
            implements ExclusionStrategy {
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getAnnotation(JSON.FileTransient.class) != null;
        }

        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    }

    private static final GsonBuilder gsonBuilder = createGsonBuilder();
    private static final GsonBuilder gsonBuilderPretty = createGsonBuilder().setPrettyPrinting();
    private static final GsonBuilder gsonBuilderFile = createGsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(new FileExclusionPolicy());
    //private static final GsonBuilder gsonBuilderPrettyNoNaN = createGsonBuilder().registerTypeAdapterFactory(new GsonNanToNullTypeAdaptorFactory()).setPrettyPrinting();

    private static GsonBuilder createGsonBuilder() {
        return new GsonBuilder(); //.registerTypeAdapterFactory(new GsonEnumTypeAdapterFactory());
    }

    public static void registerAdapter(Type type, Object adapter) {
        gsonBuilder.registerTypeAdapter(type, adapter);
        gsonBuilderPretty.registerTypeAdapter(type, adapter);
        //gsonBuilderPrettyNoNaN.registerTypeAdapter(type, adapter);
        gsonBuilderFile.registerTypeAdapter(type, adapter);
    }

    public static Gson gson() {
        return gsonBuilder.create();
    }

    public static Gson gsonPretty() {
        return gsonBuilderPretty.create();
    }

    public static String toJsonString(Object o){
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static Gson gsonForFile() {
        return gsonBuilderFile.create();
    }

    public static String sanitizeJson(String json) throws JSONException {

        JSONTokener tokener = new JSONTokener(json);
        StringWriter writer = new StringWriter();
        while (tokener.more()) {
            char c = tokener.next();
            if ((c == '"') || (c == '\'')) {
                String stringToken = tokener.nextString(c);
                writer.write(JSONObject.quote(stringToken));
            } else if (c == ',') {
                char afterComma = tokener.nextClean();
                if ((afterComma == '}') || (afterComma == ']')) {
                    writer.write(afterComma);
                } else {
                    writer.write(c);
                    tokener.back();
                }
            } else if (c == '/') {
                c = tokener.next();
                if (c == '/') {
                    tokener.skipTo('\n');
                } else {
                    if (c == '*') {
                        while (tokener.more()) {
                            c = tokener.skipTo('*');
                            if (c != '*') {
                                throw tokener.syntaxError("Unclosed comment");
                            }
                            tokener.next();
                            c = tokener.next();
                            if (c == '/') {
                                break;
                            }
                            tokener.back();
                        }
                    }
                    throw tokener.syntaxError("Invalid character: " + c + "after '/'");
                }
            } else {
                writer.write(c);
            }
        }
        return writer.toString();
    }

    public static <T> T parse(String s, Class<T> classOfT) {
        try {
            return (T) gson().fromJson(s, classOfT);
        } catch (JsonSyntaxException e) {
            LOGGER.error("FAILED TO PARSE THE FOLLOWING MESSAGE");
            LOGGER.error(s.substring(0, Math.min(s.length(), 300)));
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public static <T> T parse(String s, TypeToken<T> typeToken) {
        try {
            String sanitized = sanitizeJson(s);
            return (T) gson().fromJson(sanitized, typeToken.getType());
        } catch (JSONException e) {
            throw new JsonSyntaxException("Sanitization failed", e);
        } catch (JsonSyntaxException e) {
            LOGGER.error("FAILED TO PARSE THE FOLLOWING MESSAGE");
            LOGGER.error(s.substring(0, Math.min(s.length(), 300)));
            LOGGER.error(e.getMessage());
            throw e;
        }
    }

    public static <T> T parseFile(String path, Class<T> classOfT)
            throws IOException {
        return (T) parseFile(new File(path), classOfT);
    }

    public static <T> T parseFile(File file, Class<T> classOfT)
            throws IOException {
        try {
            T t = parse(FileUtils.readFileToString(file, "utf8"), classOfT);
            if (t == null) {
                throw new IOException("Could not read any data from file " + file.getAbsolutePath() + ", is it empty ?");
            }
            return t;
        } catch (IOException e) {
            LOGGER.error("Error while parsing JSON file " + file.getAbsolutePath());
            throw e;
        }
    }

    public static Map<String, String> parseToMap(String s) {
        return (Map) gson().fromJson(s, Map.class);
    }

    public static Map<String, Object> parserToMap(String s) {
        return (Map) gson().fromJson(s, Map.class);
    }

    public static String json(Object o) {
        if (o == null) {
            return null;
        }
        return gson().toJson(o);
    }

    public static String pretty(Object o) {
        if (o == null) {
            return null;
        }
        return gsonPretty().toJson(o);
    }

    public static String prettyO(Object o) {
        if (o == null) {
            return null;
        }
        return gsonPretty().toJson(o);
    }


    public static String prettyAsFile(Object o) {
        if (o == null) {
            return null;
        }
        return gsonForFile().toJson(o);
    }

//    public static void prettyToFile(Object o, File f)
//            throws IOException
//    {
//        if (o == null) {
//            throw new IllegalArgumentException("Cannot serialize null in file.");
//        }
//        String json = gsonForFile().toJson(o);
//        PathDirectoryUtil.writeFileUTF8(f, json);
//    }

    public static JsonBuilder kv(String k, Object v) {
        return new JsonBuilder().kv(k, v);
    }

    public static class JsonBuilder {
        final Gson gson = JSON.gsonPretty();
        final JsonObject json = new JsonObject();

        public JsonBuilder kv(String k, Object v) {
            this.json.add(k, this.gson.toJsonTree(v));
            return this;
        }

        public JsonObject get() {
            return this.json;
        }
    }

//    public static String prettyNoNaN(Object o)
//    {
//        if (o == null) {
//            return null;
//        }
//        return gsonBuilderPrettyNoNaN.create().toJson(o);
//    }

    public static void prettySyso(Object o) {
        if (o == null) {
            return;
        }
        System.out.println(pretty(o));
    }

    public static <T> T deepCopy(T o) {
        if (o == null) {
            return null;
        }
        return (T) parse(json(o), o.getClass());
    }

    public static <T> T deepCopyExcept(T o, String... fields) {
        if (o == null) {
            return null;
        }
        JsonObject obj = (JsonObject) parse(json(o), JsonObject.class);
        for (String f : fields) {
            obj.remove(f);
        }
        return (T) parse(json(obj), o.getClass());
    }

    public static abstract interface Adapter<T>
            extends JsonDeserializer<T>, JsonSerializer<T> {
    }
}
