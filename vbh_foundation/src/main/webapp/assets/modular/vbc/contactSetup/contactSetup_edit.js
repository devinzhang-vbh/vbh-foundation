/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var ContactSetupEditDlg = {
    editor: null,
    data: {
        contactId: $("#contactId").val(),
contactType: $("#contactType").val(),
contactLogo: $("#contactLogo").val(),
contactQrcode: $("#contactQrcode").val(),
contactUrl: $("#contactUrl").val(),
seq: $("#seq").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
ContactSetupEditDlg.close = function () {
    parent.layer.close(window.parent.ContactSetup.layerIndex);
};

/**
 * 验证表单
 */
ContactSetupEditDlg.validateForm = function () {

    //接收数据
    //ContactSetupEditDlg.data.title = $("#title").val();
    //ContactSetupEditDlg.data.content = ContactSetupEditDlg.editor.txt.html();

	ContactSetupEditDlg.data.contactId = $("#contactId").val();
    ContactSetupEditDlg.data.contactType = $("#contactType").val();
    ContactSetupEditDlg.data.contactUrl = $("#contactUrl").val();
    ContactSetupEditDlg.data.seq = $("#seq").val();
    ContactSetupEditDlg.data.language = $("#language").val();
    //图片路径
    var imgSrc = $("#attatch_url").attr("src");
    ContactSetupEditDlg.data.contactLogo = imgSrc;
    var imgSrc1 = $("#attatch_url1").attr("src");
    ContactSetupEditDlg.data.contactQrcode = imgSrc1;

    var data = ContactSetupEditDlg.data;

    if (!data.contactType) {
        return "请输入渠道";
    }

    if (!data.contactLogo) {
        return "请上传图标";
    }
    if (!data.contactQrcode) {
        return "请上传二维码";
    }
    if (!data.contactUrl) {
        return "请输入指向链接";
    }
    if (!data.seq) {
        return "请输入序号";
    }

    return true;
};

/**
 * 提交添加角色
 */
ContactSetupEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/contactSetup/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.ContactSetup.table.refresh();
        parent.layer.close(window.parent.ContactSetup.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(ContactSetupEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
ContactSetupEditDlg.ensure = function () {
    var result = ContactSetupEditDlg.validateForm();
    if (result === true) {
        ContactSetupEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
ContactSetupEditDlg.close = function () {
    ContactSetupEditDlg.close();
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
function upload1(count) {

    var filepath = $("#upload1").val();
    //检查是否为图片
    if (!isImage(filepath)) {
        $("#upload1").val("");
        $("#attatch_url1").attr("src", "");
        $("#attatch_url1").hide();
        return;
    }
    $.ajaxFileUpload({
        url: Feng.ctxPath +"/api/fileUpload/uploadImage1",//需要链接到服务器地址
        secureuri: false,
        fileElementId: "upload1",//文件选择框的id属性  ,//文件选择框的id属性
        dataType: 'json',   //json
        contentType: false,    //不可缺
        processData: false,    //不可缺
        type: 'post',
        success: function (data) {
            $("#attatch_url1").attr("src", data.filePath);
            $("#attatch_url1").show();
            $("#upload1").replaceWith("   <input type=\"file\" name=\"upload1\" id=\"upload1\" title='" + count + "'/>");
            $("#upload1").bind('change', function () {
                count++;
                upload1(count)
            });
        },
        error: function (data, status, e)//服务器响应失败处理函数
        {
            Feng.alert("上传失败:" + data);
            $("#upload1").replaceWith("   <input type=\"file\" name=\"upload1\" id=\"upload1\" title='" + count + "'/>");
            $("#upload1").bind('change', function () {
                count++;
                upload1(count)
            });
        }
    });

};

$(function () {

    $("#language").val($("#language1").val());
    //设置显示图片
    $("#attatch_url").attr("src", $("#contactLogo1").val());
    $("#attatch_url").show();
    $("#attatch_url1").attr("src", $("#contactQrcode1").val());
    $("#attatch_url1").show();
    //文件上传
    var count = -1;
    $("#upload").bind('change', function () {
        count++;
        upload(count)
    })
    //文件上传
    var count1 = -1;
    $("#upload1").bind('change', function () {
        count1++;
        upload1(count)
    })
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // ContactSetupEditDlg.editor = editor;

});
