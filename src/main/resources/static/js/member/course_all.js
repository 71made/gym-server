$(function () {
    $.ajax({
        url: "/member/course/all",
        method: "GET",
        dataType: "json",
        success: (res) => {
            if (res.success) {
                res.data.forEach(item => {
                    let btn = `<button type="button" class="btn btn-link text-primary" onclick="">加入课程</button>`
                    if (item['status'] === 0) btn = `<button type="button" class="btn btn-link text-primary" onclick="addFunc(${item.id})">加入课程</button>`
                    if (item['status'] === 1) btn = `<button type="button" class="btn btn-link text-warning" disabled>已停课</button>`
                    if (new Date() > new Date(item['end_time'])) btn = `<button type="button" class="btn btn-link text-danger" disabled>已结束</button>`
                    $("#course-box").append(`
                       <div class="d-flex py-2 border-bottom" style="align-items: center">
                            <span id="staff-face" class="img-sm rounded-circle bg-warning text-white text-avatar">${item['staff_name'].substr(0, 1)}</span>
                            <div class="wrapper ml-2">
                                <p class="mb-n1 font-weight-semibold">${item['staff_name']}</p>
                                <small>教练</small>
                            </div>
                            <div class="wrapper m-auto">
                                <p id="course-name" class="font-weight-semibold mb-0">${item['name']}</p>
                                <div class="mb-0">
                                    <small>课时: </small><small id="course-period">${item['period']} h</small>
                                    <small class="pl-2">报名人数: </small><small id="course-member-count">${item['member_count']} 人</small>
                                </div>
                            </div>
                            <div class="wrapper p-1 m-auto">
                                <h4 id="course-amount" class="text-danger mb-0">¥${item['amount']}</h4>
                            </div>
                            <div class="wrapper ml-auto">
                                <small id="course-time">${item['start_time']}- ${item['end_time']}</small>
                                ${btn}
                            </div>
                       </div>`)
                })
            }
        }
    })
})

let addFunc = function (courseId) {
    let memberId = localStorage.getItem("member_id")
    if (!memberId || memberId === "") {
        localStorage.removeItem("member")
        localStorage.removeItem("member_id")
        window.location.replace("/pages/login");
    }
    $.ajax({
        url: "/member/course/add",
        dataType: "json",
        contentType: "application/json",
        method: "POST",
        data: JSON.stringify({"course_id": courseId, "member_id": memberId}),
        success: (res) => {
            if (res.success) {
                // 加入成功跳转我的课程
                window.location.replace("/pages/course")
            }
            $("#notice").text(res.msg)
            $("#notice-modal").modal('show')
        }
    })
}
