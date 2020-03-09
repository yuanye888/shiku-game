package com.shiku.robot.shikugame.mapper.imroom;

import com.shiku.robot.shikugame.entity.imroom.ShikuRoomMember;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:54 下午
 **/

@Repository
public class ShikuRoomMemberRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ShikuRoomMemberRepository(@Qualifier("mongoImRoomTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ShikuRoomMember selectShikuRoomControlMember(ObjectId roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomId").is(roomId));
        query.addCriteria(Criteria.where("role").is(5));
        return mongoTemplate.findOne(query, ShikuRoomMember.class);
    }
}
