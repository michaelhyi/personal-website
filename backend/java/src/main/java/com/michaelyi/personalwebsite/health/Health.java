package com.michaelyi.personalwebsite.health;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Health {
    private Status servers;
    private String uptime;
    private Status mysql;
    private Status redis;

    public Health(Status servers, String uptime, Status mysql, Status redis) {
        this.servers = servers;
        this.uptime = uptime;
        this.mysql = mysql;
        this.redis = redis;
    }

    public Health(
            @JsonProperty("servers") String servers,
            @JsonProperty("uptime") String uptime,
            @JsonProperty("mysql") String mysql,
            @JsonProperty("redis") String redis
    ) {
        this(Status.valueOf(servers), uptime, Status.valueOf(mysql), Status.valueOf(redis));
    }

    @JsonProperty("servers")
    public String getServers() {
        return servers.name();
    }

    public String getUptime() {
        return uptime;
    }

    @JsonProperty("mysql")
    public String getMysql() {
        return mysql.name();
    }

    @JsonProperty("redis")
    public String getRedis() {
        return redis.name();
    }
}
