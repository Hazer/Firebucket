package firebucket;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cremy.firebucket.R;
import com.cremy.firebucket.ui.view.MainActivity;
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

/**
 * Created by remychantenay on 21/05/2016.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    //region Component and Rule set
    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, false, false);

    // TestComponentRule needs to go first to make sure the Dagger AppTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component).around(main);
    //endregion

    //region Test FloatingActionButton
    @Test
    public void checkFloatingActionButton() throws InterruptedException {
        main.launchActivity(null);
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }
    //endregion
}