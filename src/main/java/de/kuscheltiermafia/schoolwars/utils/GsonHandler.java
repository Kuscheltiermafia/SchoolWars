package de.kuscheltiermafia.schoolwars.utils;

import com.google.gson.Gson;
import de.kuscheltiermafia.schoolwars.SchoolWars;

import java.lang.reflect.Type;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarFile;

public class GsonHandler {
    public static final Gson GSON = new Gson();

    /**
     *
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     *
     *
     * @param json
     * @param ofT
     * @return
     */
    public static <T> T fromJson(String json, Type ofT) {
        return GSON.fromJson(json, ofT);
    }

    /**
     *
     * @param directoryPath
     * @param classOfT
     * @return
     * @param <T>
     */
    public static <T> Collection<T> fromJsonToCollection(String directoryPath, Class<T> classOfT) {
        Map<String, T> map = fromJsonToMap(directoryPath, classOfT);
        if (map == null) {
            return new ArrayList<>();
        }
        return map.values();
    }

    /**
     *
     * 
     * @param directoryPath
     * @param typeOfT
     * @return
     */
    public static <T> Map<String, T> fromJsonToMap(String directoryPath, Class<T> typeOfT) {
        HashMap<String, T> map = new HashMap<>();

        try {
            ClassLoader cl = SchoolWars.getPlugin().getClass().getClassLoader();
            Enumeration<URL> resources = cl.getResources(directoryPath);

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();

                if ("jar".equals(url.getProtocol())) {
                    String filePath = url.getPath();

                    String jarPath = filePath.substring(5, filePath.indexOf("!"));

                    try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {
                        jar.stream()
                                .filter(entry -> entry.getName().startsWith(directoryPath) && entry.getName().endsWith(".json"))
                                .forEach(entry -> {
                                    String storageID = entry.getName().substring(directoryPath.length(), entry.getName().length() - 5).replace("/", "");

                                    try (InputStream in = SchoolWars.getPlugin().getResource(entry.getName())) {
                                        if (in != null) {
                                            Object obj = GSON.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), typeOfT);
                                            map.put(storageID, typeOfT.cast(obj));

                                        }
                                    } catch (Exception e) {
                                        SchoolWars.getPlugin().getLogger().warning("Failed to load object: " + entry.getName());
                                        e.printStackTrace();
                                    }
                                });
                    } catch (Exception e) {
                        SchoolWars.getPlugin().getLogger().warning("Failed to read jar file for resources: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            SchoolWars.getPlugin().getLogger().warning("Failed to enumerate resources for path '" + directoryPath + "': " + e.getMessage());
            e.printStackTrace();
        }

        return map;
    }
}

