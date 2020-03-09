/**
 * 游戏规则编辑
 */
layui.use(['form', 'layer'],
    function() {
        $ = layui.jquery;
        var form = layui.form,
            layer = layui.layer;
        let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
        time = parseInt(time / 1000);
        var sign = hex_md5($.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token"));
        // 表单赋值
        $.ajax({
            url: '/shikuRoom/selectRoomGameRuleById',
            data: JSON.stringify({
                "token": $.cookie("access_Token"),
                "userId": $.cookie("account"),
                "sysId": "",
                "sign": sign,
                "time": time,
                "reqData": {"roomGameId": $('#id').val()}
            }),
            async: false,
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'JSON',
            success: function (res) {
                if (res.repCode == '0000') {
                    form.val('rule-edit', {
                        "receiveCount": res.repData.roomGameRule.receiveCount
                        ,"bombCount": res.repData.roomGameRule.bombCount
                        ,"chance": res.repData.roomGameRule.chance
                    });
                }
            }
        });


        //自定义验证规则
        form.verify({
            bombCount: function(value) {
                if (Number($('#bombCount').val()) > Number($('#receiveCount').val())) {
                    return '雷数必须小于等于包数';
                }
            },
            chance: [/^-?\d+\.?\d{0,2}$/, '赔率必须保留2位小数']
        });

        //监听提交
        form.on('submit(edit)',
            function(data) {
                let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
                time = parseInt(time / 1000);
                var sign = $.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token");
                $.ajax({
                    url: '/shikuRoom/editRoomGameRule',
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
                            "id": $('#id').val(),
                            "receiveCount": data.field.receiveCount,
                            "bombCount": data.field.bombCount,
                            "chance": data.field.chance
                        }
                    }),
                    //执行成功的回调函数
                    success: function (data) {
                        if (data.repCode) {
                            layer.alert("保存成功", {
                                    icon: 6
                                },
                                function () {
                                    //关闭当前frame
                                    xadmin.close();

                                    // 可以对父窗口进行刷新
                                    xadmin.father_reload();
                                });
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