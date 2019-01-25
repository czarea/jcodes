package cn.lvji.jcodes.util;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class FileUtilTest {
    @Test
    public void testGetFileList() {
        List<File> files = FileUtil.getFileList("D:\\lvji_git\\lvji-codes\\templates\\java\\guide");
        files.stream().forEach(item -> {
            System.out.println("name is " + item.getName());
            String relativePath = FileUtil.getRelativePath("D:\\lvji_git\\lvji-codes\\templates\\java\\guide", item.getAbsolutePath());
            if (relativePath.contains("\\")) {
                System.out.println("relative path is " + relativePath.substring(0, relativePath.lastIndexOf("\\")));
            } else {
                System.out.println("relative path is " + relativePath);
            }
        });
    }


}