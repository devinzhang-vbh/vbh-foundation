/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var JobImageEditDlg = {
    editor: null,
    data: {
        jobImageId: $("#jobImageId").val(),
        jobImageUrl: $("#jobImageUrl").val(),
        jobImageJobId: $("#jobImageJobId").val(),
        status: $("#status").val(),
        language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
JobImageEditDlg.close = function () {
    parent.layer.close(window.parent.JobImage.layerIndex);
};

/**
 * 验证表单
 */
JobImageEditDlg.validateForm = function () {

    //接收数据
    //JobImageEditDlg.data.title = $("#title").val();
    //JobImageEditDlg.data.content = JobImageEditDlg.editor.txt.html();

    JobImageEditDlg.data.jobImageId = $("#jobImageId").val();
//JobImageEditDlg.data.jobImageUrl = $("#jobImageUrl").val();
//图片路径
    var imgSrc = $("#attatch_url").attr("src");
    JobImageEditDlg.data.jobImageUrl = imgSrc;
    JobImageEditDlg.data.jobImageJobId = $("#jobImageJobId").val();
    JobImageEditDlg.data.status = $('input:radio:checked').val();
    JobImageEditDlg.data.language = $("#language").val();

debugger
    var data = JobImageEditDlg.data;

    if (!data.jobImageJobId) {
        return "请选择职位名称";
    }

    if (!data.jobImageUrl) {
        return "请上传图片";
    }

    return true;
};

/**
 * 提交添加角色
 */
JobImageEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/jobImage/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.JobImage.table.refresh();
        parent.layer.close(window.parent.JobImage.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobImageEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobImageEditDlg.ensure = function () {
    var result = JobImageEditDlg.validateForm();
    if (result === true) {
        JobImageEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
JobImageEditDlg.close = function () {
    JobImageEditDlg.close();
};

function isImage(filepath) {
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
        return false;
    }
    return true;
}


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
            debugger;
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

$(function () {
    //设置显示图片
    $("#attatch_url").attr("src", $("#jobImageUrl").val());
    $("#attatch_url").show();

    /**
     * 下拉框
     */
    $("#language").val($("#language1").val());

    //文件上传
    var count = -1;
    $("#upload").bind('change', function () {
        count++;
        upload(count)
    });
    var ajax = new $ax(Feng.ctxPath + "/job/listAll/");
    var result = ajax.start();
    $.each(result, function (index, item) {
        $("#jobImageJobId").append(  //此处向select中循环绑定数据
            "<option value=" + item.jobId + ">" + item.jobName + "</option>");
    });
    $("#jobImageJobId").val($("#jobImageJobId1").val());
    //redio初始化
    var status = $("#status1").val();
    if (status == 1) {
        //$('input:radio:first').attr('checked', 'checked');
        //$("#status").attr('checked', 'checked');
        $('input:radio:first').attr('checked', 'checked');
    } else {
        //$("#inlineRadio2").attr('checked', 'checked');
        $('input:radio:last').attr('checked', 'checked');
    }
    //初始化编辑器
    // var E = window.wangEditor;
    //var editor = new E('#editor');
    // editor.create();
    // editor.txt.html($("#contentVal").val());
    // JobImageEditDlg.editor = editor;

});
