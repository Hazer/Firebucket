package firebucket;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cremy.firebucket.R;
import com.cremy.firebucket.ui.view.RegisterActivity;
import com.cremy.shared.data.model.User;
import com.cremy.shared.di.app.TestComponentRule;
import com.cremy.sharedtest.utils.FirebaseOperationIdlingResource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by remychantenay on 21/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {


    //region Component and Rule set
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<RegisterActivity> main =
            new ActivityTestRule<>(RegisterActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger AppTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);
    //endregion

    //region Views
    @Test
    public void checkViewsAreDisplayed() throws InterruptedException {

        main.launchActivity(null);
        onView(withText(com.cremy.shared.R.string.register)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.register_subtitle)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.button_go)).check(matches(isDisplayed()));

        onView(withId(R.id.registerForm)).check(matches(isDisplayed()));
        onView(withId(R.id.registerFormEmailTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.registerFormPasswordTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.registerFormButton)).check(matches(isDisplayed()));

    }
    //endregion

    //region Empty credentials
    @Test
    public void checkClickButtonWithEmptyCredentials() throws InterruptedException {

        main.launchActivity(null);

        onView(withId(R.id.registerFormButton)).perform(ViewActions.click());
        TextInputLayout textInputLayoutEmail = (TextInputLayout) main.getActivity().findViewById(R.id.registerFormEmailTextInputLayout);
        assert textInputLayoutEmail.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_email));

        TextInputLayout textInputLayoutPassword = (TextInputLayout) main.getActivity().findViewById(R.id.registerFormPasswordTextInputLayout);
        assert textInputLayoutPassword.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_password));
    }
    //endregion
}