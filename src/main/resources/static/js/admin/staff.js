let staffData
$(function () {

    // 初始化日期选择器
    $("#entry-time").datetimepicker({
        language:  "zh-CN",
        autoclose: true,
        minView: 0,
        format: "yyyy-mm-dd hh:ii:ss",
        clearBtn: true,
        todayBtn: false,
    })


    $("#staff-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/staff/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "员工工号", align:'center'},
            {field: "name", title: "员工姓名", align:'center'},
            {
                field: "sex", title: "性别", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-primary text-white">男</label>`
                        case 1: return `<label class="badge badge-primary text-white">女</label>`
                    }
                }
            },
            {field: "id_card", title: "身份证",align:'center'},
            {
                field: "status", title: "状态", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-success text-white">在职</label>`
                        case 1: return `<label class="badge badge-danger text-white">已离职</label>`
                    }
                }
            },
            {
                field: "birthday_time", title: "年龄", align:'center',
                formatter: function (value, row, index) {
                    console.log(getAge(value))
                    return getAge(value)
                }
            },
            {
                field: "position", title: "职位", align:'center',
                formatter: function (value, row, index) {
                    return `<label class="badge badge-primary text-white">${value.msg}</label>`
                }
            },
            {field: "entry_time", title:"入职时间", align:'center'},
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

let showAddFunc = function () {
    // 隐藏和展示部分元素
    $("#form-id").hide()
    $("#staff-edit-btn").hide()
    $("#staff-add-btn").show()
    $("#staff-model-label").text("增加员工")

    $("#position").val(0)
    $("#sex").val(0)
    $("#id-card").val("")
    $(`input:radio[name='status-radios'][value='0']`).prop("checked", true)
    $(`input:radio[name='status-radios']`).attr("disabled", true)
    $("#name").val("")
    $("#entry-time").val("")
    $("#staff-model").modal('show')
}

let showFunc = function (id) {
    // 隐藏和展示部分元素
    $("#form-id").show()
    $("#staff-edit-btn").show()
    $("#staff-add-btn").hide()
    $("#staff-model-label").text("修改信息")

    staffData = $("#staff-table").bootstrapTable('getRowByUniqueId', id)

    $("#id").val(staffData.id)
    $("#position").val(staffData['position'].code)
    $("#sex").val(staffData.sex)
    $("#id-card").val(staffData['id_card'])
    $(`input:radio[name='status-radios']`).attr("disabled", false)
    $(`input:radio[name='status-radios'][value='${staffData.status}']`).prop("checked", true)
    $("#name").val(staffData.name)
    $("#entry-time").val(staffData['entry_time'])
    $("#staff-model").modal('show')
}

let closeModal = function () {
    $("#staff-model").modal('hide')
}

let addFunc = function () {
    let addStaff = {
        name: $("#name").val(),
        id_card:  $("#id-card").val(),
        sex: $("#sex").val(),
        position: $("#position").val(),
        entry_time: $("#entry-time").val()
    }

    $.ajax({
        url: `/admin/staff/add`,
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(addStaff),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}

let updateFunc = function () {
    let updateStaff = {
        id: $("#id").val(),
        name: $("#name").val(),
        id_card:  $("#id-card").val(),
        sex: $("#sex").val(),
        position: $("#position").val(),
        status: $("input[name='status-radios']:checked").val(),
        entry_time: $("#entry-time").val()
    }

    $.ajax({
        url: `/admin/staff/update`,
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(updateStaff),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}
let deleteFunc = function (id) {
    staffData = $("#staff-table").bootstrapTable('getRowByUniqueId', id)
    let deleteStaff = {
        ...staffData
    }
    deleteStaff.status = 3
    $.ajax({
        url: `/admin/staff/update`,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(deleteStaff),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}

/*
 * @param str {string}  'yyyy-MM-dd HH:mm:ss '
 * @return {string}
*/
function getAge(str){

    let d = new Date(str);
    let returnStr = "";

    let date = new Date();
    let yearNow = date.getFullYear();
    let monthNow = date.getMonth() + 1;
    let dayNow = date.getDate();

    let Y = yearNow - d.getFullYear();
    let M = monthNow - d.getMonth();
    let D = dayNow - d.getDay();
    if(D < 0){
        D = D + 30; //借一个月
        M--;
    }
    if(M<0){  // 借一年 12个月
        Y--;
        M = M + 12; //
    }

    if(Y<0){
        returnStr = "-";
    }else {
        returnStr = Y + "岁";
    }

    return returnStr;
}
