$(function () {
    $("#login-btn").on("click", () => {
        let adminName = $.trim($("#admin-account").val());
        let adminPassword = $.trim($("#admin-password").val());

        if (!adminName || adminName === '') {
            $("#login-notice").text( "用户名不能为空")
            $("#login-modal").modal('show')
            return
        }

        if (!adminPassword || adminPassword === '') {
            $("#login-notice").text( "密码不能为空")
            $("#login-modal").modal('show')
            return
        }

        $.ajax({
            url: "/admin/login",
            dataType: "json",
            type: "post",
            xhrFields: {
                withCredentials: true
            },
            data: {"account": adminName, "password": adminPassword},
            beforeSend: function () {
                $("#login-btn").attr("disabled", true).text("登录中....").css("opacity", 0.8);
            },
            success: function (data) {
                if (data.success) {
                    // 转跳并储存admin信息
                    localStorage.setItem("admin", JSON.stringify(data.data))
                    localStorage.setItem("admin_id", data.data.id)
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
