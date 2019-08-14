package cn.czarea.jcodes.util;

import java.io.File;
import java.io.IOException;

/**
 * gradle工具类
 *
 * @author zhouzx
 */
public class GradleUtil {
    public static String GRADLE_HOME = SystemUtil.getEnv("GRADLE_HOME");

    /**
     * 执行gradle命令
     *
     * @param dir 命令目录
     * @param cmd 命令
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String executeGradleCmd(String dir, String cmd) throws IOException, InterruptedException {
        String realCmd = GRADLE_HOME + File.separator + "bin" + File.separator + "gradle.bat " + cmd;
        return ShellUtil.cmd(realCmd, null, dir);
    }

}
