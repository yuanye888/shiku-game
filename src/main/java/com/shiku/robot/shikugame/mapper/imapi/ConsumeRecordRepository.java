package com.shiku.robot.shikugame.mapper.imapi;

import com.shiku.robot.shikugame.entity.imapi.ConsumeRecord;
import com.shiku.robot.shikugame.entity.imapi.RedPacket;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.entity.imapi.User;
import com.shiku.robot.shikugame.untils.PageFatherUtil;
import com.shiku.robot.shikugame.untils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:32 下午
 **/

@Repository
public class ConsumeRecordRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public ConsumeRecordRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ConsumeRecord insetConsumeRecord(ConsumeRecord consumeRecord) {
        return mongoTemplate.insert(consumeRecord);
    }

    public PageUtil<ConsumeRecord> selectAllConsumeRecord(String nickname, Integer pageNo, Integer pageSize) {
        PageUtil<RoomGameRule> pageUtil = new PageUtil(pageNo, pageSize);
        Query query = new Query();
        query.addCriteria(Criteria.where("nickname").is(nickname));
        query.with(Sort.by("createTime").descending());
        return new PageFatherUtil().getPage(mongoTemplate, pageUtil, query, RoomGameRule.class);
    }
}
