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

package org.hibernate.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.redis.util.JedisTool;
import org.hibernate.cfg.Settings;

import java.util.Properties;

/**
 * A non-singleton RedisRegionFactory implementation.
 *
 * @author sunghyouk.bae@gmail.com
 * @since 13. 4. 6. 오전 12:41
 */
@Slf4j
public class RedisRegionFactory extends AbstractRedisRegionFactory {

    public RedisRegionFactory(Properties props) {
        super(props);
    }

    @Override
    public void start(Settings settings, Properties properties) throws CacheException {
        log.info("starting RedisRegionFactory...");

        this.settings = settings;
        try {
            if (jedisClient == null) {
                this.jedisClient = JedisTool.createJedisClient(props);
            }
            log.info("start RedisRegionFactory");
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void stop() {
        if (jedisClient == null) return;
        log.debug("stopping RedisRegionFactory...");

        try {
            jedisClient = null;
            log.info("stopped RedisRegionFactory.");
        } catch (Exception e) {
            log.error("jedisClient region factory fail to stop.", e);
            throw new CacheException(e);
        }
    }

    private static final long serialVersionUID = 563532064197800959L;
}
