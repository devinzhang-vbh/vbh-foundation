/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var JobImageAddDlg = {
    editor: null,
    data: {
         jobImageUrl: "",
 jobImageJobId: "",
 status: "",
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
JobImageAddDlg.close = function () {
    parent.layer.close(window.parent.JobImage.layerIndex);
};

/**
 * 验证表单
 */
JobImageAddDlg.validateForm = function () {

    //接收数据
    //JobImageAddDlg.data.title = $("#title").val();
   // JobImageAddDlg.data.content = JobImageAddDlg.editor.txt.html();
    //JobImageAddDlg.data.jobImageUrl = $("#jobImageUrl").val();
JobImageAddDlg.data.jobImageJobId = $("#jobImageJobId").val();
JobImageAddDlg.data.status = $('input:radio:checked').val();
JobImageAddDlg.data.language = $("#language").val();
//图片路径
    var imgSrc = $("#attatch_url").attr("src");
    JobImageAddDlg.data.jobImageUrl = imgSrc;
debugger

    var data = JobImageAddDlg.data;

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
JobImageAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/jobImage/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.JobImage.table.refresh();
        parent.layer.close(window.parent.JobImage.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobImageAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobImageAddDlg.ensure = function () {
    var result = JobImageAddDlg.validateForm();
    if (result === true) {
        JobImageAddDlg.addSubmit();
    } else {
        //Feng.alert(result);
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
JobImageAddDlg.close = function () {
    JobImageAddDlg.close();
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
//初始化jobimage的select数据
    var ajax = new $ax(Feng.ctxPath + "/job/listAll/" );
    var result = ajax.start();
    $.each(result, function(index, item) {
        $("#jobImageJobId").append(  //此处向select中循环绑定数据
            "<option value=" + item.jobId + ">" + item.jobName + "</option>");
    });

    //文件上传
    var count = -1;
    $("#upload").bind('change', function () {
        count++;
        upload(count)
    })
    //初始化富文本编辑器
   // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //JobImageAddDlg.editor = editor2;

});
