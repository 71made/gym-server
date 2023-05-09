let courseData = {}
$(function () {

    // 初始化日期选择器
    $("#start-time").datetimepicker({
        language:  "zh-CN",
        autoclose: true,
        minView: 0,
        format: "yyyy-mm-dd hh:ii:ss",
        clearBtn: true,
        todayBtn: false,
    }).on("changeDate", function (e) {
        $('#end-time').datetimepicker('setStartDate', e.date);
    });

    // 初始化日期选择器
    $("#end-time").datetimepicker({
        language:  "zh-CN",
        autoclose: true,
        minView: 0,
        format: "yyyy-mm-dd hh:ii:ss",
        clearBtn: true,
        todayBtn: false,
        startDate: new Date(),
    }).on("changeDate", function (e) {
        $('#start-time').datetimepicker('setEndDate', e.date);
    });

    let init = function () {
        $.ajax({
            dataType: "json",
            url: "/admin/staff/all", // 请求后台
            method: "GET",
            success: (res) => {
                if (res.success) {
                    res.data.forEach(staff => {
                        if (staff.position.code === 1) {
                            $("#staff").append(`<option value="${staff.id}">${staff.id + '-' + staff.name}</option>`)
                        }
                    });
                }
            },
        })
    }
    // 构建下拉框
    init()


    $("#course-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/course/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "课程 ID", align:'center'},
            {field: "name", title: "课程名称", align:'center'},
            {field: "staff_id", title: "教练工号",align:'center'},
            {
                field: "status", title: "状态", align:'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0: return `<label class="badge badge-success text-white">正常授课</label>`
                        case 1: return `<label class="badge badge-danger text-white">停止授课</label>`
                    }
                }
            },
            {field: "amount", title:"报名金额", align:'center'},
            {field: "period", title:"课时", align:'center'},
            {field: "start_time", title:"开始时间", align:'center'},
            {field: "end_time", title:"结束时间", align:'center'},
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
    $("#course-edit-btn").hide()
    $("#course-add-btn").show()
    $("#course-model-label").text("增加课程")

    $(`input:radio[name='status-radios'][value='0']`).prop("checked", true)
    $(`input:radio[name='status-radios']`).attr("disabled", true)
    $("#name").val("")
    $("#id").val(courseData.id)
    $("#period").val("")
    $("#amount").val("")
    $("#staff").val("")
    $("#start-time").val("")
    $("#end-time").val("")
    $("#course-model").modal('show')
}

let showFunc = function (id) {
    // 隐藏和展示部分元素
    $("#form-id").show()
    $("#course-edit-btn").show()
    $("#course-add-btn").hide()
    $("#course-model-label").text("修改信息")

    courseData = $("#course-table").bootstrapTable('getRowByUniqueId', id)

    $("#id").val(id)
    $("#period").val(courseData['period'])
    $("#amount").val(courseData['amount'])
    $("#staff").val(courseData['staff_id'])
    $(`input:radio[name='status-radios']`).attr("disabled", false)
    $(`input:radio[name='status-radios'][value='${courseData.status}']`).prop("checked", true)
    $("#name").val(courseData.name)
    $("#start-time").val(courseData['start_time'])
    $("#end-time").val(courseData['end_time'])
    $("#course-model").modal('show')
}

let closeModal = function () {
    $("#course-model").modal('hide')
}

let addFunc = function () {
    let addCourse = {
        name: $("#name").val(),
        staff_id:  $("#staff").val(),
        period: $("#period").val(),
        amount: $("#amount").val(),
        start_time: $("#start-time").val(),
        end_time: $("#end-time").val()
    }

    $.ajax({
        url: `/admin/course/add`,
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(addCourse),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}

let updateFunc = function () {
    let updateCourse = {
        id: $("#id").val(),
        name: $("#name").val(),
        staff_id: $("#staff").val(),
        period: $("#period").val(),
        amount: $("#amount").val(),
        status: $("input[name='status-radios']:checked").val(),
        start_time: $("#start-time").val(),
        end_time: $("#end-time").val()
    }

    $.ajax({
        url: `/admin/course/update`,
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify(updateCourse),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}
let deleteFunc = function (id) {
    courseData = $("#course-table").bootstrapTable('getRowByUniqueId', id)
    let deleteCourse = {
        ...courseData
    }
    deleteCourse.status = 2
    $.ajax({
        url: `/admin/course/update`,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(deleteCourse),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
            closeModal()
        }
    })
}
