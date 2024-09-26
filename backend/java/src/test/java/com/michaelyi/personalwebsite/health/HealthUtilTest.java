package com.michaelyi.personalwebsite.health;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HealthUtilTest {
    private HealthUtil underTest;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void setUp() {
        underTest = new HealthUtil(jdbcTemplate, redisTemplate);
    }

    @Test
    void canGetUptime() {
        // given
        Pattern uptimePattern = Pattern.compile("^\\d{2}h \\d{2}m \\d{2}s \\d+ms$");

        // when
        String actual = underTest.getUptime();

        assertTrue(uptimePattern.matcher(actual).matches());
    }

    @Test
    void canGetMysqlUpStatus() {
        // when
        Status actual = underTest.getMysqlStatus();

        // then
        verify(jdbcTemplate).queryForObject("SELECT 1", Integer.class);
        assertEquals(Status.UP, actual);
    }

    @Test
    void canGetMysqlDownStatus() {
        // given
        when(jdbcTemplate.queryForObject("SELECT 1", Integer.class))
                .thenThrow(RuntimeException.class);

        // when
        Status actual = underTest.getMysqlStatus();

        // then
        verify(jdbcTemplate).queryForObject("SELECT 1", Integer.class);
        assertEquals(Status.DOWN, actual);
    }

    @Test
    void canGetRedisUpStatus() {
        // given
        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);
        RedisConnection conn = mock(RedisConnection.class);

        when(redisTemplate.getConnectionFactory()).thenReturn(connectionFactory);
        when(redisTemplate.getConnectionFactory().getConnection()).thenReturn(conn);
        when(redisTemplate.getConnectionFactory().getConnection().ping())
                .thenReturn("PONG");

        // when
        Status actual = underTest.getRedisStatus();

        // then
        verify(redisTemplate.getConnectionFactory().getConnection()).ping();
        assertEquals(Status.UP, actual);
    }

    @Test
    void canGetRedisDownStatus() {
        // given
        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);
        RedisConnection conn = mock(RedisConnection.class);

        when(redisTemplate.getConnectionFactory()).thenReturn(connectionFactory);
        when(redisTemplate.getConnectionFactory().getConnection()).thenReturn(conn);
        when(redisTemplate.getConnectionFactory().getConnection().ping())
                .thenThrow(RuntimeException.class);

        // when
        Status actual = underTest.getRedisStatus();

        // then
        verify(redisTemplate.getConnectionFactory().getConnection()).ping();
        assertEquals(Status.DOWN, actual);
    }
}
