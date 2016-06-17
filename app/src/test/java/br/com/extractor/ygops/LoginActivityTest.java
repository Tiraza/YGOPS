package br.com.extractor.ygops;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import br.com.extractor.ygops.view.activity.login.LoginActivity;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    @Test
    public void testNotNull() throws Exception {
        Assert.assertTrue(Robolectric.buildActivity(LoginActivity.class).create().get() != null);
    }

}
