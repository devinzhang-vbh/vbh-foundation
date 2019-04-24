/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var JobEditDlg = {
    editor: null,
    data: {
        jobId: $("#jobId").val(),
        jobCategoryId: $("#jobCategoryId").val(),
        jobName: $("#jobName").val(),
        jobNumber: $("#jobNumber").val(),
        jobLocation: $("#jobLocation").val(),
        jobYear: $("#jobYear").val(),
        education: $("#education").val(),
        jobNum: $("#jobNum").val(),
        salary: $("#salary").val(),
        jobHighlights: $("#jobHighlights").val(),
        jobDesc: $("#jobDesc").val(),
        jobNeedDesc: $("#jobNeedDesc").val(),
        status: $("#status").val(),
        language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
JobEditDlg.close = function () {
    parent.layer.close(window.parent.Job.layerIndex);
};

/**
 * 验证表单
 */
JobEditDlg.validateForm = function () {

    //接收数据
    //JobEditDlg.data.title = $("#title").val();
    //JobEditDlg.data.content = JobEditDlg.editor.txt.html();

    JobEditDlg.data.jobId = $("#jobId").val();
    JobEditDlg.data.jobCategoryId = $("#jobCategoryId").val();
    JobEditDlg.data.jobName =escape($("#jobName").val());
    JobEditDlg.data.jobLocation = $("#jobLocation").val();
    JobEditDlg.data.jobYear = $("#jobYear").val();
    JobEditDlg.data.education = $("#education").val();
    JobEditDlg.data.jobNum = $("#jobNum").val();
    JobEditDlg.data.salary = $("#salary").val();
    JobEditDlg.data.jobHighlights = $("#jobHighlights").val();
    JobEditDlg.data.jobDesc = $("#jobDesc").val();
    JobEditDlg.data.jobNeedDesc = $("#jobNeedDesc").val();
    JobEditDlg.data.jobNumber = $("#jobNumber").val();

    JobEditDlg.data.status = $('input:radio:checked').val();

    if ($("#language").val() == 1) {
        JobEditDlg.data.language = "CN";
    } else {
        JobEditDlg.data.language = "US";
    }
//JobEditDlg.data.language = $("#language").val();
    debugger;

    var data = JobEditDlg.data;

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
JobEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/job/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.Job.table.refresh();
        parent.layer.close(window.parent.Job.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobEditDlg.ensure = function () {
    var result = JobEditDlg.validateForm();
    if (result === true) {
        JobEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
JobEditDlg.close = function () {
    JobEditDlg.close();
};

$(function () {
    //查询数据库中job的redio
    var jobId = $("#jobId").val();
    var ajax = new $ax(Feng.ctxPath + "/job/query/" + jobId);
    var result = ajax.start();
    var ooo = $("#status").val();
    var ppp = $("#inlineRadio2").val();
    if (ooo == result.status) {
        //$('input:radio:first').attr('checked', 'checked');
        //$("#status").attr('checked', 'checked');
        $('input:radio:first').attr('checked', 'checked');
    } else if (ppp == result.status) {
        //$("#inlineRadio2").attr('checked', 'checked');
        $('input:radio:last').attr('checked', 'checked');
    }
    //查询job类别
    var ajax1 = new $ax(Feng.ctxPath + "/jobCategory/listAll/");
    var result1 = ajax1.start();
    $.each(result1, function (index, item) {
        if (result.jobCategoryId == item.jobCategoryId) {
            $("#jobCategoryId").append(  //此处向select中循环绑定数据
                "<option selected='selected'  value=" + item.jobCategoryId + ">" + item.JobCategoryName + "</option>");
        } else {
            $("#jobCategoryId").append(  //此处向select中循环绑定数据
                "<option value=" + item.jobCategoryId + ">" + item.JobCategoryName + "</option>");
        }

    });
    //显示language
    //var as=$("#language").val(value,result.status);
    //var lon= $("#language").val();
    var re = result.language;
    if (re == "CN") {
        re = "中文"
    } else if (re == "US") {
        re = "英文"
    }
    var lan = $("#language").val();
    var checkText = $("#language").find("option:selected").text(); //获取Select选择的text
    if (re == checkText) {
        $("#language").val("1");
    } else {
        $("#language").val("2");
    }


    /*if($("#language").val()){
       $("#language").val("1",result.language);
    }else{
        $("#language").val("2",result.language);
    }*/
    /*if($('input:radio:checked').val()==1){
        $('input:radio:first').attr('checked', 'checked');
    }else{
        $('input:radio:last').attr('checked', 'checked');
    }*/

    //JobAddDlg.data.status = $('input:radio:checked').val();
    debugger;
    //初始化编辑器
    // var E = window.wangEditor;
    //var editor = new E('#editor');
    // editor.create();
    // editor.txt.html($("#contentVal").val());
    // JobEditDlg.editor = editor;

});
