package edu.ucsd.cse110.cuteimagesapp.images.dogs;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogImageService {
    String BASE_URL = "https://dog.ceo/api/";

    @GET("breeds/image/random")
    Call<DogImageServiceResponse> getRandomDogPicture();


    class DogImageServiceResponse {
        String status;
        String message;

        public DogImageServiceResponse() {
        }
    }

}
