/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var JobAddDlg = {
    editor: null,
    data: {
        jobCategoryId: "",
        jobName: "",
        jobNumber: "",
        jobLocation: "",
        jobYear: "",
        education: "",
        jobNum: "",
        salary: "",
        jobHighlights: "",
        jobDesc: "",
        jobNeedDesc: "",
        status: "",
        createBy: "",
        createTime: "",
        lastUpdateBy: "",
        lastUpdateTime: "",
        language: ""

    }
};

/**
 * 关闭此对话框
 */
JobAddDlg.close = function () {
    parent.layer.close(window.parent.Job.layerIndex);
};

/**
 * 验证表单
 */
JobAddDlg.validateForm = function () {
    //接收数据
    //JobAddDlg.data.title = $("#title").val();
    // JobAddDlg.data.content = JobAddDlg.editor.txt.html();
    JobAddDlg.data.jobCategoryId = $("#jobCategoryId").val();
    //JobAddDlg.data.jobName = $("#jobName").val();
    JobAddDlg.data.jobName  = encodeURIComponent($("#jobName").val());
    JobAddDlg.data.jobLocation = $("#jobLocation").val();
    JobAddDlg.data.jobYear = $("#jobYear").val();
    JobAddDlg.data.education = $("#education").val();
    JobAddDlg.data.jobNum = $("#jobNum").val();
    JobAddDlg.data.salary = $("#salary").val();
    JobAddDlg.data.jobHighlights = $("#jobHighlights").val();
    JobAddDlg.data.jobDesc = $("#jobDesc").val();
    JobAddDlg.data.jobNeedDesc = $("#jobNeedDesc").val();
    JobAddDlg.data.jobNumber = $("#jobNumber").val();//职位编号
    JobAddDlg.data.status = $('input:radio:checked').val();
    //$("#status").val();
    if ($("#language").val() == 1) {
        JobAddDlg.data.language = "CN";
    } else {
        JobAddDlg.data.language = "US";
    }
    //JobAddDlg.data.language = $("#language").val();


    var data = JobAddDlg.data;

    if (!data.jobName) {
        return "请输入内容";
    }

    if (!data.jobLocation) {
        return "请输入位置";
    }
    if (!data.jobYear) {
        return "请输入工作年限";
    }
    if (!data.jobNum) {
        return "请输入招聘人数";
    }
    if (!data.jobDesc) {
        return "请输入工作描述";
    }
    if (!data.jobNeedDesc) {
        return "请输入任职要求";
    }
    if (!data.education) {
        return "请输入学历要求";
    }
    if (!data.salary) {
        return "请输入薪资待遇";
    }
    if (!data.jobHighlights) {
        return "请输入职位亮点";
    }
    if (!data.jobNumber) {
        return "请输入职位编号";
    }

    return true;
};

/**
 * 提交添加角色
 */
JobAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/job/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.Job.table.refresh();
        parent.layer.close(window.parent.Job.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobAddDlg.ensure = function () {
    var result = JobAddDlg.validateForm();
    if (result === true) {
        JobAddDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
JobAddDlg.close = function () {
    JobAddDlg.close();
};

$(function () {
//初始化job的select数据
    var ajax = new $ax(Feng.ctxPath + "/jobCategory/listAll/");
    var result = ajax.start();
    $.each(result, function (index, item) {
        $("#jobCategoryId").append(  //此处向select中循环绑定数据
            "<option value=" + item.jobCategoryId + ">" + item.JobCategoryName + "</option>");

    });
    //debugger;
    //JobAddDlg.data = result.data;
    //初始化富文本编辑器
    // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //JobAddDlg.editor = editor2;

});
