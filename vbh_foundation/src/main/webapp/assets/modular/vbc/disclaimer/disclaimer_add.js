/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var DisclaimerAddDlg = {
    editor: null,
    data: {
        disclaimerContent: "",
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
DisclaimerAddDlg.close = function () {
    parent.layer.close(window.parent.Disclaimer.layerIndex);
};

/**
 * 验证表单
 */
DisclaimerAddDlg.validateForm = function () {

    //接收数据
    //DisclaimerAddDlg.data.title = $("#title").val();
   // DisclaimerAddDlg.data.content = DisclaimerAddDlg.editor.txt.html();
    DisclaimerAddDlg.data.language = $("#language").val();
    //富文本
    var editor_data = CKEDITOR.instances.disclaimerContent.getData();
    editor_data = encodeURIComponent(editor_data);
    DisclaimerAddDlg.data.disclaimerContent = editor_data;


    var data = DisclaimerAddDlg.data;

    if (!data.disclaimerContent) {
        return "请输入免责声明内容";
    }

   // if (!data.content) {
   //     return "请输入内容";
   // }

    return true;
};

/**
 * 提交添加角色
 */
DisclaimerAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/disclaimer/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.Disclaimer.table.refresh();
        parent.layer.close(window.parent.Disclaimer.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(DisclaimerAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
DisclaimerAddDlg.ensure = function () {
    var result = DisclaimerAddDlg.validateForm();
    if (result === true) {
        DisclaimerAddDlg.addSubmit();
    } else {
        //Feng.alert(result);
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
DisclaimerAddDlg.close = function () {
    DisclaimerAddDlg.close();
};

$(function () {
//初始化富文本编辑器

    CKEDITOR.replace('disclaimerContent',
        {
            filebrowserBrowseUrl: Feng.ctxPath + '/api/fileUpload/showImage?Type=Image',
            filebrowserUploadUrl: Feng.ctxPath + '/api/fileUpload/upload?Type=Image'
        });
    //初始化富文本编辑器
   // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //DisclaimerAddDlg.editor = editor2;

});
