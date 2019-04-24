/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyAddDlg = {
    editor: null,
    data: {
         companyName: "",
 companyPhone: "",
 companyFax: "",
 companyEmail: "",
 companyAddress: "",
 companyRecordNo: "",
 companyDesc: "",
 companyCultureDesc: "",
 companyRewardDesc: "",
 creatBy: "",
 createTime: "",
 lastUpdateBy: "",
 lastUpdateTime: "",
language: ""

    }
};

/**
 * 关闭此对话框
 */
CompanyAddDlg.close = function () {
    parent.layer.close(window.parent.Company.layerIndex);
};

/**
 * 验证表单
 */
CompanyAddDlg.validateForm = function () {

    //接收数据
    //CompanyAddDlg.data.title = $("#title").val();
   // CompanyAddDlg.data.content = CompanyAddDlg.editor.txt.html();
    CompanyAddDlg.data.companyName = $("#companyName").val();
CompanyAddDlg.data.companyPhone = $("#companyPhone").val();
CompanyAddDlg.data.companyFax = $("#companyFax").val();
CompanyAddDlg.data.companyEmail = $("#companyEmail").val();
CompanyAddDlg.data.companyAddress = $("#companyAddress").val();
CompanyAddDlg.data.companyRecordNo = $("#companyRecordNo").val();
CompanyAddDlg.data.companyDesc = $("#companyDesc").val();
CompanyAddDlg.data.companyCultureDesc = $("#companyCultureDesc").val();
CompanyAddDlg.data.companyRewardDesc = $("#companyRewardDesc").val();
CompanyAddDlg.data.language = $("#language").val();


    var data = CompanyAddDlg.data;

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
CompanyAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/company/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.Company.table.refresh();
        parent.layer.close(window.parent.Company.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyAddDlg.ensure = function () {
    var result = CompanyAddDlg.validateForm();
    if (result === true) {
        CompanyAddDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyAddDlg.close = function () {
    CompanyAddDlg.close();
};

$(function () {

    //初始化富文本编辑器
   // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //CompanyAddDlg.editor = editor2;

});
