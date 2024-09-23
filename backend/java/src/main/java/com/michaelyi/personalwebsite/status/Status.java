package com.michaelyi.personalwebsite.status;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    private ServerStatus servers;
    private String uptime;
    private Details details;

    public Status(ServerStatus servers, String uptime, Details details) {
        this.servers = servers;
        this.uptime = uptime;
        this.details = details;
    }

    public Status(
            @JsonProperty("servers") String servers,
            @JsonProperty("uptime") String uptime,
            @JsonProperty("details") Details details
    ) {
        this(ServerStatus.valueOf(servers), uptime, details);
    }

    public Status(String uptime) {
        this(ServerStatus.UP, uptime, new Details());
    }

    public ServerStatus getServers() {
        return servers;
    }

    @JsonProperty("servers")
    public String getServersAsString() {
        return servers.name();
    }

    public String getUptime() {
        return uptime;
    }

    public Details getDetails() {
        return details;
    }

    public static class Details {
        private ServerStatus mysql;
        private ServerStatus redis;

        public Details(ServerStatus mysql, ServerStatus redis) {
            this.mysql = mysql;
            this.redis = redis;
        }

        public Details() {
            this(ServerStatus.UP, ServerStatus.UP);
        }

        public ServerStatus getMysql() {
            return mysql;
        }

        @JsonProperty("mysql")
        public String getMysqlAsString() {
            return mysql.name();
        }

        public ServerStatus getRedis() {
            return redis;
        }

        @JsonProperty("redis")
        public String getRedisAsString() {
            return redis.name();
        }
    }
}
