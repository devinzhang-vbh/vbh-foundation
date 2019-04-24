/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyManagerEditDlg = {
    editor: null,
    data: {
        managerId: $("#managerId").val(),
managerName: $("#managerName").val(),
managerEnglishName: $("#managerEnglishName").val(),
managerImage: $("#managerImage").val(),
managerJob: $("#managerJob").val(),
managerJobDesc: $("#managerJobDesc").val(),
seq: $("#seq").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
CompanyManagerEditDlg.close = function () {
    parent.layer.close(window.parent.CompanyManager.layerIndex);
};

/**
 * 验证表单
 */
CompanyManagerEditDlg.validateForm = function () {

    //接收数据
    //CompanyManagerEditDlg.data.title = $("#title").val();
    //CompanyManagerEditDlg.data.content = CompanyManagerEditDlg.editor.txt.html();

	CompanyManagerEditDlg.data.managerId = $("#managerId").val();
CompanyManagerEditDlg.data.managerName = $("#managerName").val();
CompanyManagerEditDlg.data.managerEnglishName = $("#managerEnglishName").val();
CompanyManagerEditDlg.data.managerJob = $("#managerJob").val();
CompanyManagerEditDlg.data.managerJobDesc = $("#managerJobDesc").val();
CompanyManagerEditDlg.data.seq = $("#seq").val();
CompanyManagerEditDlg.data.language = $("#language").val();
    //图片路径
    var imgSrc = $("#attatch_url").attr("src");

    CompanyManagerEditDlg.data.managerImage = imgSrc;

    var data = CompanyManagerEditDlg.data;

    if (!data.managerName) {
        return "请输入名称";
    }
    if (!data.managerEnglishName) {
        return "请输入英文名";
    }

    if (!data.managerJob) {
        return "请输入职位";
    }
    if (!data.managerJobDesc) {
        return "请输入职位描述";
    }
    if (!data.seq) {
        return "请输入序号";
    }
    if (!data.managerImage) {
        return "请上传图片";
    }


    return true;
};

/**
 * 提交添加角色
 */
CompanyManagerEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/companyManager/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.CompanyManager.table.refresh();
        parent.layer.close(window.parent.CompanyManager.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyManagerEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyManagerEditDlg.ensure = function () {
    var result = CompanyManagerEditDlg.validateForm();
    if (result === true) {
        CompanyManagerEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyManagerEditDlg.close = function () {
    CompanyManagerEditDlg.close();
};

//判断
function isImage(filepath) {
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        return false;
    }
    return true;
}
//图片上传
function upload(count) {

    var filepath = $("#upload").val();
    //检查是否为图片
    if (!isImage(filepath)) {
        $("#upload").val("");
        $("#attatch_url").attr("src", "");
        $("#attatch_url").hide();
        return;
    }
    $.ajaxFileUpload({
        url: Feng.ctxPath +"/api/fileUpload/uploadImage",//需要链接到服务器地址
        secureuri: false,
        fileElementId: "upload",//文件选择框的id属性  ,//文件选择框的id属性
        dataType: 'json',   //json
        contentType: false,    //不可缺
        processData: false,    //不可缺
        type: 'post',
        success: function (data) {
            $("#attatch_url").attr("src", data.filePath);
            $("#attatch_url").show();
            debugger;
            $("#upload").replaceWith("   <input type=\"file\" name=\"upload\" id=\"upload\" title='" + count + "'/>");
            $("#upload").bind('change', function () {
                count++;
                upload(count)
            });
        },
        error: function (data, status, e)//服务器响应失败处理函数
        {
            Feng.alert("上传失败:" + data);
            $("#upload").replaceWith("   <input type=\"file\" name=\"upload\" id=\"upload\" title='" + count + "'/>");
            $("#upload").bind('change', function () {
                count++;
                upload(count)
            });
        }
    });

};

$(function () {

    //设置显示图片
    $("#attatch_url").attr("src", $("#managerImage1").val());
    $("#attatch_url").show();
    $("#language").val($("#language1").val());
    //文件上传
    var count = -1;
    $("#upload").bind('change', function () {
        count++;
        upload(count)
    })
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // CompanyManagerEditDlg.editor = editor;

});
