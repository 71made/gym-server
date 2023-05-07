let equipmentData
$(function () {

    $("#member-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/member/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "器材 id", align:'center'},
            {field: "name", title: "器材名称", align:'center'},
            {field: "location", title: "地点位置",align:'center'},
            {
                field: "status", title: "状态", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-success text-white">使用中</label>`
                        case 1: return `<label class="badge badge-warning text-white">已移除</label>`
                        case 2: return `<label class="badge badge-danger text-white">丢失</label>`
                    }
                }
            },
            {field: "create_time", title:"创建时间", align:'center'},
            {field: "update_time", title:"更新时间", align:'center'},
            {
                title: "操作", align:'center', formatter: function (value, row, index) {
                    let operations = []
                    operations.push(`<button type="button" class="btn btn-link btn-rounded text-primary" onclick="showFunc(${row.id})">编辑</button>`)
                    operations.push(`<button type="button" class="btn btn-link btn-rounded text-danger" onclick="deleteFunc(${row.id})">删除</button>`)
                    return operations.join("");

                }
            }
        ]
    })
})

let showFunc = function (id) {
    equipmentData = $("#equipment-table").bootstrapTable('getRowByUniqueId', id)
    $("input[name='status-radios']").attr("checked", equipmentData.status)
    $("#name").attr("value", equipmentData.name)
    $("#location").attr("value", equipmentData.location)
}

let updateFunc = function () {

    equipmentData.name = $("#name").val()
    equipmentData.location = $("#location").val()
    equipmentData.status = $("input[name='status-radios']:checked").val()

    $.ajax({
        url: `/admin/equipment/update`,
        dataType: "json",
        method: "POST",
        data: JSON.stringify(equipmentData),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
let deleteFunc = function (id) {
    $.ajax({
        url: `/admin/equipment/update`,
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
        url: `/admin/equipment/update`,
        dataType: "json",
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
