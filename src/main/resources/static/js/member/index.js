$(function () {
    $.ajax({
        url: "/member/trade/all",
        method: "GET",
        dataType: "json",
        success: (res) => {
            if (res.success) {
                res.data.forEach(item => {
                    let type = "-"
                    let amount = "-"
                    let amountClass = ""
                    switch (item.type) {
                        case 0: {
                            type = "充值";
                            amount = "+¥" + item['amount']
                            amountClass = "text-success"
                        } break
                        case 1: {
                            type = "缴费";
                            amount = "-¥" + item['amount']
                            amountClass = "text-danger"
                        } break
                    }
                    $("#member-trade-table").append(`
                        <tr>
                        <td>${item.id}</td>
                        <td>${type}</td>
                        <td class="font-weight-medium">${"¥"+item['last_amount']}</td>
                        <td class="${amountClass} font-weight-medium">${amount}</td>
                        <td>${item['create_time']}</td>
                        <th><p class="font-weight-medium">${item['notes']}</p></th>
                        </tr>`)
                })
            }
        }
    })
})
