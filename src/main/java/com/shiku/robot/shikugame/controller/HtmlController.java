package com.shiku.robot.shikugame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lijun
 * @Description
 * @Date 2019-12-22 12:21 上午
 **/
@Controller
public class HtmlController {
    // 跳转登陆页面
    @RequestMapping("login.html")
    public String loginHtml() {
        return "login";
    }

    // 跳转首页面
    @RequestMapping("index.html")
    public String indexHtml() {
        return "index";
    }

    // 跳转欢迎页面
    @RequestMapping("welcome.html")
    public String welcomeHtml() {
        return "welcome";
    }

    // 跳转群组管理页面
    @RequestMapping("room-list.html")
    public String roomListHtml() {
        return "room-list";
    }

    // 跳转群组管理页面
    @RequestMapping("room-add.html")
    public String roomAddHtml() {
        return "room-add";
    }

    // 跳转聊天记录页面
    @RequestMapping("groupchat-logs-list.html")
    public String groupchatLogsListHtml(HttpServletRequest request, @RequestParam(value = "room_jid_id") String room_jid_id) {
        request.setAttribute("room_jid_id", room_jid_id);
        return "groupchat-logs-list";
    }

    // 跳转成员管理页面
    @RequestMapping("room-usermanager-list.html")
    public String roomUsermanagerListHtml(HttpServletRequest request, @RequestParam(value = "room_id") String room_id) {
        request.setAttribute("room_id", room_id);
        return "room-usermanager-list";
    }

    // 跳转游戏规则页面
    @RequestMapping("room-game-rule.html")
    public String roomGameRuleHtml(HttpServletRequest request, @RequestParam(value = "room_id") String room_id, @RequestParam(value = "room_jid_id") String room_jid_id) {
        request.setAttribute("room_id", room_id);
        request.setAttribute("room_jid_id", room_jid_id);
        return "room-game-rule";
    }

    // 跳转游戏规则添加页面
    @RequestMapping("room-game-rule-add.html")
    public String roomGameRuleAddHtml(HttpServletRequest request,
                                      @RequestParam(value = "roomId") String roomId,
                                      @RequestParam(value = "roomJidId") String roomJidId,
                                      @RequestParam(value = "type") String type) {
        request.setAttribute("roomId", roomId);
        request.setAttribute("roomJidId", roomJidId);
        request.setAttribute("type", type);
        return "room-game-rule-add";
    }

    // 跳转游戏规则页面
    @RequestMapping("room-game-rule-edit.html")
    public String roomGameRuleEditHtml(HttpServletRequest request, @RequestParam(value = "id") String id) {
        request.setAttribute("id", id);
        return "room-game-rule-edit";
    }

    // 跳转检测雷页面
    @RequestMapping("checkBomb.html")
    public String checkBombHtml() {
        return "checkBomb";
    }

    // 跳转群组管理页面
    @RequestMapping("userList.html")
    public String userListHtml() {
        return "userList";
    }
}
