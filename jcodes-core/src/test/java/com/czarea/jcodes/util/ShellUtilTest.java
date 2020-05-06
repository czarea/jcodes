package com.czarea.jcodes.util;

import org.junit.Test;

import java.io.IOException;

public class ShellUtilTest {

    @Test
    public void cmd() throws IOException, InterruptedException {
        String initCmd = "E:\\gradle\\gradle-4.6\\bin\\gradle.bat propertiesInit";
        String str = ShellUtil.cmd(initCmd, null, "E:/codes/test");
        System.out.println(str);
    }
}
