package com.michaelyi.personalwebsite.health;

import org.springframework.stereotype.Service;

@Service
public class HealthService {
    private final HealthUtil healthUtil;

    public HealthService(HealthUtil healthUtil) {
        this.healthUtil = healthUtil;
    }

    public Health getHealth() {
        Status servers = Status.UP;
        String uptime = healthUtil.getUptime();
        Status mysql = healthUtil.getMysqlStatus();
        Status redis = healthUtil.getRedisStatus();

        return new Health(servers, uptime, mysql, redis);
    }
}
