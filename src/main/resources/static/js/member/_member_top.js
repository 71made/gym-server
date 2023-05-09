$(function () {

    $.ajax({
        url: "/member/info",
        method: "GET",
        dataType: "json",
        success: res => {
            if (res.success) {
                $("#card-number").text(res.data['card_number'])
                $("#member-name").text(res.data['name'])
                $("#member-phone").text(res.data['phone'])
                switch (res.data['type'].code) {
                    default:
                    case 0: {
                        $("#member-type").append(`
                        <p class="text-white mb-0">${res.data['type'].msg}</p>
                        <small class="text-white mb-0">可联系管理员办理会员升级</small>`)
                    } break
                    case 1: {
                        $("#member-type").append(`
                        <p class="text-dark mb-0 badge badge-secondary">${res.data['type'].msg}</p>
                        <small class="text-white mb-0">可联系管理员办理会员升级</small>`)
                    } break
                    case 2: {
                        $("#member-type").append(`
                        <p class="text-white mb-0 bg-warning">${res.data['type'].msg}</p>`)
                    } break
                }
                $("#member-period").text(res.data['period']+" h")
                $("#member-amount").text("¥"+res.data['amount'])

            } else {
                localStorage.removeItem("member")
                localStorage.removeItem("member_id")
                window.location.replace("/pages/login");
            }

        }
    })
})

let recharge = function () {
    let amount = $("#recharge-amount").val()
    if (amount === undefined || amount <= 0) {
        $("#recharge-notice").show().text("金额错误")
        return
    }
    $.ajax({
        url: "/member/recharge",
        method: "POST",
        dataType: "json",
        data: {"amount": amount},
        success: res => {
            if (res.success) {
                $("#recharge-notice").hide()
                window.location.reload()
            }
            $("#recharge-notice").show().text(res['msg'])
        }
    })
}
