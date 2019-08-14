package cn.czarea.jcodes.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

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
                Files.copy(Paths.get(sourcePath + File.separator + filePath[i]), Paths.get(targetPath + File.separator + filePath[i]), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * 递归获取目录下所有文件
     *
     * @param path 目录
     * @return 文件列表
     */
    public static List<File> getFileList(String path) {
        List<File> list = new ArrayList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    list.addAll(getFileList(files[i].getAbsolutePath()));
                } else {
                    list.add(files[i]);
                }
            }
        }
        return list;
    }


    /**
     * 获得targetPath相对于sourcePath的相对路径
     *
     * @param sourcePath : 原文件路径
     * @param targetPath : 目标文件路径
     * @return 相对路径
     */
    public static String getRelativePath(String sourcePath, String targetPath) {
        StringBuffer buf = new StringBuffer();
        if (targetPath.indexOf(sourcePath) == 0) {
            buf.append(targetPath.replace(sourcePath, ""));
        } else {
            String[] sourcePathArray = sourcePath.split("/");
            String[] targetPathArray = targetPath.split("/");
            if (targetPathArray.length >= sourcePathArray.length) {
                for (int i = 0; i < targetPathArray.length; i++) {
                    if (sourcePathArray.length > i && targetPathArray[i].equals(sourcePathArray[i])) {
                        continue;
                    } else {
                        for (int j = i; j < sourcePathArray.length; j++) {
                            buf.append("../");
                        }
                        for (; i < targetPathArray.length; i++) {
                            buf.append(targetPathArray[i] + "/");
                        }
                        break;
                    }
                }
            } else {
                for (int i = 0; i < sourcePathArray.length; i++) {
                    if (targetPathArray.length > i && targetPathArray[i].equals(sourcePathArray[i])) {
                        continue;
                    } else {
                        for (int j = i; j < sourcePathArray.length; j++) {
                            buf.append("../");
                        }
                        break;
                    }
                }
            }
        }
        return buf.toString();
    }
}
