package cn.lvji.codes.util;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * beetl工具类
 *
 * @author zhouzx
 */
public class BeetlUtil {
    /**
     * 根据模版生成文件
     *
     * @param templatePath 模版文件名
     * @param outPath      输出文件名
     * @param map          模版变量
     * @throws IOException
     */
    public static void generator(String templatePath, String outPath, Map<String, Object> map) throws IOException {
        FileResourceLoader resourceLoader = new FileResourceLoader(System.getProperty("user.dir"));
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
        Template template = groupTemplate.getTemplate(templatePath, resourceLoader);
        template.binding(map);
        FileOutputStream outputStream = new FileOutputStream(outPath);
        template.renderTo(outputStream);
    }
}
