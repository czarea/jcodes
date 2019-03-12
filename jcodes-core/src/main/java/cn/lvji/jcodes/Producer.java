package cn.lvji.jcodes;

import cn.lvji.jcodes.config.Config;
import cn.lvji.jcodes.db.CodesDataSource;
import cn.lvji.jcodes.db.Table;
import cn.lvji.jcodes.db.TableExtractor;
import cn.lvji.jcodes.model.Codes;
import cn.lvji.jcodes.template.BeetlUtil;
import cn.lvji.jcodes.util.FileUtil;
import cn.lvji.jcodes.util.GradleUtil;
import cn.lvji.jcodes.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

	/**
	 * 生产模版项目
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void produceProject(String config) throws IOException, InterruptedException {
		codes = Config.yamlInit(config);
		String projectPath = codes.getProject().getBaseDir() + File.separator + codes.getProject().getName();
		String createProjectResponse = GradleUtil.executeGradleCmd(projectPath, "init");
		System.out.println(createProjectResponse);
		configGradle();
		copyConfig();
	}

	/**
	 * 生产模版项目
	 *
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void produceProject() throws IOException, InterruptedException {
		init();
		configGradle();
		copyConfig();
	}

	/**
	 * 生产模版代码
	 *
	 * @throws IOException
	 */
	public void produceCodes() throws IOException {
		codes = Config.yamlInit();
		baseProduceCodes(codes);
	}

	/**
	 * 生产模版代码
	 *
	 * @throws IOException
	 */
	public void produceCodes(String config) throws IOException {
		codes = Config.yamlInit(config);
		baseProduceCodes(codes);
	}

	private void baseProduceCodes(Codes codes) throws IOException {
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

		if (tables == null || tables.isEmpty()) {
			tables = TableExtractor.getAllTables(codes.getDb().getDatabase());
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

	/**
	 * 删除输入目录
	 *
	 * @throws IOException
	 */
	public void deleteOutput() throws IOException {
		codes = Config.yamlInit();
		Path path = Paths.get(codes.getTemplate().getOutPath());
		if (Files.exists(path)) {
			Files.delete(path);
		}
	}

	protected void produceFile(String templateDir, String outPath, Table table) throws Exception {
		List<File> files = FileUtil.getFileList(templateDir);
		for (int i = 0; i < files.size(); i++) {
			File item = files.get(i);
			System.out.println("开始生成数据库表：" + table.getName() + " 的 " + item.getAbsolutePath() + "模版代码");
			long start = System.currentTimeMillis();
			String outPutFileName = BeetlUtil.getFileName(item.getName(), table.getClassName());
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
			codes.getTemplate().getKeys().put("className", table.getClassName());
			try {
				BeetlUtil.produceCodes(table, templateDir, relativePath.substring(1), outputPath.toString(),
						codes.getTemplate().getKeys());
			} catch (IOException e) {
			}
			long end = System.currentTimeMillis();
			System.out.println(
					"生成数据库表：" + table.getName() + " 的 " + item.getAbsolutePath() + "模版代码成功，耗时：" + (end - start)
							+ " ms");
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
		String projectPath = codes.getProject().getBaseDir() + File.separator + codes.getProject().getName();
		final CountDownLatch latch = new CountDownLatch(1);
		new Thread(() -> {
			String createDirsResponse = null;
			try {
				createDirsResponse = GradleUtil.executeGradleCmd(projectPath, "createDirs");
				System.out.println(createDirsResponse);
				latch.countDown();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		try {
			latch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *	生成build.gradle和README.MD
	 * @throws IOException
	 */
	private void createFilesFromTemplate() throws IOException {
		Map<String, Object> map = new HashMap<>(3);
		map.put("groupId", StringUtils.wrapperSingleQuote(codes.getProject().getGroupId()));
		map.put("version", StringUtils.wrapperSingleQuote(codes.getProject().getVersion()));
		map.put("name", codes.getProject().getName());
		String buildGradle = codes.getProject().getBaseDir() + "/" + codes.getProject().getName() + "/build.gradle";
		String readMe = codes.getProject().getBaseDir() + "/" + codes.getProject().getName() + "/README.md";
		BeetlUtil.produce(codes.getProject().getTemplate(), "boot.template", buildGradle, map);
		BeetlUtil.produce(codes.getProject().getTemplate(), "readme.template", readMe, map);
	}

	protected void copyConfig() throws IOException {
		String configTemplate = codes.getProject().getConfig();
		String projectConfig = codes.getProject().getBaseDir() + "/" + codes.getProject().getName() + "/docs";
		FileUtil.copyDir(configTemplate, projectConfig);
	}


}
