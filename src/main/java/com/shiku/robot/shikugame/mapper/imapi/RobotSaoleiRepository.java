package com.shiku.robot.shikugame.mapper.imapi;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.shiku.robot.shikugame.entity.imapi.RoomGameRule;
import com.shiku.robot.shikugame.entity.imapi.User;
import com.shiku.robot.shikugame.entity.imroom.ShikuRobotSaoLei;
import com.shiku.robot.shikugame.untils.PageFatherUtil;
import com.shiku.robot.shikugame.untils.PageUtil;
import com.shiku.robot.shikugame.untils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:32 下午
 **/

@Repository
public class RobotSaoleiRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public RobotSaoleiRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Autowired
    UserRepository userRepository;

    public PageUtil<ShikuRobotSaoLei> selectAllPage(Integer pageNo, Integer pageSize) {
        PageUtil<ShikuRobotSaoLei> pageUtil = new PageUtil(pageNo, pageSize);
        Query query = new Query();
//        query.addCriteria(Criteria.where("nickname").is(nickname));
        query.with(Sort.by("createTime").descending());

        PageUtil<ShikuRobotSaoLei> page = new PageFatherUtil().getPage(mongoTemplate, pageUtil, query, ShikuRobotSaoLei.class);
        List<ShikuRobotSaoLei> list = page.getData();
        for (ShikuRobotSaoLei sr:list
        ) {
            User user = userRepository.selectUserById(sr.getUserId());
            sr.setUserName(user.getNickname());
        }
        return page;
    }

    public ShikuRobotSaoLei selectById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, ShikuRobotSaoLei.class);
    }
    public ShikuRobotSaoLei addObj(ShikuRobotSaoLei obj) {
        return mongoTemplate.insert(obj);
    }
    public DeleteResult delById(String id) {
        ShikuRobotSaoLei obj = new ShikuRobotSaoLei();
        obj.setId(id);
        return mongoTemplate.remove(obj);
    }

    public UpdateResult updateObj(ShikuRobotSaoLei obj) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(obj.getId()));
        Update update = new Update();
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("isStartUp", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("rapPacketSpeed", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("sendPacketMin", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("sendPacketMax", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("sendPacketNum", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("sendPacketSize", obj.getIsStartUp());
        }
        if (StringUtil.isEmpty(obj.getIsStartUp(),false)) {
            update.set("sendPacketBlessings", obj.getIsStartUp());
        }
        return mongoTemplate.updateMulti(query, update, User.class);

    }

}
