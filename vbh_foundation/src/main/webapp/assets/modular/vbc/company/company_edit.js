/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyEditDlg = {
    editor: null,
    data: {
        companyId: $("#companyId").val(),
companyName: $("#companyName").val(),
companyPhone: $("#companyPhone").val(),
companyFax: $("#companyFax").val(),
companyEmail: $("#companyEmail").val(),
companyAddress: $("#companyAddress").val(),
companyRecordNo: $("#companyRecordNo").val(),
companyDesc: $("#companyDesc").val(),
companyCultureDesc: $("#companyCultureDesc").val(),
companyRewardDesc: $("#companyRewardDesc").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
CompanyEditDlg.close = function () {
    parent.layer.close(window.parent.Company.layerIndex);
};

/**
 * 验证表单
 */
CompanyEditDlg.validateForm = function () {

    //接收数据
    //CompanyEditDlg.data.title = $("#title").val();
    //CompanyEditDlg.data.content = CompanyEditDlg.editor.txt.html();

	CompanyEditDlg.data.companyId = $("#companyId").val();
CompanyEditDlg.data.companyName = $("#companyName").val();
CompanyEditDlg.data.companyPhone = $("#companyPhone").val();
CompanyEditDlg.data.companyFax = $("#companyFax").val();
CompanyEditDlg.data.companyEmail = $("#companyEmail").val();
CompanyEditDlg.data.companyAddress = $("#companyAddress").val();
CompanyEditDlg.data.companyRecordNo = $("#companyRecordNo").val();
//CompanyEditDlg.data.companyDesc = $("#companyDesc").val();
//CompanyEditDlg.data.companyCultureDesc = $("#companyCultureDesc").val();
//CompanyEditDlg.data.companyRewardDesc = $("#companyRewardDesc").val();
CompanyEditDlg.data.language = $("#language").val();


    var data = CompanyEditDlg.data;

    if (!data.companyName) {
        return "请输入公司名称";
    }

    if (!data.companyPhone) {
        return "请输入手机号";
    }
    if (!data.companyFax) {
        return "请输入传真";
    }
    if (!data.companyEmail) {
        return "请输入邮箱";
    }
    if (!data.companyAddress) {
        return "请输入地址";
    }
    if (!data.companyRecordNo) {
        return "请输入备案号";
    }

    return true;
};

/**
 * 提交添加角色
 */
CompanyEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/company/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.Company.table.refresh();
        parent.layer.close(window.parent.Company.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyEditDlg.ensure = function () {
    var result = CompanyEditDlg.validateForm();
    if (result === true) {
        CompanyEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyEditDlg.close = function () {
    CompanyEditDlg.close();
};

$(function () {

    /**
     * 下拉框
     */
    $("#language").val($("#language1").val());
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // CompanyEditDlg.editor = editor;

});
