package com.project.springBlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private int defaultpPageNumber;
    private int defaultPageSize;
    private int defaultMaxPageSize;
    private long adminAccountId;

    public int getDefaultpPageNumber() {
        return defaultpPageNumber;
    }

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public int getDefaultMaxPageSize() {
        return defaultMaxPageSize;
    }

    public long getAdminAccountId() {
        return adminAccountId;
    }

    public void setDefaultpPageNumber(int defaultpPageNumber) {
        this.defaultpPageNumber = defaultpPageNumber;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public void setDefaultMaxPageSize(int defaultMaxPageSize) {
        this.defaultMaxPageSize = defaultMaxPageSize;
    }

    public void setAdminAccountId(long adminAccountId) {
        this.adminAccountId = adminAccountId;
    }
}
