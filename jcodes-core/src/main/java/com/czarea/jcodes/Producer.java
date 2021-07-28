package com.czarea.jcodes;

import com.czarea.jcodes.config.Config;
import com.czarea.jcodes.db.CodesDataSource;
import com.czarea.jcodes.db.Table;
import com.czarea.jcodes.db.TableExtractor;
import com.czarea.jcodes.model.Codes;
import com.czarea.jcodes.template.BeetlUtil;
import com.czarea.jcodes.util.FileUtil;
import com.czarea.jcodes.util.GradleUtil;
import com.czarea.jcodes.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 生成器
 *
 * @author zhouzx
 */
public class Producer {

    private Codes codes;
    private static final CodesDataSource DATA_SOURCE = CodesDataSource.getInstance();

    /**
     * 生产模版项目
     */
    public void produceProject(String config) throws Exception {
        codes = Config.yamlInit(config);
        String projectPath = codes.getProject().getBaseDir() + File.separator + codes.getProject().getName();
        String createProjectResponse = GradleUtil.executeGradleCmd(projectPath, "init");
        System.out.println(createProjectResponse);
        configGradle();
        copyConfig();
    }

    /**
     * 生产模版项目
     */
    public void produceProject() throws IOException, InterruptedException {
        init();
        configGradle();
        copyConfig();
    }

    /**
     * 生产模版代码
     */
    public void produceCodes() throws Exception {
        codes = null;
        codes = Config.yamlInit();
        baseProduceCodes(codes);
    }

    /**
     * 生产模版代码
     */
    public void produceCodes(String config) throws Exception {
        codes = null;
        codes = Config.yamlInit(config);
        baseProduceCodes(codes);
    }

    private void baseProduceCodes(Codes codes) throws Exception {
        codes.getTemplate().getKeys().put("package", codes.getTemplate().getBasePackage());
        codes.getTemplate().getKeys().put("commonPackage", codes.getTemplate().getCommonPackage());
        codes.getTemplate().getKeys().put("author", codes.getAuthor());
        LocalDateTime time = LocalDateTime.now();
        codes.getTemplate().getKeys().put("timestamp", time.toString());
        codes.getTemplate().getKeys().put("time", time.toLocalTime());
        codes.getTemplate().getKeys().put("date", time.toLocalDate());
        StringBuilder outputPath = new StringBuilder(codes.getTemplate().getOutPath());

        delDir(outputPath.toString());

        CodesDataSource.getInstance().init(codes);
        List<String> tables = codes.getTemplate().getTables();
        Path path = Paths
            .get(codes.getTemplate().getOutPath() + File.separator + codes.getTemplate().getBasePackage().replaceAll("\\.", "/"));

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        if (tables != null) {
            tables.removeAll(Collections.singleton(null));
        }

        if (tables == null || tables.isEmpty()) {
            Connection conn = null;
            try {
                conn = DATA_SOURCE.getConnection();
                String dbName = conn.getCatalog();
                tables = TableExtractor.getAllTables(dbName);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }

        assert tables != null;
        for (int i = 0; i < tables.size(); i++) {
            String item = tables.get(i);
            Table table = TableExtractor.getTable(item);
            if (table.getName() == null) {
                continue;
            }
            try {
                produceFile(codes.getTemplate().getDir(), codes.getTemplate().getOutPath(), table);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String basePackage = codes.getTemplate().getBasePackage();
        if (!StringUtils.isEmpty(basePackage)) {
            outputPath.append(File.separator).append(basePackage.replaceAll("\\.", "/"));
        }

        if (isMac()) {
            Runtime.getRuntime().exec("open " + outputPath);
        } else {
            java.awt.Desktop.getDesktop().open(new File(outputPath.toString()));
        }
    }

    private boolean isMac() {
        String os = System.getProperty("os.name");
        return os.toLowerCase().contains("mac os");
    }

    private boolean delDir(String filePath) {
        boolean flag = true;
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isFile()) {
                        f.delete();
                    }
                    if (f.isDirectory()) {
                        String fpath = f.getPath();
                        delDir(fpath);
                        f.delete();
                    }
                }
            }
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 删除输入目录
     */
    public void deleteOutput() throws IOException {
        codes = Config.yamlInit();
        Path path = Paths.get(codes.getTemplate().getOutPath());
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    protected void produceFile(String templateDir, String outPath, Table table)
        throws Exception {
        List<File> files = FileUtil.getFileList(templateDir);
        for (int i = 0; i < files.size(); i++) {
            File item = files.get(i);
            System.out.println("开始生成数据库表：" + table.getName() + " 的 " + item.getAbsolutePath() + "模版代码");
            long start = System.currentTimeMillis();
            String outPutFileName = BeetlUtil.getFileName(item.getName(), table.getClassName());
            String relativePath = FileUtil.getRelativePath(templateDir, item.getAbsolutePath());
            String bizPath = relativePath.substring(0, relativePath.lastIndexOf("/"));
            if (!isMac()) {
                relativePath = item.getAbsolutePath().substring(templateDir.length());
                bizPath = relativePath.substring(0, relativePath.lastIndexOf("\\"));
            }
            StringBuilder outputPath = new StringBuilder(outPath);
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
            codes.getTemplate().getKeys().put("className", table.getClassName());
            codes.getTemplate().getKeys().put("lowerClassName", StringUtils.toLowerCaseFirst(table.getClassName()));
            try {
                BeetlUtil.produceCodes(table, templateDir, relativePath.substring(1), outputPath.toString(), codes.getTemplate().getKeys());
            } catch (IOException e) {
                e.printStackTrace();
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
    }

    protected void configGradle() throws IOException {
        createFilesFromTemplate();
        String projectPath =
            codes.getProject().getBaseDir() + File.separator + codes
                .getProject().getName();
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            String createDirsResponse = null;
            try {
                createDirsResponse = GradleUtil.executeGradleCmd(projectPath, "createDirs");
                System.out.println(createDirsResponse);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }).start();
        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成build.gradle和README.MD
     */
    private void createFilesFromTemplate() throws IOException {
        Map<String, Object> map = new HashMap<>(3);
        map.put("groupId", StringUtils
            .wrapperSingleQuote(codes.getProject().getGroupId()));
        map.put("version", StringUtils
            .wrapperSingleQuote(codes.getProject().getVersion()));
        map.put("name", codes.getProject().getName());
        String buildGradle =
            codes.getProject().getBaseDir() + "/" + codes.getProject()
                .getName() + "/build.gradle";
        String readMe =
            codes.getProject().getBaseDir() + "/" + codes.getProject()
                .getName() + "/README.md";
        BeetlUtil.produce(codes.getProject().getTemplate(), "boot.template",
            buildGradle, map);
        BeetlUtil.produce(codes.getProject().getTemplate(), "readme.template",
            readMe, map);
    }

    protected void copyConfig() throws IOException {
        String configTemplate = codes.getProject().getConfig();
        String projectConfig =
            codes.getProject().getBaseDir() + "/" + codes.getProject()
                .getName() + "/docs";
        FileUtil.copyDir(configTemplate, projectConfig);
    }

}
