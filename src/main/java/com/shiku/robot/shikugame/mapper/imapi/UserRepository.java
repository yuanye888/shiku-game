package com.shiku.robot.shikugame.mapper.imapi;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-21 9:32 下午
 **/

@Repository
public class UserRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public UserRepository(@Qualifier("mongoImApiTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public User selectUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        return mongoTemplate.findOne(query, User.class);
    }

    public DeleteResult delUserById(String userId) {
        User user = new User();
        user.setId(userId);
        return mongoTemplate.remove(user);
    }
    public User addUser(User user) {
        return mongoTemplate.insert(user);
    }
    public UpdateResult updateUseralance(Integer userId, Double balance, Double money, Double totalConsume, Integer type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update();
        if (totalConsume!=null) {
            update.set("totalConsume", totalConsume + money);
        } else {
            update.set("totalConsume", money);
        }
        if (type == 0) {
            update.set("balance", balance - money);
            return mongoTemplate.updateMulti(query, update, User.class);
        } else if (type == 1) {
            update.set("balance", balance + money);
            return mongoTemplate.updateMulti(query, update, User.class);
        }
        return null;
    }
    public UpdateResult updateRobot(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(user.getId()));
        Update update = new Update();

        update.set("telephone", user.getTelephone());
        update.set("nickname", user.getNickname());
        update.set("sex", user.getSex());

        return mongoTemplate.updateMulti(query, update, User.class);

    }
    public PageUtil<User> selectAllUser(String nickname, Integer pageNo, Integer pageSize) {
        PageUtil<RoomGameRule> pageUtil = new PageUtil(pageNo, pageSize);
        Query query = new Query();
        query.addCriteria(Criteria.where("nickname").is(nickname));
        query.with(Sort.by("createTime").descending());
        return new PageFatherUtil().getPage(mongoTemplate, pageUtil, query, RoomGameRule.class);
    }
}
