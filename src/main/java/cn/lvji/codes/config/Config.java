package cn.lvji.codes.config;

import cn.lvji.codes.model.CodesConfig;
import cn.lvji.codes.util.StringUtils;
import cn.lvji.codes.util.SystemUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件
 *
 * @author zhouzx
 */
public class Config {
    public static CodesConfig init() throws IOException {
        Properties properties = new Properties();
        InputStream in = Config.class.getClassLoader().getResourceAsStream("codes.properties");
        properties.load(in);
        CodesConfig codesConfig = new CodesConfig();
        String dir = properties.getProperty("project.dir");
        if (StringUtils.isEmpty(dir)) {
            dir = System.getProperty("user.dir") + "codes";
        }
        codesConfig.setDir(dir);
        String name = properties.getProperty("project.name");
        if (StringUtils.isEmpty(name)) {
            name = "generate-codes-test";
        }
        codesConfig.setName(name);
        String home = properties.getProperty("gradle.home");
        if (StringUtils.isEmpty(home)) {
            home = SystemUtil.getEnv("gradle_home");
        }
        codesConfig.setHome(home);
        String groupId = properties.getProperty("groupId");
        if (StringUtils.isEmpty(groupId)) {
            groupId = "xx.xx";
        }
        codesConfig.setGroupId(StringUtils.wrapperSingleQuote(groupId));

        String version = properties.getProperty("version");
        if (StringUtils.isEmpty(version)) {
            version = "1.0";
        }
        codesConfig.setVersion(StringUtils.wrapperSingleQuote(version));

        return codesConfig;
    }

}
