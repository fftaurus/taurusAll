package com.taurus.zjbm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键值
     * @param time 时间（秒）
     */
    public boolean expire(final String key, final long time) {

        try {
            if (time > 0) {
                this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间点
     *
     * @param key        键值
     * @param expireTime 时间（秒）
     */
    public boolean expireAt(final String key, final Date expireTime) {

        try {
            this.redisTemplate.expireAt(key, expireTime);
            return true;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     */
    @SuppressWarnings("boxing")
    public boolean hasKey(final String key) {

        try {
            return this.redisTemplate.hasKey(key);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 一个或多个
     */
    public void del(final String... key) {

        if (null != key && key.length > 0) {
            // final List<String> list = new ArrayList<String>();
            this.redisTemplate.delete(Arrays.asList(key));
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key
     */
    public Object get(final String key) {

        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key
     * @param value
     */
    public boolean set(final String key, final Object value) {

        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key
     * @param value
     * @param time  时间（秒）
     */
    public boolean set(final String key, final Object value, final long time) {

        try {
            if (time > 0) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }
            return true;
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 自增数字
     *
     * @param key
     * @return
     */
    public Long getIncrementNum(final String key) {
        // 不存在准备创建 键值对
        final RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, this.redisTemplate.getConnectionFactory());
        final Long counter = entityIdCounter.incrementAndGet();
        // 初始设置过期时间
        if ((null == counter || counter.longValue() == 1)) {
            log.info("设置过期时间为1天!");
            // 单位天
            entityIdCounter.expire(1, TimeUnit.DAYS);
        }
        return counter;
    }

    /**
     * 格式化数字，数字前自动补0
     *
     * @param seq
     * @param length
     * @return
     */
    public String getSequence(final Long seq, final Integer length) {

        final String str = String.valueOf(Integer.toHexString(seq.intValue()));
        final int len = str.length();
        // 取决于业务规模,应该不会到达4
        if (len >= length) {
            return str;
        }
        final int rest = length - len;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rest; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb.toString();
    }

    public long getAssetNameSeq(final String key) {

        final RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        long sequence = 0L;
        try {
            sequence = redisAtomicLong.incrementAndGet();
        } catch (final Exception ex) {
            log.error("Failed to get sequence.", ex);
        }
        return sequence;
    }

}