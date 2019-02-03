package edu.ucsd.cse110.cuteimagesapp.images;

import java.util.HashMap;
import java.util.Map;

public class ImageServiceFactory {
    private static Map<String, ImageService> imageServiceMap = new HashMap<>();

    public static void put(String key, ImageService service) {
        imageServiceMap.put(key, service);
    }

    public static ImageService create(String key) {
        return imageServiceMap.get(key);
    }

}
