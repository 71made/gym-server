const accountInfo = localStorage.getItem("admin") || localStorage.getItem("member")

$(function () {
    let init = function () {
        if (!accountInfo) {
            window.location.replace("/pages/login")
        } else {
            if (localStorage.getItem("admin_id") && window.location.pathname.startsWith("/pages/admin")) {
                let obj = JSON.parse(localStorage.getItem("admin"))
                $("#account-name").text(obj.account)
                $("#account-type").text("系统管理员")
                $("#account-info").hide()
                return
            }
            if (localStorage.getItem("member_id")) {
                let obj = JSON.parse(localStorage.getItem("member"))
                $("#account-name").text(obj.name)
                $("#left-top").hide()
                $("#account-info").show().on("click", () => {
                    window.location.replace("/pages/index")
                })
                let text = ""
                let styleClass = ""
                switch (obj['type'].code) {
                    default:
                    case 0: {
                        text = "普通会员"
                    } break
                    case 1: {
                        text = "白银会员";
                        styleClass = "bg-secondary text-dark"
                    } break
                    case 2: {
                        text = "黄金会员";
                        styleClass = "bg-warning text-white"
                    } break
                }
                $("#account-type").text(text).addClass(styleClass)
            }
        }
    }
    // 调用初始化
    init()


    $("#logout").on("click", () => {
        if (localStorage.getItem("admin_id")) {
            $.ajax({
                url: "/admin/logout",
                method: "GET",
                dataType: "json",
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.success) {
                        // 刷新并清除admin信息
                        localStorage.removeItem("admin")
                        localStorage.removeItem("admin_id")
                        window.location.replace("/pages/admin/login");
                    }
                }
            })
        } else if (localStorage.getItem("member_id")) {
            $.ajax({
                url: "/member/logout",
                method: "GET",
                dataType: "json",
                xhrFields: {
                    withCredentials: true
                },
                success: function (data) {
                    if (data.success) {
                        // 刷新并清除member信息
                        localStorage.removeItem("member")
                        localStorage.removeItem("member_id")
                        window.location.replace("/pages/login");
                    }
                }
            })
        }
    })
})
