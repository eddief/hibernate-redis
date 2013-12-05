Forked from user/debop's [hibernate-redis][hibernate-redis], same but without multiple pom.xml's, no tests, and without spring-core or tomcat dependencies.

[hibernate-redis]: https://github.com/debop/hibernate-redis

Import these:
`import org.hibernate.annotations.{CacheConcurrencyStrategy, Cache}`

Add this to the top of the class:
`@Cache(region = "redis:common", usage = CacheConcurrencyStrategy.READ_WRITE)`

Add this to persistence.xml:
>     <property name="hibernate.cache.use_second_level_cache" value="true"/>
    <property name="hibernate.cache.use_query_cache" value="true"/>
    <property name="hibernate.cache.region.factory_class" value="org.hibernate.cache.redis.SingletonRedisRegionFactory"/>
    <property name="hibernate.cache.region_prefix" value="hibernate:"/>
    <property name="redis.host" value="localhost"/>
    <property name="redis.port" value="6379"/>
    <property name="redis.database" value="1"/>
    <property name="redis.expiryInSeconds" value="30"/>
    <property name="redis.expiryInSeconds.hibernate.common" value="30"/>
    <property name="redis.expiryInSeconds.hibernate.account" value="30"/>
