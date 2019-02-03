package edu.ucsd.cse110.cuteimagesapp.test.steps;

import android.content.Context;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ucsd.cse110.cuteimagesapp.EditPrefrencesActivity;
import edu.ucsd.cse110.cuteimagesapp.MainActivity;
import edu.ucsd.cse110.cuteimagesapp.R;
import edu.ucsd.cse110.cuteimagesapp.SharedPreferencesAdapter;
import edu.ucsd.cse110.cuteimagesapp.images.ImageServiceFactory;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static edu.ucsd.cse110.cuteimagesapp.SharedPreferencesAdapter.Preference.IMAGE_SERVICE_TYPE;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;

public class MainActivitySteps {

    private ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity
            .class);
    public static final String FAKE_URL = "fake-%s-image-url";
    public static final String TEST_SERVICE = "%s_TEST_SERVICE";

    public MainActivitySteps() {
    }

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void tearDown() {
        mActivityTestRule.getActivity().finish();
        Intents.release();
    }

    @Given("^a fake (.*) image service$")
    public void aFakeImageService(String serviceType) {
        String testService = String.format(TEST_SERVICE, serviceType);
        String fakeUrl = String.format(FAKE_URL, serviceType);
        ImageServiceFactory.put(testService, c -> c.accept(fakeUrl));

        Context context = getInstrumentation().getTargetContext();
        SharedPreferencesAdapter.init(context);
        SharedPreferencesAdapter.getInstance().setValue(IMAGE_SERVICE_TYPE, testService);
    }

    @And("^a main activity$")
    public void thereIsAMainActivity() throws Throwable {
        mActivityTestRule.launchActivity(null);
        assertThat(mActivityTestRule.getActivity(), notNullValue());
    }

    @Then("^the user should see a fake (.*) image$")
    @And("^the user sees a fake (.*) image$")
    public void weShouldSeeADogImage(String serviceType) throws Throwable {
        String fakeUrl = String.format(FAKE_URL, serviceType);

        CountingIdlingResource componentIdlingResource = mActivityTestRule.getActivity().getIdlingResourceCounter();
        IdlingRegistry.getInstance().register(componentIdlingResource);

        onView(withId(R.id.image)).check(matches(isDisplayed()))
                .check(matches(withContentDescription(containsString(fakeUrl))));
    }

    @When("^the user clicks the edit preferences button$")
    public void theUserClicksTheEditPreferencesButton() throws Throwable {
        onView(withId(R.id.buttonEditPreferences))
                .check(matches(isDisplayed()))
                .perform(click());
        intended(hasComponent(EditPrefrencesActivity.class.getName()));
    }

}
