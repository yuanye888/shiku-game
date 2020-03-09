/**
 * 群组管理
 */
/*加载群组列表*/
layui.use('table', function () {
    let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
    time = parseInt(time / 1000);
    var sign = hex_md5($.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token"));
    var table = layui.table;
    table.render({
        elem: '#roomTable'
        , url: '/shikuRoom/selectAllShikuRoom'
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
                {field: 'name', width: 120, title: '群组名称'}
                , {field: 'desc', width: 220, title: '群组说明'}
                , {field: 'userId', width: 120, title: '创建者ID'}
                , {field: 'nickname', width: 120, title: '创建者昵称'}
                , {field: 'userSize', width: 70, title: '群人数'}
                , {
                field: 'redPacketLock', width: 120, title: '红包状态', templet: function (d) {
                    if (d.redPacketLock == "-1") {
                        return "红包未锁定";
                    } else if (d.redPacketLock == "1") {
                        return "红包已锁定";
                    }
                }
            }
                , {
                field: 'createTime', width: 160, title: '创建时间', templet: function (t) {
                    return timeFormat(t.createTime, "yyyy-mm-dd hh:mm:ss");
                }
            }
                , {field: '', title: '操作', width: 750, toolbar: '#barDemo', fixed: 'right'}
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
        if (obj.event === 'groupchat') {
            location.href = 'groupchat-logs-list.html?room_jid_id=' + data.jid;
        } else if (obj.event === 'usermanager') {
            location.href = 'room-usermanager-list.html?room_id=' + data.id;
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么?', function (index) {
                $.ajax({
                    url: '/shikuRoom/deleteShikuRoom',
                    data: JSON.stringify({
                        "token": $.cookie("access_Token"),
                        "userId": $.cookie("account"),
                        "sysId": "",
                        "sign": sign,
                        "time": time,
                        "reqData": {"id": data.id}
                    }),
                    async: false,
                    type: "POST",
                    contentType: "application/json;charset=utf-8",
                    dataType: 'JSON',
                    success: function (res) {
                        if (res.repCode == '0000') {
                            obj.del();
                            layer.msg("删除成功", {icon: 1});
                        }
                    }
                });
            });
        } else if (obj.event === 'lock') {
            $.ajax({
                url: '/shikuRoom/editRoomRedPacketLock',
                data: JSON.stringify({
                    "token": $.cookie("access_Token"),
                    "userId": $.cookie("account"),
                    "sysId": "",
                    "sign": sign,
                    "time": time,
                    "reqData": {"roomId": data.id, "redPacketLock": "1"}
                }),
                async: false,
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'JSON',
                success: function (res) {
                    if (res.repCode == '0000') {
                        layer.msg("已锁定", {icon: 1});
                        table.reload("roomTable");
                    } else {
                        layer.msg(res.repMsg, {icon: 5});
                    }
                }
            });
        } else if (obj.event === 'cancelLock') {
            $.ajax({
                url: '/shikuRoom/editRoomRedPacketLock',
                data: JSON.stringify({
                    "token": $.cookie("access_Token"),
                    "userId": $.cookie("account"),
                    "sysId": "",
                    "sign": sign,
                    "time": time,
                    "reqData": {"roomId": data.id, "redPacketLock": "-1"}
                }),
                async: false,
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'JSON',
                success: function (res) {
                    if (res.repCode == '0000') {
                        layer.msg("已解锁", {icon: 1});
                        table.reload("roomTable");
                    } else {
                        layer.msg(res.repMsg, {icon: 5});
                    }
                }
            });
        } else if (obj.event === 'gameRule') {
            location.href = 'room-game-rule.html?room_id=' + data.id + "&room_jid_id=" + data.jid;
        }
    });

});