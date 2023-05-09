let lostItemData
$(function () {

    $("#lost_item-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/lost_item/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "遗失物品 id", align:'center'},
            {field: "name", title: "物品名称", align:'center'},
            {field: "location", title: "拾取地点",align:'center'},
            {field: "notes", title: "备注",align:'center'},
            {field: "phone", title: "拾取人电话",align:'center'},
            {
                field: "status", title: "状态", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-success text-white">认领中</label>`
                        case 1: return `<label class="badge badge-warning text-white">已认领</label>`
                        case 2: return `<label class="badge badge-danger text-white">删除</label>`
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
    lostItemData = $("#lost_item-table").bootstrapTable('getRowByUniqueId', id)
    $(`input:radio[name='status-radios'][value='${lostItemData.status}']`).prop("checked", true)
    $("#name").val(lostItemData.name)
    $("#phone").val(lostItemData.phone)
    $("#location").val(lostItemData.location)
    $('#notes').val(lostItemData.notes)
    $("#lost_item-model").modal('show')
}

let updateFunc = function () {

    lostItemData.name = $("#name").val()
    lostItemData.location = $("#location").val()
    lostItemData.status = $("input[name='status-radios']:checked").val()
    lostItemData.notes=$("#notes").val()
    lostItemData.phone=$("#phone").val()
    $.ajax({
        url: `/admin/lost_item/update`,
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(lostItemData),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
let deleteFunc = function (id) {
    lostItemData = $("#lost_item-table").bootstrapTable('getRowByUniqueId', id)
    let deleteLostItem = {
        ...lostItemData
    }
    lostItemData.status = 3
    $.ajax({
        url: `/admin/lost_item/update`,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(deleteLostItem),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}
