package cn.lvji.codes.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExecutorCmdUtilTest {

    @Test
    public void cmd() throws IOException, InterruptedException {
        String initCmd = "E:\\gradle\\gradle-4.6\\bin\\gradle.bat init";
        String str = ExecutorCmdUtil.cmd(initCmd, null, "E:/codes/test");
        System.out.println(str);
    }
}