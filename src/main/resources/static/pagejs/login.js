/**
 * 登陆管理
 */
$(function () {
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function (data) {
            var param = {
                "token":"",
                "userId":"",
                "sysId":"",
                "sign":"",
                "time":"",
                "reqData":data.field
            };
            $.ajax({
                url: '/login',
                type : "POST",
                data: JSON.stringify(param),
                contentType: "application/json;charset=utf-8",
                dataType: 'JSON',
                success: function (res) {
                    if (res.repCode == '0000') {
                        $.ajax({
                            url:'/getCurrentTime',
                            data:{},
                            async:false,
                            type: "POST",
                            success:function(result){
                                $.cookie("currentTime",result.repData.data-Math.round(new Date().getTime()));
                            }
                        });
                        $.cookie("access_Token",res.repData.data.access_Token);
                        $.cookie("role",res.repData.data.role);
                        $.cookie("account",res.repData.data.account);
                        $.cookie("adminId",res.repData.data.adminId);
                        $.cookie("apiKey",res.repData.data.apiKey);
                        $.cookie("nickname",res.repData.data.nickname);
                        $.cookie("registerInviteCode",res.repData.data.registerInviteCode); //系统邀请码模式
                        layer.msg("登录成功",{icon: 1});
                        setTimeout(function() {
                            location.replace("index.html");
                        }, 1000);
                    } else {
                        layer.msg(res.repMsg,{icon: 5});
                    }
                },
                error: function (data) {
                }
            })
            return false;
        });
    });
});