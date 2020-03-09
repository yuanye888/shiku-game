/**
 * 游戏规则
 */
/*加载聊天记录列表*/
layui.use(['table', 'form', 'layer'], function () {
    let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
    time = parseInt(time / 1000);
    var sign = hex_md5($.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token"));
    var table = layui.table;
    var form = layui.form;
    table.render({
        elem: '#roomGameRuleTable'
        , url: '/shikuRoom/selectRoomGameRule'
        , where: {
            "token": $.cookie("access_Token"),
            "userId": $.cookie("account"),
            "sysId": "",
            "sign": sign,
            "time": time,
            "reqData": {"roomJidId": $('#roomJidId').html()}
        }
        , contentType: "application/json;charset=utf-8"
        , dataType: "JSON"
        , method: "POST"
        , parseData: function (res) {
            form.val('edit-money', {
                "minMoney": res.repData.shikuRoom.minMoney
                , "maxMoney": res.repData.shikuRoom.maxMoney
            });
            return {
                "code": res.repCode,
                "msg": res.repMsg,
                "count": res.repData.count,
                "data": res.repData.roomGameRules.data
            }
        },
        response: {
            statusCode: "0000" //重新规定成功的状态码为 200，table 组件默认为 0
        }
        , cols: [[
            {field: 'receiveCount', width: 300, title: '包数'}
            , {field: 'bombCount', width: 300, title: '雷数'}
            , {field: 'chance', width: 300, title: '赔率'}
            , {field: 'right', title: '操作', toolbar: '#gameRuleBar', align: 'left'}
        ]]
    });

    // 操作
    table.on('tool(demo)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            layer.msg('ID：' + data.id + ' 的查看操作');
        } else if (obj.event === 'del') {
            layer.confirm('真的删除行么?', function (index) {
                $.ajax({
                    url: '/shikuRoom/deleteRoomGameRule',
                    data: JSON.stringify({
                        "token": $.cookie("access_Token"),
                        "userId": $.cookie("account"),
                        "sysId": "",
                        "sign": sign,
                        "time": time,
                        "reqData": {"roomGameId": data.id}
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
        } else if (obj.event === 'edit') {
            xadmin.open('编辑', 'room-game-rule-edit.html?id=' + data.id, 500, 350);
        }
        ;
    });
    //自定义验证规则
    form.verify({
        minMoney: function (value) {
            if (value.length < 1) {
                return '请填写最小金额';
            }
            var re = /^[0-9]+$/;
            if (!re.test(value)) {
                return '最小金额只能是正整数';
            }
        },
        maxMoney: function (value) {
            if (value.length < 1) {
                return '请填写最大金额';
            }
            var re = /^[0-9]+$/;
            if (!re.test(value)) {
                return '最大金额只能是正整数';
            }
            if (Number($('#minMoney').val()) > Number($('#maxMoney').val())) {
                return '最小金额必须小于等于最大金额';
            }
        }
    });

    //监听提交
    form.on('submit(save)',
        function (data) {
            let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
            time = parseInt(time / 1000);
            var sign = $.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token");
            $.ajax({
                url: '/shikuRoom/editRoomRedPacketMinMax',
                type: 'post',
                dataType: 'json',
                contentType: "application/json",
                async: true,//异步请求
                cache: false,
                data: JSON.stringify({
                    "token": $.cookie("access_Token"),
                    "userId": $.cookie("account"),
                    "sysId": "",
                    "sign": sign,
                    "time": time,
                    "reqData": {
                        "id": $('#roomId').html(),
                        "minMoney": data.field.minMoney,
                        "maxMoney": data.field.maxMoney
                    }
                }),
                //执行成功的回调函数
                success: function (data) {
                    if (data.repCode) {
                        layer.msg("保存成功", {icon: 1});
                    }
                },
                //执行失败或错误的回调函数
                error: function (data) {
                    layer.msg(data.repMsg, {icon: 5});
                }
            });
            return false;
        });
});

// 执行添加，加载游戏规则添加页面
function addRoomGameRule() {
    var type = $(".layui-tab-title>.layui-this").attr("lay-id");
    xadmin.open('添加规则', 'room-game-rule-add.html?roomId=' + $('#roomId').html() + "&roomJidId=" + $('#roomJidId').html() + "&type=" +type, 500, 350);
};