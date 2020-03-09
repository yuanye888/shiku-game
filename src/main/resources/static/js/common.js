/**
 * 消息类型转换
 */
function msgType(type) {
    if(1 == type){
        return "文本消息";
    }else if(2 == type ){
        return "图片消息";
    }else if(3 == type){
        return "语音消息";
    }else if(4 == type){
        return "位置消息";
    }else if(5 == type){
        return "动画消息";
    }else if(6 == type){
        return "视频消息";
    }else if(7 == type){
        return "音频消息";
    }else if(8 == type){
        return "名片消息";
    }else if(9 == type){
        return "文件消息";
    }else if(10 == type){
        return "提醒消息";
    }else if (28 == type) {
        return "红包消息";
    }else if(83 == type){
        return "领取红包消息";
    }else if(86 == type){
        return "红包退回消息";
    }else if(29 == type){
        return "转账消息";
    }else if(80 == type){
        return "单条图文消息";
    }else if(81 == type){
        return "多条图文消息";
    }else if(84 == type){
        return "戳一戳消息";
    }else if(85 == type){
        return "聊天记录消息";
    }else if(88 == type){
        return "转账被领取消息";
    }else if(89 == type){
        return "转账已退回消息";
    }else if(90 == type){
        return "付款码已付款通知消息";
    }else if(91 == type){
        return "付款码已到账通知消息";
    }else if(92 == type){
        return "收款码已付款通知消息";
    }else if(93 == type){
        return "收款码已到账通知消息";
    }else if(96 == type){
        return "双向撤回消息";
    }else if(201 == type){
        return "正在输入消息";
    }else if(202 == type){
        return "撤回消息";
    }else if(100 == type){
        return "发起语音通话消息";
    }else if(102 == type){
        return "接听语音通话消息";
    }else if(103 == type){
        return "拒绝语音通话消息";
    }else if(104 == type){
        return "结束语音通话消息";
    }else if(110 == type){
        return "发起视频通话消息";
    }else if(112 == type){
        return "接听视频通话消息";
    }else if(113 == type){
        return "拒绝视频通话消息";
    }else if(114 == type){
        return "结束视频通话消息";
    }else if(115 == type){
        return "会议邀请消息";
    }else if(901 == type){
        return "修改昵称消息";
    }else if(902 == type){
        return "修改房间名消息";
    }else if(903 == type){
        return "解散房间消息";
    }else if(904 == type){
        return "退出房间消息";
    }else if(905 == type){
        return "新公告消息";
    }else if(906 == type){
        return "禁言、取消禁言消息";
    }else if(907 == type){
        return "增加成员消息";
    }else if(913 == type){
        return "设置、取消管理员消息";
    }else if(915 == type){
        return "设置群已读消息";
    }else if(916 == type){
        return "群验证消息";
    }else if(917 == type){
        return "群组是否公开消息";
    }else if(918 == type){
        return "是否展示群成员列表消息";
    }else if(919 == type){
        return "允许发送名片消息";
    }else if(920 == type){
        return "全员禁言消息";
    }else if(921 == type){
        return "允许普通群成员邀请好友加群";
    }else if(922 == type){
        return "允许普通成员上传群共享";
    }else if(923 == type){
        return "允许普通成员发起会议";
    }else if(924 == type){
        return "允许普通成员发送讲课";
    }else if(925 == type){
        return "转让群组";
    }else if(930 == type){
        return "设置、取消隐身人，监控人";
    }else if(931 == type){
        return "群组被后台锁定/解锁";
    }
    else{
        return "其他消息类型";
    }
}

/**
 * 成员角色
 **/
function roleType(type) {
    if(type==1){
        return "群主";
    }else if(type==2){
        return "管理员";
    }else if(type==3){
        return "成员";
    }else if(type == 4){
        return "隐身人";
    }else if(type == 5){
        return "监控人";
    }
    return type;
}