package edu.ucsd.cse110.cuteimagesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.ucsd.cse110.cuteimagesapp.SharedPreferencesAdapter.Preference;
import edu.ucsd.cse110.cuteimagesapp.images.ImageService;

import static edu.ucsd.cse110.cuteimagesapp.images.ImageService.ImageServiceType.DOG;

public class EditPrefrencesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prefrences);

        String currentPreferredImageServiceType = SharedPreferencesAdapter.getInstance()
                .getValue(Preference.IMAGE_SERVICE_TYPE, DOG.name());

        Spinner spinner = findViewById(R.id.spinner_image_service_types);
        List<String> items = Arrays.stream(ImageService.ImageServiceType.values())
                .map(Enum::name)
                .collect(Collectors.toList());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setSelection(items.indexOf(currentPreferredImageServiceType));

        // TODO: implement spinner.setOnItemSelectedListener to save the selected image service in the SharedPreferencesAdapter
    }
}
