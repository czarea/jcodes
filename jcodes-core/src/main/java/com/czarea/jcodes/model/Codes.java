package com.czarea.jcodes.model;

/**
 * Codes配置
 *
 * @author zhouzx
 */
public class Codes {
	private String gradleHome;
	private String author;
	private Project project;
	private Db db;
	private Template template;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getGradleHome() {
		return gradleHome;
	}

	public void setGradleHome(String gradleHome) {
		this.gradleHome = gradleHome;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Db getDb() {
		return db;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	@Override
	public String toString() {
		return "Codes{" + "gradleHome='" + gradleHome + '\'' + ", author='"
				+ author + '\'' + ", project=" + project + ", db=" + db
				+ ", template=" + template + '}';
	}
}
