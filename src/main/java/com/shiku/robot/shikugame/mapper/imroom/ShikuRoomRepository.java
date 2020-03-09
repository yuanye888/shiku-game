package com.shiku.robot.shikugame.mapper.imroom;

import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.entity.imroom.ShikuRoom;
import com.shiku.robot.shikugame.untils.PageFatherUtil;
import com.shiku.robot.shikugame.untils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.regex.Pattern;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 4:54 下午
 **/

@Repository
public class ShikuRoomRepository {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ShikuRoomRepository(@Qualifier("mongoImRoomTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PageUtil<ShikuRoom> selectAllShikuRoom(int pageNo, int pageSize, String name, Integer userSize) {
        PageUtil<ShikuRoom> pageUtil = new PageUtil(pageNo, pageSize);
        Query query = new Query();
        if (name != null && name != "") {
            //模糊匹配
            Pattern pattern = Pattern.compile("^.*" + name + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("name").regex(pattern));
        } else if (userSize != null) {
            query.addCriteria(Criteria.where("userSize").gt(userSize));
        }
        return new PageFatherUtil().getPage(mongoTemplate, pageUtil, query, ShikuRoom.class);
    }

    public UpdateResult editRoomRedPacketLock(String roomId, String redPacketLock) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        Update update = new Update();
        update.set("redPacketLock", redPacketLock);
        return mongoTemplate.updateMulti(query, update, ShikuRoom.class);
    }

    public ShikuRoom selectShikuRoomByRoomId(String roomId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        return mongoTemplate.findOne(query, ShikuRoom.class);
    }

    public ShikuRoom selectShikuRoomByRoomJidId(String roomJidId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("jid").is(roomJidId));
        return mongoTemplate.findOne(query, ShikuRoom.class);
    }
    public UpdateResult editRoomRedPacketMinMax(String roomId, String minMoney, String maxMoney) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(roomId));
        Update update = new Update();
        update.set("minMoney", minMoney);
        update.set("maxMoney", maxMoney);
        return mongoTemplate.updateMulti(query, update, ShikuRoom.class);
    }

}
