package firebucket;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cremy.firebucket.R;
import com.cremy.firebucket.ui.view.LoginActivity;
import com.cremy.shared.di.app.TestComponentRule;
import com.cremy.sharedtest.utils.FirebaseOperationIdlingResource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
public class LoginActivityTest {

    private final static String USER_EMAIL_SUCCESS = "test@test.com";
    private final static String USER_PASSWORD_SUCCESS = "testpassword";

    private final static String USER_EMAIL_FAIL = "fail@fail.com";
    private final static String USER_PASSWORD_FAIL = "failpassword";

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

        main.launchActivity(null);
        onView(withText(com.cremy.shared.R.string.signin)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.login_subtitle)).check(matches(isDisplayed()));
        onView(withText(com.cremy.shared.R.string.button_go)).check(matches(isDisplayed()));

        onView(withId(com.cremy.firebucket.R.id.loginForm)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormEmailTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormPasswordTextInputLayout)).check(matches(isDisplayed()));
        onView(withId(com.cremy.firebucket.R.id.loginFormButton)).check(matches(isDisplayed()));

    }
    //endregion

    //region Empty credentials
    @Test
    public void checkClickButtonWithEmptyCredentials() throws InterruptedException {


        main.launchActivity(null);

        onView(withId(com.cremy.firebucket.R.id.loginFormButton)).perform(ViewActions.click());
        TextInputLayout textInputLayoutEmail = (TextInputLayout) main.getActivity().findViewById(R.id.loginFormEmailTextInputLayout);
        assert textInputLayoutEmail.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_email));

        TextInputLayout textInputLayoutPassword = (TextInputLayout) main.getActivity().findViewById(R.id.loginFormPasswordTextInputLayout);
        assert textInputLayoutPassword.getError().toString().equals(main.getActivity().getResources().getString(R.string.error_login_invalid_password));
    }
    //endregion

    //region Invalid credentials
    @Test
    public void checkClickButtonWithInvalidCredentials() throws InterruptedException {

        main.launchActivity(null);

        // We prepare the IdlingResource for the firebase asynchronous auth call
        final FirebaseOperationIdlingResource firebaseAuthIdlingResource = new FirebaseOperationIdlingResource();
        Espresso.registerIdlingResources(firebaseAuthIdlingResource);

        component.getMockDataHelper().signInWithEmailAndPassword(USER_EMAIL_FAIL, USER_PASSWORD_FAIL, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                firebaseAuthIdlingResource.onOperationEnded();
                assert !task.isSuccessful();
            }
        });

        firebaseAuthIdlingResource.onOperationStarted();
        Espresso.unregisterIdlingResources(firebaseAuthIdlingResource);
    }
    //endregion

    //region Valid credentials
    @Test
    public void checkClickButtonWithValidCredentials() throws InterruptedException {

        main.launchActivity(null);

        // We prepare the IdlingResource for the firebase asynchronous auth call
        final FirebaseOperationIdlingResource firebaseAuthIdlingResource = new FirebaseOperationIdlingResource();
        Espresso.registerIdlingResources(firebaseAuthIdlingResource);

        component.getMockDataHelper().signInWithEmailAndPassword(USER_EMAIL_SUCCESS, USER_PASSWORD_SUCCESS, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                firebaseAuthIdlingResource.onOperationEnded();
                assert task.isSuccessful();
            }
        });

        firebaseAuthIdlingResource.onOperationStarted();
        Espresso.unregisterIdlingResources(firebaseAuthIdlingResource);
    }
    //endregion




}