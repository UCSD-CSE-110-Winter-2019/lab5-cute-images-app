package edu.ucsd.cse110.cuteimagesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import edu.ucsd.cse110.cuteimagesapp.images.ImageService;
import edu.ucsd.cse110.cuteimagesapp.images.ImageServiceFactory;
import edu.ucsd.cse110.cuteimagesapp.images.dogs.DogImageServiceAdapter;

import static edu.ucsd.cse110.cuteimagesapp.SharedPreferencesAdapter.Preference.IMAGE_SERVICE_TYPE;
import static edu.ucsd.cse110.cuteimagesapp.images.ImageService.ImageServiceType.DOG;

public class MainActivity extends AppCompatActivity {
    private ImageService imageService;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private RequestOptions requestOptions;
    private CountingIdlingResource idlingResourceCounter = new CountingIdlingResource(MainActivity.class.getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        ImageServiceFactory.put(DOG.name(), new DogImageServiceAdapter());

//        In case of 'java.io.IOException: No original dex files found for dex location'
//        Go to: Settings > Build, Execution, Deployment > Instant run
//          and uncheck "Enable Instant Run to hot swap code/resource changes on deploy"
        SharedPreferencesAdapter.init(getBaseContext());

        // TODO: use registerOnSharedPreferenceChangeListener in the SharedPreferencesAdapter to make sure that you always update
        // the imageService and call getNewImage whenever the IMAGE_SERVICE_TYPE changes in the shared preferences
        // there is already a variable called "listener" that you can assign an implementation to
        // NOTE: if you don't assign it to that variable the listener will get garbage collected after the onCreate method is done!

        initImageService();

        Button btnEditPreferences = findViewById(R.id.buttonEditPreferences);
        btnEditPreferences.setOnClickListener(view -> startEditPreferencesActivity());

        Button btnGetNewImage = findViewById(R.id.buttonGetNewImage);

        btnGetNewImage.setOnClickListener(v -> getNewImage());
        getNewImage();
    }

    private void initImageService() {
        String imageServiceType = SharedPreferencesAdapter.getInstance().getValue(IMAGE_SERVICE_TYPE, DOG.name());
        System.out.println("setting image service type: " + imageServiceType);
        imageService = ImageServiceFactory.create(imageServiceType);
    }

    private void startEditPreferencesActivity() {
        startActivity(new Intent(this, EditPrefrencesActivity.class));
    }

    private void getNewImage() {
        idlingResourceCounter.increment();
        imageService.getRandomImageUrl(url -> {
            System.out.println("new url: " + url);
            Glide.with(this).load(url).apply(requestOptions).into((ImageView) findViewById(R.id.image));
            findViewById(R.id.image).setContentDescription(url);
            idlingResourceCounter.decrement();
        });
    }

    public CountingIdlingResource getIdlingResourceCounter() {
        return idlingResourceCounter;
    }
}
