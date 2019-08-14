package cn.czarea.jcodes.template;

import cn.czarea.jcodes.db.Table;
import cn.czarea.jcodes.util.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.cache.ProgramCacheFactory;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * beetl工具类
 *
 * @author zhouzx
 */
public class BeetlUtil {

	private static Map<String, GroupTemplate> GROUPTEMPLATE_CACHE = new ConcurrentHashMap<>();

	/**
	 * 绑定常用对象
	 *
	 * @param t 模版
	 */
	public static void bindingCommon(Template t) {
		t.binding("separator", File.separator);
	}

	/**
	 * 根据模版生成文件
	 *
	 * @param table        数据表
	 * @param templatePath 模版文件名
	 * @param outPath      输出文件名
	 * @param map          模版变量
	 * @throws IOException
	 */
	public static void produceCodes(Table table, String rootPath,
			String templatePath, String outPath, Map<String, Object> map)
			throws IOException {
		FileResourceLoader resourceLoader = new FileResourceLoader(rootPath);
		GroupTemplate groupTemplate = createTemplate(rootPath);
		Template template = groupTemplate
				.getTemplate(templatePath, resourceLoader);
		template.binding(map);
		bindingCommon(template);
		template.binding("table", table);
		FileOutputStream outputStream = new FileOutputStream(outPath);
		template.renderTo(outputStream);
	}


	/**
	 * 根据模版生成文件
	 *
	 * @param templatePath 模版文件名
	 * @param outPath      输出文件名
	 * @param map          模版变量
	 * @throws IOException
	 */
	public static void produce(String rootPath, String templatePath,
			String outPath, Map<String, Object> map) throws IOException {
		FileResourceLoader resourceLoader = new FileResourceLoader(rootPath);
		GroupTemplate groupTemplate = createTemplate(rootPath);
		Template template = groupTemplate
				.getTemplate(templatePath, resourceLoader);
		bindingCommon(template);
		template.binding(map);
		FileOutputStream outputStream = new FileOutputStream(outPath);
		template.renderTo(outputStream);
	}


	public static String getFileName(String templatePath, String className)
			throws Exception {
		StringTemplateResourceLoader stringLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		ClassLoader loader = ProgramCacheFactory.class.getClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		GroupTemplate stringTemplate = new GroupTemplate(stringLoader, cfg);
		Template template = stringTemplate.getTemplate(templatePath);
		template.binding("className", className);
		String fileName = template.render();
		return fileName;
	}

	/**
	 * 创建模版
	 *
	 * @param rootPath 模版目录
	 * @return GroupTemplate
	 * @throws IOException
	 */
	private static GroupTemplate createTemplate(String rootPath)
			throws IOException {
		GroupTemplate groupTemplate = GROUPTEMPLATE_CACHE.get(rootPath);
		if (groupTemplate != null) {
			return groupTemplate;
		}
		FileResourceLoader resourceLoader = new FileResourceLoader(rootPath);
		Configuration cfg = Configuration.defaultConfiguration();
		ClassLoader loader = ProgramCacheFactory.class.getClassLoader();
		ClassLoader ploader = loader.getParent();
		ClassLoader tloader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(loader);
		groupTemplate = new GroupTemplate(resourceLoader, cfg);
		groupTemplate.registerFunctionPackage("utils", StringUtils.class);
		cfg.setStatementStart("#");
		cfg.setStatementEnd("#");
		cfg.setPlaceholderStart("@{");
		cfg.setPlaceholderEnd("}");
		GROUPTEMPLATE_CACHE.put(rootPath, groupTemplate);
		return groupTemplate;
	}
}
