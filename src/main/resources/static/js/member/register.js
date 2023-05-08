$(function () {


    $("#to-login-btn").on("click", () => {
        window.location.replace("/pages/login")
    })

    $("#register-btn").on("click", () => {
        let number = $.trim($("#member-account").val());
        let password = $.trim($("#member-password").val());
        let rePassword = $.trim($("#re-member-password").val());
        let name =  $.trim($("#member-name").val())

        if (!number || number === '') {
            $("#register-notice").text( "卡号不能为空")
            $("#register-modal").modal('show')
            return
        }

        if (!password || password === '') {
            $("#register-notice").text( "密码不能为空")
            $("#register-modal").modal('show')
            return
        }

        if (!rePassword || rePassword === '' || rePassword !== password) {
            $("#register-notice").text( "密码和确认密码不一致")
            $("#register-modal").modal('show')
            return
        }

        if (!name || name === '') {
            $("#register-notice").text( "会员姓名不能为空")
            $("#register-modal").modal('show')
            return
        }

        $.ajax({
            url: "/member/register",
            dataType: "json",
            method: "POST",
            xhrFields: {
                withCredentials: true
            },
            contentType: "application/json",
            data: JSON.stringify({"phone": number, "password": password, "name": name}),
            beforeSend: function () {
                $("#register-btn").attr("disabled", true).text("注册中....").css("opacity", 0.8);
            },
            success: function (data) {
                if (data.success) {
                    $("#register-notice").text("是否跳转登陆")
                    $("#to-login-modal").modal('show')
                } else {
                    $("#register-notice").text(data.msg)
                    $("#register-modal").modal('show')
                }
            },
            complete: () => {
                $("#register-btn").attr("disabled", false).text("注册").css("opacity", 1);
            },
            error: () => {
                $("#register-notice").text("系统错误请重试")
                $("#register-modal").modal('show')
            }
        });
    })
})


