/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyPlanEditDlg = {
    editor: null,
    data: {
        planId: $("#planId").val(),
planDate: $("#planDate").val(),
planItem1: $("#planItem1").val(),
planItem2: $("#planItem2").val(),
planItem3: $("#planItem3").val(),
seq: $("#seq").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
CompanyPlanEditDlg.close = function () {
    parent.layer.close(window.parent.CompanyPlan.layerIndex);
};

/**
 * 验证表单
 */
CompanyPlanEditDlg.validateForm = function () {

    //接收数据
    //CompanyPlanEditDlg.data.title = $("#title").val();
    //CompanyPlanEditDlg.data.content = CompanyPlanEditDlg.editor.txt.html();

	CompanyPlanEditDlg.data.planId = $("#planId").val();
CompanyPlanEditDlg.data.planDate = $("#planDate").val();
CompanyPlanEditDlg.data.planItem1 = $("#planItem1").val();
CompanyPlanEditDlg.data.planItem2 = $("#planItem2").val();
CompanyPlanEditDlg.data.planItem3 = $("#planItem3").val();
CompanyPlanEditDlg.data.seq = $("#seq").val();
CompanyPlanEditDlg.data.language = $("#language").val();


    var data = CompanyPlanEditDlg.data;

    if (!data.planDate) {
        return "请输入规划时间 ";
    }

    if (!data.planItem1) {
        return "请输入事项一";
    }
    if (!data.planItem2) {
        return "请输入事项二";
    }
    if (!data.planItem3) {
        return "请输入事项三";
    }
    if (!data.seq) {
        return "请输入序列";
    }

    return true;
};

/**
 * 提交添加角色
 */
CompanyPlanEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/companyPlan/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.CompanyPlan.table.refresh();
        parent.layer.close(window.parent.CompanyPlan.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyPlanEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyPlanEditDlg.ensure = function () {
    var result = CompanyPlanEditDlg.validateForm();
    if (result === true) {
        CompanyPlanEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyPlanEditDlg.close = function () {
    CompanyPlanEditDlg.close();
};

$(function () {

    $("#language").val($("#language1").val());
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // CompanyPlanEditDlg.editor = editor;

});
