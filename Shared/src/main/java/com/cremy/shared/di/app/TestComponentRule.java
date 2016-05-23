package com.cremy.shared.di.app;

import android.content.Context;

import com.cremy.shared.App;
import com.cremy.shared.data.DataManager;
import com.cremy.shared.di.app.component.AppTestComponent;
import com.cremy.shared.di.app.component.DaggerAppTestComponent;
import com.cremy.shared.di.app.module.AppTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private final AppTestComponent testComponent;
    private final Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
        App app = App.get(context);
        testComponent = DaggerAppTestComponent.builder()
                .appTestModule(new AppTestModule(app))
                .build();
    }

    public AppTestComponent getAppTestComponent() {
        return testComponent;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getMockDataHelper() {
        return testComponent.provideDataManager();
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                App application = App.get(mContext);
                application.setComponent(testComponent);
                base.evaluate();
                application.setComponent(null);
            }
        };
    }
}