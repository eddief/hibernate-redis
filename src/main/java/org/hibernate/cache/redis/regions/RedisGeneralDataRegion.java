/*
 * Copyright 2011-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.hibernate.cache.redis.regions;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.redis.jedis.JedisClient;
import org.hibernate.cache.redis.strategy.RedisAccessStrategyFactory;
import org.hibernate.cache.spi.GeneralDataRegion;

import java.util.Properties;

/**
 * RedisGeneralDataRegion
 *
 * @author sunghyouk.bae@gmail.com
 * @since 13. 4. 5. 오후 9:00
 */
@Slf4j
public abstract class RedisGeneralDataRegion extends RedisDataRegion implements GeneralDataRegion {

    protected RedisGeneralDataRegion(RedisAccessStrategyFactory accessStrategyFactory,
                                     JedisClient jedisClient,
                                     String regionName,
                                     Properties props) {
        super(accessStrategyFactory, jedisClient, regionName, props);
    }

    @Override
    public Object get(Object key) throws CacheException {
        if (key == null) return null;
        try {
            return jedisClient.get(getName(), key);
        } catch (Exception e) {
            return new CacheException(e);
        }
    }

    @Override
    public void put(Object key, Object value) throws CacheException {
        try {
            jedisClient.set(getName(), key, value, getExpireInSeconds());
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void evict(Object key) throws CacheException {
        try {
            jedisClient.del(getName(), key);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void evictAll() throws CacheException {
        try {
            jedisClient.deleteRegion(getName());
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }
}
