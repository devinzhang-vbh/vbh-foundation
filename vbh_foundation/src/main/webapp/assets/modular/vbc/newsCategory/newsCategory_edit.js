/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var NewsCategoryEditDlg = {
    editor: null,
    data: {
        newsCategoryId: $("#newsCategoryId").val(),
        newsCategoryName: $("#newsCategoryName").val(),
        seq: $("#seq").val(),
        //status: $("#status").val(),
        status: $('input:radio:checked').val(),
        language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
NewsCategoryEditDlg.close = function () {
    parent.layer.close(window.parent.NewsCategory.layerIndex);
};

/**
 * 验证表单
 */
NewsCategoryEditDlg.validateForm = function () {

    //接收数据
    //NewsCategoryEditDlg.data.title = $("#title").val();
    //NewsCategoryEditDlg.data.content = NewsCategoryEditDlg.editor.txt.html();

    NewsCategoryEditDlg.data.newsCategoryId = $("#newsCategoryId").val();
    NewsCategoryEditDlg.data.newsCategoryName = $("#newsCategoryName").val();
    NewsCategoryEditDlg.data.seq = $("#seq").val();
    NewsCategoryEditDlg.data.status =  $('input:radio:checked').val();
    //NewsCategoryEditDlg.data.status = $("#status").val();
    NewsCategoryEditDlg.data.language = $("#language").val();


    var data = NewsCategoryEditDlg.data;

    if (!data.newsCategoryName) {
        return "请输入名称";
    }

    if (!data.seq) {
        return "请输入序号";
    }

    return true;
};

/**
 * 提交添加角色
 */
NewsCategoryEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/newsCategory/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.NewsCategory.table.refresh();
        parent.layer.close(window.parent.NewsCategory.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(NewsCategoryEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
NewsCategoryEditDlg.ensure = function () {
    var result = NewsCategoryEditDlg.validateForm();
    if (result === true) {
        NewsCategoryEditDlg.editSubmit();
    } else {
        //Feng.alert(result);
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
NewsCategoryEditDlg.close = function () {
    NewsCategoryEditDlg.close();
};

//初始化
$(function () {

    $("#language").val($("#language1").val());

    if($("#status1").val()==0){
        $("input[type=radio][name=inlineRadioOptions][value='0']").attr("checked",'checked');
    }else{
        $("input[type=radio][name=inlineRadioOptions][value='1']").attr("checked",'checked');
    }
//debugger;


    //初始化编辑器
    // var E = window.wangEditor;
    //var editor = new E('#editor');
    // editor.create();
    // editor.txt.html($("#contentVal").val());
    // NewsCategoryEditDlg.editor = editor;

});
