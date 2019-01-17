package cn.lvji.codes.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 文件工具类
 *
 * @author zhouzx
 */
public class FileUtil {
    /**
     * 目录拷贝
     *
     * @param sourcePath 源目录
     * @param targetPath 目标目录
     * @throws IOException
     */
    public static void copyDir(String sourcePath, String targetPath) throws IOException {
        File file = new File(sourcePath);
        String[] filePath = file.list();

        if (!(new File(targetPath)).exists()) {
            (new File(targetPath)).mkdir();
        }

        for (int i = 0; i < filePath.length; i++) {

            if (Files.isDirectory(Paths.get(sourcePath + File.separator + filePath[i]))) {
                copyDir(sourcePath + File.separator + filePath[i], targetPath + File.separator + filePath[i]);
            } else {
                Files.copy(Paths.get(sourcePath + File.separator + filePath[i]), Paths.get(targetPath + File.separator + filePath[i]));
            }
        }
    }
}
