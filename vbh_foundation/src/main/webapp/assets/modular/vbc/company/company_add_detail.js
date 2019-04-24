/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyAddDetailDlg = {
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
CompanyAddDetailDlg.close = function () {
    parent.layer.close(window.parent.Company.layerIndex);
};

/**
 * 验证表单
 */
CompanyAddDetailDlg.validateForm = function () {

    CompanyAddDetailDlg.data.companyId = $("#companyId").val();
    CompanyAddDetailDlg.data.companyName = $("#companyName").val();
    CompanyAddDetailDlg.data.companyPhone = $("#companyPhone").val();
    CompanyAddDetailDlg.data.companyFax = $("#companyFax").val();
    CompanyAddDetailDlg.data.companyEmail = $("#companyEmail").val();
    CompanyAddDetailDlg.data.companyAddress = $("#companyAddress").val();
    CompanyAddDetailDlg.data.companyRecordNo = $("#companyRecordNo").val();
   // CompanyAddDetailDlg.data.companyDesc = $("#companyDesc").val();
    CompanyAddDetailDlg.data.companyCultureDesc = $("#companyCultureDesc").val();
    CompanyAddDetailDlg.data.companyRewardDesc = $("#companyRewardDesc").val();
    CompanyAddDetailDlg.data.language = $("#language").val();

    //富文本
    var editor_data = CKEDITOR.instances.companyDesc.getData();
    editor_data = encodeURIComponent(editor_data);
    CompanyAddDetailDlg.data.companyDesc = editor_data;


    var data = CompanyAddDetailDlg.data;

    if (!data.companyDesc) {
        return "请输入内容";
    }

   // if (!data.content) {
   //     return "请输入内容";
   // }

    return true;
};

/**
 * 提交添加角色
 */
CompanyAddDetailDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/company/update", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.Company.table.refresh();
        parent.layer.close(window.parent.Company.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyAddDetailDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyAddDetailDlg.ensure = function () {
    var result = CompanyAddDetailDlg.validateForm();
    if (result === true) {
        CompanyAddDetailDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyAddDetailDlg.close = function () {
    CompanyAddDetailDlg.close();
};

$(function () {
    //初始化富文本编辑器


    var CKEditor = CKEDITOR.replace('companyDesc',
        {
            filebrowserBrowseUrl: Feng.ctxPath + '/api/fileUpload/showImage?Type=Image',
            filebrowserUploadUrl: Feng.ctxPath + '/api/fileUpload/upload?Type=Image'
        });

    var data = $("#companyDesc1").val();
    var dataa = $("#companyDesc").val();
    //debugger;
    CKEDITOR.instances.companyDesc.setData(data);
    //初始化富文本编辑器
   // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //CompanyAddDlg.editor = editor2;

});
