/**
 * 群组管理
 */
/*加载群组列表*/
let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
time = parseInt(time / 1000);
var sign = hex_md5($.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token"));
layui.use('table', function () {

    var table = layui.table;
    table.render({
        elem: '#roomTable'
        , url: '/user/selectAllRobot'
        , where: {
            "token": $.cookie("access_Token"),
            "userId": $.cookie("account"),
            "sysId": "",
            "sign": sign,
            "time": time,
            "reqData": {}
        }
        , contentType: "application/json;charset=utf-8"
        , dataType: "JSON"
        , method: "POST"
        , parseData: function (res) {
            if (res.repCode == "1030102") {
                layer.msg(res.repMsg, function () {
                    window.parent.frames.location.href = 'login.html'
                });
            } else if (res.repCode == "0000") {
                return {
                    "code": res.repCode,
                    "msg": res.repMsg,
                    "count": res.repData.count,
                    "data": res.repData.data
                }
            } else {
                layer.msg(res.repMsg, {icon: 5});
            }
        },
        response: {
            statusCode: "0000" //重新规定成功的状态码为 200，table 组件默认为 0
        }
        , cols: [
            [

                {field: 'userId', title: '用户Id',sort:'true', width:100}
                ,{field: 'nickname', title: '昵称',sort:'true', width:100}
                ,{field: 'telephone', title: '手机号码',sort:'true', width:135,templet(d){
                    // if(0 == Config.getConfig().regeditPhoneOrName){
                        return d.telephone;
                    // }else{
                    //     return d.phone;
                    // }
                }}
                ,{field: 'account', title: '通讯号',sort:'true', width:145}
                // ,{field: 'model', title: '登录设备',sort:'true', width:110,templet(d){
                //     if(null != d.loginLog){
                //         var model = d.loginLog.model;
                //         if(null == model || undefined == model || "" == model){
                //             return "其他设备";
                //         }else{
                //             return model;
                //         }
                //     }
                //     return "其他设备"
                //
                // }}

                ,{field: 'balance', title: '账户余额',sort:'true', width:145,templet:function (d) {
                    var money = d.balance.toFixed(2);
                    return money;
                }}
                ,{field: 'createTime', title: '注册时间',sort:'true', width:170,templet: function(d){

                    return timeFormat(d.createTime, "yyyy-mm-dd hh:mm:ss");
                }}
                ,{field: 'onlinestate', title: '在线状态',sort:'true', width:105,templet: function(d){
                    return (d.onlinestate==0?"离线":"在线");
                }}
                ,{field: 'sex', title: '性别',sort:'true', width:100,templet: function(d){
                    return d.sex==1?"男":"女";
                }}
                // ,{field: 'loginLog', title: '最后上线时间',sort:'true', width:170,templet: function(d){
                //     // console.log("log    :"+JSON.stringify(d.loginLog));
                //     if(d.loginLog==undefined){
                //         return "";
                //     }else{
                //         if(d.loginLog.loginTime==0){
                //             return "";
                //         }else{
                //             return UI.getLocalTime(d.loginLog.loginTime);
                //         }
                //
                //     }
                //
                // }}
                //
                // ,{field: 'loginLog', title: '最后离线时间',sort:'true', width:170,templet: function(d){
                //     if(d.loginLog==undefined){
                //         return "";
                //     }else{
                //         if(d.loginLog.offlineTime==0){
                //             return "";
                //         }else{
                //             return UI.getLocalTime(d.loginLog.offlineTime);
                //         }
                //
                //     }
                //
                // }}
                , {field: '', title: '操作', width: 250, toolbar: '#barDemo', fixed: 'right'}
            ]
        ]
        , page: true
        , limits: [10, 20, 50]  //每页条数的选择项，默认：[10,20,30,40,50,60,70,80,90]。
        , limit: 10 //每页默认显示的数量
    });
    // 执行搜索，表格重载
    $('#do_search').click(function () {
        table.reload('roomTable', {
            method: 'post'
            , where: {
                "token": $.cookie("access_Token"),
                "userId": $.cookie("account"),
                "sysId": "",
                "sign": sign,
                "time": time,
                "reqData": {"name": $('#toomName').val(), "userSize": $('#userSize').val()}
            }
            , page: {
                curr: 1
            }
        });
    });

    // 操作
    table.on('tool(demo)', function (obj) {
        var data = obj.data;
        if (obj.event === 'update') {
            User.updateUser(obj.data,obj.data.userId);
        } else if (obj.event === 'recharge') {
            layer.prompt({title: '请输入充值金额', formType: 0,value: '1'}, function(money, index){
                // 充值金额（正整数）的正则校验
                /*if(!/^(?!00)(?:[0-9]{1,3}|1000)$/.test(money)){
                    layer.msg("请输入 1-1000 的整数",{"icon":2});
                    return;
                }*/
                /*if(1 != money){
                    layer.msg("每次只能充值1元",{"icon":2});
                    return;
                }*/
                $.ajax({
                    url: '/user/recharge',
                    data: JSON.stringify({
                        "token": $.cookie("access_Token"),
                        "userId": $.cookie("account"),
                        "sysId": "",
                        "sign": sign,
                        "time": time,
                        "reqData": {"money": money, "userId": data.userId}

                    }),
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    dataType: 'JSON',
                    success: function (res) {
                        layer.close(index); //关闭弹框
                        if (res.repCode == '0000') {
                            layer.msg("充值成功", {icon: 1});
                            table.reload("roomTable");
                        } else {
                            layer.msg(res.repMsg, {icon: 5});
                        }
                    },
                    error : function(result) {
                        layer.close(index); //关闭弹框
                        layer.msg("充值失败，请稍后重试", {icon: 1});
                    }
                });
            });
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么?', function (index) {
                $.ajax({
                            url: '/user/deleteRobot',
                            data: JSON.stringify({
                                "token": $.cookie("access_Token"),
                                "userId": $.cookie("account"),
                                "sysId": "",
                                "sign": sign,
                                "time": time,
                                "reqData": {"userId": data.userId}
                            }),
                            async: false,
                            type: "POST",
                            contentType: "application/json;charset=utf-8",
                            dataType: 'JSON',
                            success: function (res) {
                                if (res.repCode == '0000') {
                                    obj.del();
                                    layer.msg("删除成功", {icon: 1});
                                } else {
                                    layer.msg(res.repMsg, {icon: 5});
                                }
                    }
                });
            });
        } else if (obj.event === 'handCash') {
            layer.prompt({title: '请输入提现金额', formType: 0,value: '1'}, function(money, index){

                $.ajax({
                    url: '/user/handCash',
                    data: JSON.stringify({
                        "token": $.cookie("access_Token"),
                        "userId": $.cookie("account"),
                        "sysId": "",
                        "sign": sign,
                        "time": time,
                        "reqData": {"money": money, "userId": data.userId}
                    }),
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    dataType: 'JSON',
                    success: function (res) {
                        layer.close(index); //关闭弹框
                        if (res.repCode == '0000') {
                            layer.msg("提现成功", {icon: 1});
                            table.reload("roomTable");
                        } else {
                            layer.msg(res.repMsg, {icon: 5});
                        }
                    },
                    error : function(result) {
                        layer.close(index); //关闭弹框
                        layer.msg("提现失败，请稍后重试", {icon: 1});
                    }
                });
            });
        }
    });

});

var User= {
// 修改用户
    updateUser: function (data, userId) {
        // $(".password").hide();
        // $("#birthday").val("");
        $.ajax({
            url: '/user/selectUserById',
            data: JSON.stringify({
                "token": $.cookie("access_Token"),
                "userId": $.cookie("account"),
                "sysId": "",
                "sign": sign,
                "time": time,
                "reqData": {"userId": userId}
            }),
            async: false,
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'JSON',
            success: function (result) {
                // var userType = result.data.userType;
                // console.log("type:" + result.data.userType);
                if (result.repData != null) {
                    $("#userId").val(result.repData.id);
                    // alert($("#userId").val());
                    $("#userName").val(result.repData.nickname);
                    $("#telephone").val(result.repData.telephone);
                    // $('#isPublic').val(result.repData.userType);
                    $("#sex").val(result.repData.sex);
                    // $("#birthday").val(UI.getLocalTime(result.data.birthday));
                }
                $("#addUserTitle").empty();
                $("#addUserTitle").append("修改用户");
                $("#blocks").hide();
                $("#addUser").show();
                layui.form.render();
            }
        });

    }
}