package com.shiku.robot.shikugame.mapper.imapi;

import com.shiku.robot.shikugame.entity.imapi.RedReceive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:32 下午
 **/

@Repository
public class RedReceiveRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RedReceiveRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public RedReceive insetRedReceive(RedReceive redReceive) {
        return mongoTemplate.insert(redReceive);
    }

    public RedReceive selectRedReceiveById(String redReceiveId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(redReceiveId));
        return mongoTemplate.findOne(query, RedReceive.class);
    }

    public List<RedReceive> selectAllRedReceiveByRedPacketId(String redPacketId){
        Query query = new Query();
        query.addCriteria(Criteria.where("redId").is(redPacketId));
        query.with(Sort.by("time").descending());
        return mongoTemplate.find(query,RedReceive.class);
    }
}
