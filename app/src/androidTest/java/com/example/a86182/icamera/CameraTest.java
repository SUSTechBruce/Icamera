package com.example.a86182.icamera;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.FileProvider;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.test.espresso.intent.Intents.*;
import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CameraTest {

    private Uri uri;
    private File cameraSavePath;
    private Solo solo;
    Instrumentation I;
    MainActivity activity;
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        I = InstrumentationRegistry.getInstrumentation();
        activity = activityTestRule.getActivity();

        solo = new Solo(I, activity);
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testCamera() {
        ImageButton camera = (ImageButton) solo.getView(R.id.camera);
        solo.clickOnView(camera);
        TextView text = (TextView) solo.getView("message");
        assertEquals("camera starts", text.getText().toString());
    }
}