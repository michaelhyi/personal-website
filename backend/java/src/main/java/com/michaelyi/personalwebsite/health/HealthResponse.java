package com.michaelyi.personalwebsite.health;

public class HealthResponse {
    private HealthStatus status;
    private String uptime;
    private Details details;

    public HealthResponse(HealthStatus status, String uptime, Details details) {
        this.status = status;
        this.uptime = uptime;
        this.details = details;
    }

    public HealthResponse(String uptime) {
        this(HealthStatus.UP, uptime, new Details());
    }

    public String getStatus() {
        return status.name();
    }

    public String getUptime() {
        return uptime;
    }

    public Details getDetails() {
        return details;
    }

    protected static class Details {
        private HealthStatus mysql;
        private HealthStatus redis;

        public Details(HealthStatus mysql, HealthStatus redis) {
            this.mysql = mysql;
            this.redis = redis;
        }

        public Details() {
            this(HealthStatus.UP, HealthStatus.UP);
        }

        public String getMysql() {
            return mysql.name();
        }

        public String getRedis() {
            return redis.name();
        }
    }
}
