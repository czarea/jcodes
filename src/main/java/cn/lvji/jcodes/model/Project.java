package cn.lvji.jcodes.model;

/**
 * 项目配置
 *
 * @author zhouzx
 */
public class Project {
    private String baseDir;
    private String name;
    private String module;
    private String groupId;
    private String version;

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Project{" +
                "baseDir='" + baseDir + '\'' +
                ", name='" + name + '\'' +
                ", module='" + module + '\'' +
                ", groupId='" + groupId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
