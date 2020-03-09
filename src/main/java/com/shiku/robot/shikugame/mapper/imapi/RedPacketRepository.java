package com.shiku.robot.shikugame.mapper.imapi;

import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.entity.imapi.RedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:32 下午
 **/

@Repository
public class RedPacketRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RedPacketRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public RedPacket insetRedPacket(RedPacket redPacket) {
        return mongoTemplate.insert(redPacket);
    }

    public RedPacket selectRedPacketLockById(String redPacketId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(redPacketId));
        return mongoTemplate.findOne(query, RedPacket.class);
    }

    public UpdateResult updateRedPacketByIdAndUpdate(String redPacketId, Update update) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(redPacketId));
        return mongoTemplate.updateMulti(query, update, RedPacket.class);
    }

    public RedPacket selectRedPacketById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, RedPacket.class);
    }
}
