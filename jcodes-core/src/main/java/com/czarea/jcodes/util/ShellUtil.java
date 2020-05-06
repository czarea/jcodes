package com.czarea.jcodes.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 执行系统命令
 *
 * @author zhouzx
 */
public class ShellUtil {


    /**
     * 执行系统命令
     *
     * @param cmd 命令
     * @return 响应文本
     * @throws IOException
     * @throws InterruptedException
     */
    public static String cmd(String cmd, String[] envp, String dir) throws IOException, InterruptedException {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Process process = Runtime.getRuntime().exec(cmd, envp, new File(dir));
        InputStream is = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        process.waitFor();
        StringBuffer stringBuffer = new StringBuffer();
        String str;
        while ((str = reader.readLine()) != null) {
            stringBuffer.append(str).append("\r\n");
        }
        return stringBuffer.toString();
    }
}
