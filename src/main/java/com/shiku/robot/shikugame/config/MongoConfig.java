package com.shiku.robot.shikugame.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @Author lijun
 * @Description monogDB数据源配置
 * @Date 2019-12-21 4:30 下午
 **/

@Configuration
public class MongoConfig {

    @Primary
    @Bean(name = "mongoImRoomProperties")
    @ConfigurationProperties(prefix = "mongodb.imroom")
    public MongoProperties mongoImRoomProperties() {
        return new MongoProperties();
    }

    @Bean(name = "mongoImApiProperties")
    @ConfigurationProperties(prefix = "mongodb.imapi")
    public MongoProperties mongoImApiProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean("mongoImRoomTemplate")
    public MongoTemplate mongoImRoomTemplate(@Qualifier("mongoImRoomProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoDbFactory(mongoProperties));
    }

    @Bean("mongoImApiTemplate")
    public MongoTemplate mongoImApiTemplate(@Qualifier("mongoImApiProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoDbFactory(mongoProperties));
    }

    private MongoDbFactory mongoDbFactory(MongoProperties mongoProperties) {
        MongoClient mongoClient = new MongoClient(mongoProperties.getHost(),mongoProperties.getPort());
        return new SimpleMongoDbFactory(mongoClient, mongoProperties.getDatabase());
    }
}
