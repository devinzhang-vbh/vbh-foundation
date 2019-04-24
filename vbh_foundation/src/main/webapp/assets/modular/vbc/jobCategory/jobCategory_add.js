/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var JobCategoryAddDlg = {
    editor: null,
    data: {
         JobCategoryName: "",
 jobCategoryImage: "",
 status: "",
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
JobCategoryAddDlg.close = function () {
    parent.layer.close(window.parent.JobCategory.layerIndex);
};

/**
 * 验证表单
 */
JobCategoryAddDlg.validateForm = function () {

    //接收数据
    //JobCategoryAddDlg.data.title = $("#title").val();
   // JobCategoryAddDlg.data.content = JobCategoryAddDlg.editor.txt.html();
    JobCategoryAddDlg.data.JobCategoryName = $("#JobCategoryName").val();

    //图片路径
    //var imgSrc = $("#attatch_url").attr("src");
    //JobCategoryAddDlg.data.jobCategoryImage = imgSrc;
    //JobCategoryAddDlg.data.jobCategoryImage = $("#jobCategoryImage").val();

    //JobCategoryAddDlg.data.status = $("#status").val();
    JobCategoryAddDlg.data.status =  $('input:radio:checked').val();
    JobCategoryAddDlg.data.seq = $("#seq").val();
    JobCategoryAddDlg.data.language =$("#language").val();
    /*if($("#language").val()==1){
        JobCategoryAddDlg.data.language ="CN" ;
    }else {
        JobCategoryAddDlg.data.language ="US";
    }*/
        //var OO=JobCategoryAddDlg.data.language
debugger
    var data = JobCategoryAddDlg.data;

    if (!data.JobCategoryName) {
        return "请输入名称";
    }

    if (!data.seq) {
        return "请输入排序";
    }

    return true;
};

/**
 * 提交添加角色
 */
JobCategoryAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/jobCategory/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.JobCategory.table.refresh();
        parent.layer.close(window.parent.JobCategory.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobCategoryAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobCategoryAddDlg.ensure = function () {
    var result = JobCategoryAddDlg.validateForm();
    if (result === true) {
        JobCategoryAddDlg.addSubmit();
    } else {
        //Feng.alert(result);
        parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
JobCategoryAddDlg.close = function () {
    JobCategoryAddDlg.close();
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

$(function () {

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
    //JobCategoryAddDlg.editor = editor2;

});
