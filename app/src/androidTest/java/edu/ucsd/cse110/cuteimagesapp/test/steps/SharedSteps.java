package edu.ucsd.cse110.cuteimagesapp.test.steps;

import android.support.test.espresso.action.ViewActions;

import cucumber.api.java.en.And;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

public class SharedSteps {

    @And("^presses the back button$")
    public void pressesTheBackButton() throws Throwable {
        onView(isRoot()).perform(ViewActions.pressBack());
    }

}
