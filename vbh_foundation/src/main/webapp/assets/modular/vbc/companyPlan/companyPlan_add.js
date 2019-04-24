/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyPlanAddDlg = {
    editor: null,
    data: {
         planDate: "",
 planItem1: "",
 planItem2: "",
 planItem3: "",
 seq: "",
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
CompanyPlanAddDlg.close = function () {
    parent.layer.close(window.parent.CompanyPlan.layerIndex);
};

/**
 * 验证表单
 */
CompanyPlanAddDlg.validateForm = function () {

    //接收数据
    //CompanyPlanAddDlg.data.title = $("#title").val();
   // CompanyPlanAddDlg.data.content = CompanyPlanAddDlg.editor.txt.html();
    CompanyPlanAddDlg.data.planDate = $("#planDate").val();
CompanyPlanAddDlg.data.planItem1 = $("#planItem1").val();
CompanyPlanAddDlg.data.planItem2 = $("#planItem2").val();
CompanyPlanAddDlg.data.planItem3 = $("#planItem3").val();
CompanyPlanAddDlg.data.seq = $("#seq").val();
CompanyPlanAddDlg.data.language = $("#language").val();


    var data = CompanyPlanAddDlg.data;

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
CompanyPlanAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/companyPlan/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.CompanyPlan.table.refresh();
        parent.layer.close(window.parent.CompanyPlan.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyPlanAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyPlanAddDlg.ensure = function () {
    var result = CompanyPlanAddDlg.validateForm();
    if (result === true) {
        CompanyPlanAddDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyPlanAddDlg.close = function () {
    CompanyPlanAddDlg.close();
};

$(function () {

    //初始化富文本编辑器
   // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //CompanyPlanAddDlg.editor = editor2;

});
