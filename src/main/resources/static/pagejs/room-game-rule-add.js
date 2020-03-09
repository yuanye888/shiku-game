/**
 * 游戏规则添加
 */
layui.use(['form', 'layer'],
    function () {
        $ = layui.jquery;
        var form = layui.form,
            layer = layui.layer;
        //自定义验证规则
        form.verify({
            chance: [/^-?\d+\.?\d{0,2}$/, '赔率必须保留2位小数']
        });

        //监听提交
        form.on('submit(add)',
            function (data) {
                let time = parseInt(Math.round(new Date().getTime())) + parseInt($.cookie("currentTime"));
                time = parseInt(time / 1000);
                var sign = hex_md5($.cookie("apiKey") + time + $.cookie("account") + $.cookie("access_Token"));
                $.ajax({
                    url: '/shikuRoom/addRoomGameRule',
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
                            "roomJidId": $('#roomJidId').val(),
                            "roomId": $('#roomId').val(),
                            "type": $('#type').val(),
                            "receiveCount": data.field.receiveCount,
                            "bombCount": data.field.bombCount,
                            "chance": data.field.chance
                        }
                    }),
                    //执行成功的回调函数
                    success: function (data) {
                        if (data.repCode == "0000") {
                            layer.alert("增加成功", {
                                    icon: 6
                                },
                                function () {
                                    //关闭当前frame
                                    xadmin.close();

                                    // 可以对父窗口进行刷新
                                    xadmin.father_reload();
                                });
                        } else {
                            layer.msg(data.repMsg, {icon: 5});
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