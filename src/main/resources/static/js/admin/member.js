$(function () {

    $("#member-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/member/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "会员 id", align:'center'},
            {field: "card_number", title: "会员卡号", align:'center'},
            {field: "name", title: "会员姓名",align:'center'},
            {field: "phone", title:"联系方式", align:'center'},
            {
                field: "status", title: "状态", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-success text-white">正常</label>`
                        case 1: return `<label class="badge badge-warning text-white">异常</label>`
                        case 2: return `<label class="badge badge-danger text-white">禁用</label>`
                    }
                }
            },
            {
                field: "amount", title: "卡内余额", align:'center', formatter: function (value, row, index) {
                    if (!value || value === '' || value === null) return "-"
                    return `<span class="text-danger">¥${value}</span>`
                }
            },
            {field: "period", title:"剩余课时", align:'center'},
            {
                field: "type", title: "会员类型", align:'center', formatter: function (value, row, index) {
                    switch (value.code) {
                        case 0:
                            return `<label class="badge badge-primary text-white">${value.msg}</label>`;
                        case 1:
                            return `<label class="badge badge-secondary text-dark">${value.msg}</label>`;
                        case 2:
                            return `<label class="badge badge-warning text-white">${value.msg}</label>`;
                        default:
                            return "-";
                    }
                }
            },
            {field: "create_time", title:"创建时间", align:'center'},
            {field: "update_time", title:"更新时间", align:'center'},
            {
                title: "操作", align:'center', formatter: function (value, row, index) {
                    let operations = []
                    if ((row.type.code === 0 || row.type.code === 1) && row.status !== 2) {
                        operations.push(`<button type="button" class="btn btn-link btn-rounded text-success" onclick="upgradeFunc(${row.id})">升级</button>`)
                    }
                    if (row.status === 0 || row.status === 1) {
                       operations.push(`<button type="button" class="btn btn-link btn-rounded text-danger" onclick="forbiddenFunc(${row.id})">禁用</button>`)
                    }
                    if (row.status === 2) {
                        operations.push(`<button type="button" class="btn btn-link btn-rounded text-success" onclick="normalFunc(${row.id})">恢复</button>`)
                    }
                    operations.push(`<button type="button" class="btn btn-link btn-rounded text-danger" onclick="deleteFunc(${row.id})">删除</button>`)
                    return operations.join("");

                }
            }
        ]
    })
})

let upgradeFunc = function (id) {
    $.ajax({
        url: `/admin/member/upgrade?member_id=${id}`,
        dataType: "json",
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}

let normalFunc = function (id) {
    $.ajax({
        url: `/admin/member/update?member_id=${id}&status=0`,
        dataType: "json",
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
let deleteFunc = function (id) {
    $.ajax({
        url: `/admin/member/update?member_id=${id}&status=3`,
        dataType: "json",
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
let forbiddenFunc = function (id) {
    $.ajax({
        url: `/admin/member/update?member_id=${id}&status=2`,
        dataType: "json",
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
