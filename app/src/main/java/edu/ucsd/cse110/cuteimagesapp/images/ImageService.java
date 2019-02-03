package edu.ucsd.cse110.cuteimagesapp.images;

import java.util.function.Consumer;

public interface ImageService {
    void getRandomImageUrl(Consumer<String> c);

    enum ImageServiceType {
        DOG,
        CAT
    }
}
