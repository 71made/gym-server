let staffData
$(function () {

    // 初始化日期选择器
    $("#entry-time").datepicker({
        language:  "zh-CN",
        autoclose: true,
        startView: 0,
        format: "yyyy-mm-dd",
        clearBtn: true,
        todayBtn: false,
        endDate: new Date()
    })


    $("#staff-table").bootstrapTable({
        uniqueId: "id", // 唯一键
        dataField: "data",
        dataType: "json",
        url: "/admin/staff/all", // 请求后台
        method: "GET",
        columns: [
            {field: "id", title: "员工 id", align:'center'},
            {field: "code", title: "员工工号", align:'center'},
            {field: "name", title: "员工姓名", align:'center'},
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
                    return getAge(value)
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

let showFunc = function (id) {
    staffData = $("#staff-table").bootstrapTable('getRowByUniqueId', id)
    $("#position").val(staffData['position'].code)
    $("#id-card").val(staffData['id_card'])
    $(`input:radio[name='status-radios'][value='${staffData.status}']`).prop("checked", true)
    $("#name").val(staffData.name)
    $("#entry-time").val(staffData['entry_time'])
    $("#staff-model").modal('show')
}

let updateFunc = function () {
    let updateStaff = {
        name: $("#name").val(),
        id_card:  $("#id-card").val(),
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
        }
    })
}
let deleteFunc = function (id) {
    $.ajax({
        url: `/admin/staff/update`,
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            id: id,
            status: 2,
        }),
        success: (res) => {
            if (res.success) {
                window.location.reload()
            }
        }
    })
}

/*
 * @param str {string}  'yyyy-MM-dd HH:mm:ss '
 * @return {string}
*/
function getAge(str){
    const r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/);
    if(r==null)return   false;

    let d = new Date(r[1], r[3] - 1, r[4]);
    let returnStr = "";

    if(d.getFullYear()===r[1]&&(d.getMonth()+1)===r[3]&&d.getDate()===r[4]){

        let date = new Date();
        let yearNow = date.getFullYear();
        let monthNow = date.getMonth() + 1;
        let dayNow = date.getDate();

        let largeMonths = [1,3,5,7,8,10,12], //大月， 用于计算天，只在年月都为零时，天数有效
            lastMonth = monthNow -1>0?monthNow-1:12,  // 上一个月的月份
            isLeapYear = false, // 是否是闰年
            daysOFMonth = 0;    // 当前日期的上一个月多少天

        if((yearNow%4===0&&yearNow%100!==0)||yearNow%400===0){  // 是否闰年， 用于计算天，只在年月都为零时，天数有效
            isLeapYear = true;
        }

        if(largeMonths.indexOf(lastMonth)>-1){
            daysOFMonth = 31;
        }else if(lastMonth===2){
            if(isLeapYear){
                daysOFMonth = 29;
            }else{
                daysOFMonth = 28;
            }
        }else{
            daysOFMonth = 30;
        }

        let Y = yearNow - parseInt(r[1]);
        let M = monthNow - parseInt(r[3]);
        let D = dayNow - parseInt(r[4]);
        if(D < 0){
            D = D + daysOFMonth; //借一个月
            M--;
        }
        if(M<0){  // 借一年 12个月
            Y--;
            M = M + 12; //
        }

        if(Y<0){
            returnStr = "-";

        }else{
            returnStr = Y+"岁";
        }

    }

    return returnStr;
}
