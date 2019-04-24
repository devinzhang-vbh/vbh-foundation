/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var DisclaimerEditDlg = {
    editor: null,
    data: {
        disclaimerId: $("#disclaimerId").val(),
        disclaimerContent: $("#disclaimerContent").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
DisclaimerEditDlg.close = function () {
    parent.layer.close(window.parent.Disclaimer.layerIndex);
};

/**
 * 验证表单
 */
DisclaimerEditDlg.validateForm = function () {

    //接收数据
    //DisclaimerEditDlg.data.title = $("#title").val();
    //DisclaimerEditDlg.data.content = DisclaimerEditDlg.editor.txt.html();

	DisclaimerEditDlg.data.disclaimerId = $("#disclaimerId").val();
    var editor_data = CKEDITOR.instances.disclaimerContent.getData();
    editor_data =  encodeURIComponent(editor_data);

	DisclaimerEditDlg.data.disclaimerContent = editor_data;
DisclaimerEditDlg.data.language = $("#language").val();


    var data = DisclaimerEditDlg.data;

    if (!data.disclaimerContent) {
        return "请输入免责声明内容";
    }

    //if (!data.content) {
    //    return "请输入内容";
    //}

    return true;
};

/**
 * 提交添加角色
 */
DisclaimerEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/disclaimer/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.Disclaimer.table.refresh();
        parent.layer.close(window.parent.Disclaimer.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(DisclaimerEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
DisclaimerEditDlg.ensure = function () {
    var result = DisclaimerEditDlg.validateForm();
    if (result === true) {
        DisclaimerEditDlg.editSubmit();
    } else {
        //Feng.alert(result);
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
DisclaimerEditDlg.close = function () {
    DisclaimerEditDlg.close();
};

$(function () {
//初始化富文本编辑器

    var CKEditor = CKEDITOR.replace('disclaimerContent',
        {
            filebrowserBrowseUrl: Feng.ctxPath + '/api/fileUpload/showImage?Type=Image',
            filebrowserUploadUrl: Feng.ctxPath + '/api/fileUpload/upload?Type=Image'
        });
    var data = $("#disclaimerContent1").val();
    CKEDITOR.instances.disclaimerContent.setData(data);
    /**
     * 下拉框
     */
    $("#language").val($("#language1").val());
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // DisclaimerEditDlg.editor = editor;

});
