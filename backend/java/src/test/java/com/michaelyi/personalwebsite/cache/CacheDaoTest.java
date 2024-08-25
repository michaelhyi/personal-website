package com.michaelyi.personalwebsite.cache;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
public class CacheDaoTest {
    private CacheDao underTest;

    @Mock
    private RedisTemplate<String, String> template;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final long TTL = 1000 * 60 * 15;

    @BeforeEach
    void setup() {
        underTest = new CacheDao(template);
    }

    @Test
    void canSet() {
        // given
        when(template.opsForValue()).thenReturn(valueOperations);

        // when
        underTest.set(KEY, VALUE, TTL);

        // then
        verify(template.opsForValue())
                .set(KEY, VALUE, TTL, TimeUnit.MILLISECONDS);
    }

    @Test
    void canGet() {
        // given
        when(template.opsForValue()).thenReturn(valueOperations);
        when(template.opsForValue().get(KEY)).thenReturn(VALUE);

        // when
        String actual = underTest.get(KEY);

        // then
        assertEquals(VALUE, actual);
        verify(template.opsForValue()).get(KEY);
    }

    @Test
    void canDelete() {
        // when
        underTest.delete(KEY);

        // then
        verify(template).delete(KEY);
    }
}
