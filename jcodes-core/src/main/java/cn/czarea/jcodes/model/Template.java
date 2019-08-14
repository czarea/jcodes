package cn.czarea.jcodes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模版配置
 *
 * @author zhouzx
 */
public class Template {
    private String dir;
    private String outPath;
    private String basePackage;
    private List<String> tables;
    private String prefixes;
    private Map<String,Object> keys = new HashMap<>();

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Map<String, Object> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, Object> keys) {
        this.keys = keys;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(String prefixes) {
        this.prefixes = prefixes;
    }

    @Override
    public String toString() {
        return "Template{" +
                "dir='" + dir + '\'' +
                ", outPath='" + outPath + '\'' +
                ", basePackage='" + basePackage + '\'' +
                ", tables=" + tables +
                ", prefixes='" + prefixes + '\'' +
                ", keys=" + keys +
                '}';
    }
}
