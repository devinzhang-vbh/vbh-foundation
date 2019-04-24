/**
 * 角色详情对话框（可用于添加和修改对话框）  $("#status").val(),
 */
var JobCategoryEditDlg = {
    editor: null,
    data: {
        jobCategoryId: $("#jobCategoryId").val(),
JobCategoryName: $("#JobCategoryName").val(),
jobCategoryImage: $("#jobCategoryImage").val(),
status: $('input:radio:checked').val(),
seq: $("#seq").val(),
language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
JobCategoryEditDlg.close = function () {

    parent.layer.close(window.parent.JobCategory.layerIndex);
};

/**
 * 验证表单
 */
JobCategoryEditDlg.validateForm = function () {

    //接收数据
    //JobCategoryEditDlg.data.title = $("#title").val();
    //JobCategoryEditDlg.data.content = JobCategoryEditDlg.editor.txt.html();

	JobCategoryEditDlg.data.jobCategoryId = $("#jobCategoryId").val();
    JobCategoryEditDlg.data.JobCategoryName = $("#JobCategoryName").val();
    //JobCategoryEditDlg.data.jobCategoryImage = $("#jobCategoryImage").val();
    //JobCategoryEditDlg.data.jobCategoryImage = $("#jobCategoryImage").val();
    //var imgSrc = $("#attatch_url").attr("src");
    //JobCategoryEditDlg.data.jobCategoryImage = imgSrc;
    JobCategoryEditDlg.data.status =  $('input:radio:checked').val();
    JobCategoryEditDlg.data.seq = $("#seq").val();
    JobCategoryEditDlg.data.language =$("#language").val();

    var data = JobCategoryEditDlg.data;

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
JobCategoryEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/jobCategory/update", function (data) {
        //debugger;
        parent.Feng.success("修改成功!");
        window.parent.JobCategory.table.refresh();
        parent.layer.close(window.parent.JobCategory.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(JobCategoryEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
JobCategoryEditDlg.ensure = function () {
    var result = JobCategoryEditDlg.validateForm();
    if (result === true) {
        JobCategoryEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
        // Feng.alert(result);
    }
};

/**
 * 取消按钮
 */
JobCategoryEditDlg.close = function () {
    JobCategoryEditDlg.close();
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
    //$("#attatch_url").attr("src", $("#jobCategoryImage").val());
    //$("#attatch_url").show();

    //文件上传
    //var count = -1;
    /*$("#upload").bind('change', function () {
        count++;
        upload(count)
    });*/
    //查询数据库中catagory的redio
    /*var jobCategoryId= $("#jobCategoryId").val();
    var ajax = new $ax(Feng.ctxPath + "/jobCategory/query/"+jobCategoryId );
    var result = ajax.start();
    var ooo=$("#inlineRadio1").val();
    var ppp=$("#inlineRadio2").val();*/
    var status=$("#status").val();
    if(status==1){
        //$('input:radio:first').attr('checked', 'checked');
        //$("#status").attr('checked', 'checked');
        $('input:radio:first').attr('checked', 'checked');
    }else{
        //$("#inlineRadio2").attr('checked', 'checked');
        $('input:radio:last').attr('checked', 'checked');
    }
//显示语言
    //var re=result.language;
    $("#language").val($("#language1").val());
debugger;
    //初始化编辑器
   // var E = window.wangEditor;
    //var editor = new E('#editor');
   // editor.create();
   // editor.txt.html($("#contentVal").val());
   // JobCategoryEditDlg.editor = editor;

});
