package cn.lvji.codes.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class GradleUtilTest {

    @Test
    public void executeGradleCmd() throws IOException, InterruptedException {
        String response = GradleUtil.executeGradleCmd("D:\\codes\\test2\\lvji-codes-test", "createDirs");
        assertNotNull(response);
    }
}