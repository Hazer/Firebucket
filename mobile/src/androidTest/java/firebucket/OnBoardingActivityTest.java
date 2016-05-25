package firebucket;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cremy.firebucket.R;
import com.cremy.firebucket.ui.view.OnBoardingActivity;
import com.cremy.shared.di.app.TestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by remychantenay on 21/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class OnBoardingActivityTest {


    //region Component and Rule set
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<OnBoardingActivity> main =
            new ActivityTestRule<>(OnBoardingActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger AppTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);
    //endregion

    //region Views
    @Test
    public void checkViewsAreDisplayed() throws InterruptedException {

        main.launchActivity(null);
        onView(withText(com.cremy.shared.R.string.login)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.onboarding_register_link)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.onboarding_title)).check(matches(isDisplayed()));

        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()));
        onView(withId(R.id.linkRegister)).check(matches(isDisplayed()));

    }
    //endregion

    //region Events
    @Test
    public void checkButtonLoginClickEvent() throws InterruptedException {

        main.launchActivity(null);
        onView(withId(R.id.buttonLogin))
                .perform(ViewActions.click());

        onView(withId(R.id.rootViewLogin))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkLinkRegisterClickEvent() throws InterruptedException {

        main.launchActivity(null);
        onView(withId(R.id.linkRegister))
                .perform(ViewActions.click());

        onView(withId(R.id.rootViewRegister))
                .check(matches(isDisplayed()));
    }
    //endregion

}