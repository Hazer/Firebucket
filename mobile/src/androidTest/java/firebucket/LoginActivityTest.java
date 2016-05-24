package firebucket;

import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cremy.firebucket.R;
import com.cremy.firebucket.ui.view.LoginActivity;
import com.cremy.shared.di.app.TestComponentRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by remychantenay on 21/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    //region Component and Rule set
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<LoginActivity> main =
            new ActivityTestRule<>(LoginActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger AppTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);
    //endregion

    //region Views
    @Test
    public void checkViewsAreDisplayed() throws InterruptedException {

        // 1. We set the rules and mock with Mockito
        Mockito.when(component.getMockDataHelper().ifUserExists())
                .thenReturn(false);

        // 2. We launch the activity and check the views
        main.launchActivity(null);
        onView(withText(com.cremy.shared.R.string.login)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.login_subtitle)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.signin)).check(matches(isDisplayed()));

        onView(withId(com.cremy.firebucket.R.id.loginForm)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormEmailTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormPasswordTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormButton)).check(matches(isDisplayed()));

    }
    //endregion

    //region Empty credentials
    @Test
    public void checkClickButtonWithEmptyEmailAndPassword() throws InterruptedException {

        // 1. We set the rules and mock with Mockito
        Mockito.when(component.getMockDataHelper().ifUserExists())
                .thenReturn(false);

        // 2. We launch the activity and check the views
        main.launchActivity(null);

        onView(withId(com.cremy.firebucket.R.id.loginFormButton)).perform(ViewActions.click());
        TextInputLayout textInputLayoutEmail = (TextInputLayout) main.getActivity().findViewById(R.id.loginFormEmailTextInputLayout);
        assert textInputLayoutEmail.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_email));

        TextInputLayout textInputLayoutPassword = (TextInputLayout) main.getActivity().findViewById(R.id.loginFormPasswordTextInputLayout);
        assert textInputLayoutPassword.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_password));
    }
    //endregion




}