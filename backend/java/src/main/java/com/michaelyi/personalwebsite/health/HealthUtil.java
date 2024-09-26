package com.michaelyi.personalwebsite.health;

import com.michaelyi.personalwebsite.util.DateUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HealthUtil {
    private static final Date START = new Date();
    private final JdbcTemplate jdbcTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public HealthUtil(JdbcTemplate jdbcTemplate, RedisTemplate<String, String> redisTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    public String getUptime() {
        Date now = new Date();
        long uptime = now.getTime() - START.getTime();

        long hours = DateUtil.getTotalHours(uptime);
        long minutes = DateUtil.getRemainingMinutes(uptime);
        long seconds = DateUtil.getRemainingSeconds(uptime);
        long ms = DateUtil.getRemainingMillis(uptime);

        return String.format("%02dh %02dm %02ds %dms", hours, minutes, seconds, ms);
    }

    public Status getMysqlStatus() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return Status.UP;
        } catch (Exception e) {
            return Status.DOWN;
        }
    }

    public Status getRedisStatus() {
        try {
            String ping = redisTemplate.getConnectionFactory().getConnection().ping();

            if (ping.equals("PONG")) {
                return Status.UP;
            } else {
                return Status.DOWN;
            }
        } catch (Exception e) {
            return Status.DOWN;
        }
    }
}
