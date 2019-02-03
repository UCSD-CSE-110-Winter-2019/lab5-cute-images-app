package edu.ucsd.cse110.cuteimagesapp.images.dogs;

import android.support.annotation.NonNull;

import java.util.function.Consumer;

import edu.ucsd.cse110.cuteimagesapp.images.ImageService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogImageServiceAdapter implements ImageService {
    private DogImageService dogImageService;


    public DogImageServiceAdapter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DogImageService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        dogImageService = retrofit.create(DogImageService.class);
    }

    /**
     * This method asynchronously calls a remote API to fetch a random image
     *
     * @param c consumer that will receive the new random image url as String
     */
    public void getRandomImageUrl(Consumer<String> c) {
        Callback<DogImageService.DogImageServiceResponse> callback = new Callback<DogImageService.DogImageServiceResponse>() {
            @Override
            public void onResponse(@NonNull Call<DogImageService.DogImageServiceResponse> call, @NonNull Response<DogImageService.DogImageServiceResponse> response) {
                try {
                    c.accept(response.body() != null ? response.body().message : null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DogImageService.DogImageServiceResponse> call, Throwable t) {
                t.printStackTrace();
            }
        };

        Call<DogImageService.DogImageServiceResponse> randomDogPicture = dogImageService.getRandomDogPicture();
        randomDogPicture.enqueue(callback);
    }
}
