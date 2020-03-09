package com.shiku.robot.shikugame.mapper.imapi;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.untils.PageFatherUtil;
import com.shiku.robot.shikugame.untils.PageUtil;
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
public class RoomGameRuleRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RoomGameRuleRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PageUtil<RoomGameRule> selectRoomGameRule(String roomJidId, Integer pageNo, Integer pageSize) {
        PageUtil<RoomGameRule> pageUtil = new PageUtil(pageNo, pageSize);
        Query query = new Query();
        query.addCriteria(Criteria.where("roomJidId").is(roomJidId));
        return new PageFatherUtil().getPage(mongoTemplate, pageUtil, query, RoomGameRule.class);
    }

    public RoomGameRule addRoomGameRule(RoomGameRule roomGameRule) {
        return mongoTemplate.insert(roomGameRule);
    }

    public DeleteResult roomGameRuleRepository(String gameRuleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(gameRuleId));
        return mongoTemplate.remove(query, RoomGameRule.class);
    }

    public RoomGameRule selectRoomGameRuleById(String gameRuleId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(gameRuleId));
        return mongoTemplate.findOne(query, RoomGameRule.class);
    }

    public UpdateResult editRoomGameRule(RoomGameRule roomGameRule) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomGameRule.getId()));
        Update update = new Update();
        update.set("receiveCount", roomGameRule.getReceiveCount());
        update.set("bombCount", roomGameRule.getBombCount());
        update.set("chance", roomGameRule.getChance());
        return mongoTemplate.updateMulti(query, update, RoomGameRule.class);
    }

    public RoomGameRule selectRoomGameRuleByRBCount(String roomJidId, String receiveCount, String bombCount, String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("roomJidId").is(roomJidId));
        query.addCriteria(Criteria.where("receiveCount").is(receiveCount));
        query.addCriteria(Criteria.where("bombCount").is(bombCount));
        query.addCriteria(Criteria.where("type").is(type));
        return mongoTemplate.findOne(query, RoomGameRule.class);
    }
}
