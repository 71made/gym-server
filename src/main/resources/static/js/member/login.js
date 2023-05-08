$(function () {
    $("#login-btn").on("click", () => {
        let number = $.trim($("#member-account").val());
        let password = $.trim($("#member-password").val());

        if (!number || number === '') {
            $("#login-notice").text( "卡号不能为空")
            $("#login-modal").modal('show')
            return
        }

        if (!password || password === '') {
            $("#login-notice").text( "密码不能为空")
            $("#login-modal").modal('show')
            return
        }

        $.ajax({
            url: "/member/login",
            dataType: "json",
            method: "POST",
            xhrFields: {
                withCredentials: true
            },
            data: {"card_number": number, "password": password},
            beforeSend: function () {
                $("#login-btn").attr("disabled", true).text("登录中....").css("opacity", 0.8);
            },
            success: function (data) {
                if (data.success) {
                    // 转跳并储存member信息
                    localStorage.setItem("member", JSON.stringify(data.data))
                    localStorage.setItem("member_id", data.data.id)
                    window.location.replace("/pages/index");
                } else {
                    $("#login-notice").text(data.msg)
                    $("#login-modal").modal('show')
                }
            },
            complete: () => {
                $("#login-btn").attr("disabled", false).text("登录").css("opacity", 1);
            },
            error: () => {
                $("#login-notice").text("系统错误请重试")
                $("#login-modal").modal('show')
            }
        });
    })
})
