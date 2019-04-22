package com.example.a86182.icamera;

import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)

public class PlatformUtilTest {
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.a86182.icamera", appContext.getPackageName());
    }

    @Test
    public void isInstallApp() {
        Context c = InstrumentationRegistry.getTargetContext();
        PlatformUtil p = new PlatformUtil();
        boolean R = p.judgeInstalled(c,p.PACKAGE_MOBILE_QQ);
        assertEquals(R,false);
    }
}
