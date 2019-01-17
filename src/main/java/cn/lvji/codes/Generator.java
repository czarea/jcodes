package cn.lvji.codes;

import cn.lvji.codes.config.Config;
import cn.lvji.codes.model.CodesConfig;
import cn.lvji.codes.util.BeetlUtil;
import cn.lvji.codes.util.ExecutorCmdUtil;
import cn.lvji.codes.util.FileUtil;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 生成器
 *
 * @author zhouzx
 */
public class Generator {
    private CodesConfig config;

    public void start() throws IOException, InterruptedException {
        init();
        configGradle();
        copyConfig();
    }

    protected void init() throws IOException, InterruptedException {
        config = Config.init();
        String gradleBinPath = config.getHome() + File.separator + "bin" + File.separator;
        String projectPath = config.getDir() + File.separator + config.getName() + File.separator;
        String initCmd = gradleBinPath + "gradle.bat init";
        String executorResponse = ExecutorCmdUtil.cmd(initCmd, null, projectPath);
        System.out.println(executorResponse);
        System.out.println("========================================================================");
        System.out.println("==================================== create dirs ====================================");
        String createDirsCmd = projectPath + "gradlew.bat createDirs";
        executorResponse = ExecutorCmdUtil.cmd(createDirsCmd, null, projectPath);
        System.out.println(executorResponse);
        System.out.println("========================================================================");
    }

    protected void configGradle() throws IOException {
        System.out.println("==================================== config build.gradle ====================================");
        Map<String, Object> map = new HashMap<>(2);
        map.put("groupId", config.getGroupId());
        map.put("version", config.getVersion());
        String buildGradle = config.getDir() + "/" + config.getName() + "/build.gradle";
        BeetlUtil.generator("boot.template", buildGradle, map);
        System.out.println("========================================================================");
    }

    protected void copyConfig() throws IOException {
        System.out.println("==================================== copy config files ====================================");
        String configTemplate = System.getProperty("user.dir") + "/config";
        String projectConfig = config.getDir() + "/" + config.getName() + "/config";
        FileUtil.copyDir(configTemplate, projectConfig);
        System.out.println("========================================================================");
    }
}
