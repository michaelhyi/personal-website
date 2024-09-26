package com.michaelyi.personalwebsite.health;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HealthServiceTest {
    private HealthService underTest;

    @Mock
    private HealthUtil healthUtil;

    @BeforeEach
    void setUp() {
        underTest = new HealthService(healthUtil);
    }

    @Test
    void canGetHealthWithDatabasesUp() throws Exception {
        // given
        when(healthUtil.getUptime()).thenReturn("12h 04m 02s 234ms");
        when(healthUtil.getMysqlStatus()).thenReturn(Status.UP);
        when(healthUtil.getRedisStatus()).thenReturn(Status.UP);

        // when
        Health actual = underTest.getHealth();

        String servers = actual.getServers();
        String uptime = actual.getUptime();
        String mysqlStatus = actual.getMysql();
        String redisStatus = actual.getRedis();

        assertEquals(Status.UP.toString(), servers);
        assertEquals("12h 04m 02s 234ms", uptime);
        assertEquals(Status.UP.toString(), mysqlStatus);
        assertEquals(Status.UP.toString(), redisStatus);
    }

    @Test
    void canGetHealthWithDatabasesDown() throws Exception {
        // given
        when(healthUtil.getUptime()).thenReturn("12h 04m 02s 234ms");
        when(healthUtil.getMysqlStatus()).thenReturn(Status.DOWN);
        when(healthUtil.getRedisStatus()).thenReturn(Status.DOWN);

        // when
        Health actual = underTest.getHealth();

        String servers = actual.getServers();
        String uptime = actual.getUptime();
        String mysqlStatus = actual.getMysql();
        String redisStatus = actual.getRedis();

        assertEquals(Status.UP.toString(), servers);
        assertEquals("12h 04m 02s 234ms", uptime);
        assertEquals(Status.DOWN.toString(), mysqlStatus);
        assertEquals(Status.DOWN.toString(), redisStatus);
    }

    @Test
    void canGetHealthWithMysqlUpAndRedisDown() throws Exception {
        // given
        when(healthUtil.getUptime()).thenReturn("12h 04m 02s 234ms");
        when(healthUtil.getMysqlStatus()).thenReturn(Status.UP);
        when(healthUtil.getRedisStatus()).thenReturn(Status.DOWN);

        // when
        Health actual = underTest.getHealth();

        String servers = actual.getServers();
        String uptime = actual.getUptime();
        String mysqlStatus = actual.getMysql();
        String redisStatus = actual.getRedis();

        assertEquals(Status.UP.toString(), servers);
        assertEquals("12h 04m 02s 234ms", uptime);
        assertEquals(Status.UP.toString(), mysqlStatus);
        assertEquals(Status.DOWN.toString(), redisStatus);
    }

    @Test
    void canGetHealthWithMysqlDownAndRedisUp() throws Exception {
        // given
        when(healthUtil.getUptime()).thenReturn("12h 04m 02s 234ms");
        when(healthUtil.getMysqlStatus()).thenReturn(Status.DOWN);
        when(healthUtil.getRedisStatus()).thenReturn(Status.UP);

        // when
        Health actual = underTest.getHealth();

        String servers = actual.getServers();
        String uptime = actual.getUptime();
        String mysqlStatus = actual.getMysql();
        String redisStatus = actual.getRedis();

        assertEquals(Status.UP.toString(), servers);
        assertEquals("12h 04m 02s 234ms", uptime);
        assertEquals(Status.DOWN.toString(), mysqlStatus);
        assertEquals(Status.UP.toString(), redisStatus);
    }
}
