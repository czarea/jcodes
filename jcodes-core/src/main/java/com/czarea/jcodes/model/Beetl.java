package com.czarea.jcodes.model;

/**
 * beetl配置
 *
 * @author zhouzx
 */
public class Beetl {
    private String statementStart;
    private String statementEnd;
    private String placeholderStart;
    private String placeholderEnd;

    public String getStatementStart() {
        return statementStart;
    }

    public void setStatementStart(String statementStart) {
        this.statementStart = statementStart;
    }

    public String getStatementEnd() {
        return statementEnd;
    }

    public void setStatementEnd(String statementEnd) {
        this.statementEnd = statementEnd;
    }

    public String getPlaceholderStart() {
        return placeholderStart;
    }

    public void setPlaceholderStart(String placeholderStart) {
        this.placeholderStart = placeholderStart;
    }

    public String getPlaceholderEnd() {
        return placeholderEnd;
    }

    public void setPlaceholderEnd(String placeholderEnd) {
        this.placeholderEnd = placeholderEnd;
    }

    @Override
    public String toString() {
        return "Beetl{" +
                "statementStart='" + statementStart + '\'' +
                ", statementEnd='" + statementEnd + '\'' +
                ", placeholderStart='" + placeholderStart + '\'' +
                ", placeholderEnd='" + placeholderEnd + '\'' +
                '}';
    }
}
