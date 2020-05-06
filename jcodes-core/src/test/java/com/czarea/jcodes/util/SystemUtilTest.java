package com.czarea.jcodes.util;

import org.junit.Assert;
import org.junit.Test;

public class SystemUtilTest {

    @Test
    public void getEnv() {
        String home = SystemUtil.getEnv("GRADLE_HOME");
        Assert.assertNotNull(home);
    }
}
