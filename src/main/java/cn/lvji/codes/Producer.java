package cn.lvji.codes;

import cn.lvji.codes.config.Config;
import cn.lvji.codes.db.CodesDataSource;
import cn.lvji.codes.db.Table;
import cn.lvji.codes.db.TableExtractor;
import cn.lvji.codes.model.Codes;
import cn.lvji.codes.template.BeetlUtil;
import cn.lvji.codes.util.FileUtil;
import cn.lvji.codes.util.GradleUtil;
import cn.lvji.codes.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成器
 *
 * @author zhouzx
 */
public class Producer {
    private Codes codes;

    public void produceProject() throws IOException, InterruptedException {
        init();
        configGradle();
        copyConfig();
    }

    public void produceCodes() throws IOException {
        codes = Config.yamlInit();
        codes.getTemplate().getKeys().put("package", codes.getTemplate().getBasePackage());
        codes.getTemplate().getKeys().put("author", codes.getAuthor());
        LocalDateTime time = LocalDateTime.now();
        codes.getTemplate().getKeys().put("timestamp", time.toString());
        codes.getTemplate().getKeys().put("time", time.toLocalTime());
        codes.getTemplate().getKeys().put("date", time.toLocalDate());

        CodesDataSource.getInstance().init(codes);
        List<String> tables = codes.getTemplate().getTables();

        StringBuffer codeOutDir = new StringBuffer(codes.getTemplate().getOutPath());
        codeOutDir.append(File.separator).append(codes.getTemplate().getBasePackage().replaceAll("\\.", "/"));
        Path path = Paths.get(codeOutDir.toString());

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        tables.forEach(item -> {
            Table table = TableExtractor.getTable(item);
            try {
                produceFile(codes.getTemplate().getDir(), codes.getTemplate().getOutPath(), table);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected void produceFile(String templateDir, String outPath, Table table) throws Exception {
        List<File> files = FileUtil.getFileList(templateDir);
        for (int i = 0; i < files.size(); i++) {
            File item = files.get(i);
            System.out.println("开始生成数据库表：" + table.getName() + " 的 " + item.getAbsolutePath() + "模版代码");
            long start = System.currentTimeMillis();
            String outPutFileName = BeetlUtil.getFileName(item.getName(), table);
            String relativePath = FileUtil.getRelativePath(templateDir, item.getAbsolutePath());
            String bizPath = relativePath.substring(0, relativePath.lastIndexOf("\\"));
            StringBuffer outputPath = new StringBuffer(outPath);
            String basePackage = codes.getTemplate().getBasePackage();
            if (!StringUtils.isEmpty(basePackage)) {
                outputPath.append(File.separator).append(basePackage.replaceAll("\\.", "/"));
            }
            outputPath.append(bizPath).append(File.separator);
            Path outputDir = Paths.get(outputPath.toString());
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
            outputPath.append(outPutFileName);
            try {
                BeetlUtil.produceCodes(table, templateDir, relativePath.substring(1), outputPath.toString(), codes.getTemplate().getKeys());
            } catch (IOException e) {
            }
            long end = System.currentTimeMillis();
            System.out.println("生成数据库表：" + table.getName() + " 的 " + item.getAbsolutePath() + "模版代码成功，耗时：" + (end - start) + " ms");
        }
    }

    protected void init() throws IOException, InterruptedException {
        codes = Config.yamlInit();
        String projectPath = codes.getProject().getBaseDir() + File.separator + codes.getProject().getName();
        String createProjectResponse = GradleUtil.executeGradleCmd(projectPath, "init");
        System.out.println(createProjectResponse);
        new Thread(() -> {
            String createDirsResponse = null;
            try {
                createDirsResponse = GradleUtil.executeGradleCmd(projectPath, "createDirs");
                System.out.println(createDirsResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    protected void configGradle() throws IOException {
        Map<String, Object> map = new HashMap<>(2);
        map.put("groupId", StringUtils.wrapperSingleQuote(codes.getProject().getGroupId()));
        map.put("version", StringUtils.wrapperSingleQuote(codes.getProject().getVersion()));
        String buildGradle = codes.getProject().getBaseDir() + "/" + codes.getProject().getName() + "/build.gradle";
        BeetlUtil.produce(System.getProperty("user.dir"), "templates/boot.template", buildGradle, map);
    }

    protected void copyConfig() throws IOException {
        String configTemplate = System.getProperty("user.dir") + "/config";
        String projectConfig = codes.getProject().getBaseDir() + "/" + codes.getProject().getName() + "/config";
        FileUtil.copyDir(configTemplate, projectConfig);
    }


}
