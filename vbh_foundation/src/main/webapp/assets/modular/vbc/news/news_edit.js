/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var NewsEditDlg = {
    editor: null,
    data: {
        newsId: $("#newsId").val(),
        newsCategory: $("#newsCategory").val(),
        newsTitle: $("#newsTitle").val(),
        newsLink: $("#newsLink").val(),
        newsImage: $("#newsImage").val(),
        newsSource: $("#newsSource").val(),
        newUpTime: $("#newUpTime").val(),
        newsContent: $("#newsContent").val(),
        status: $("#status").val(),
        language: $("#language").val()

    }
};

/**
 * 关闭此对话框
 */
NewsEditDlg.close = function () {
    parent.layer.close(window.parent.News.layerIndex);
};

/**
 * 验证表单
 */
NewsEditDlg.validateForm = function () {

    //接收数据
    //NewsEditDlg.data.title = $("#title").val();
    //NewsEditDlg.data.content = NewsEditDlg.editor.txt.html();

    NewsEditDlg.data.newsId = $("#newsId").val();
    NewsEditDlg.data.newsCategory = $("#newsCategory").val();
    NewsEditDlg.data.newsTitle = $("#newsTitle").val();
    NewsEditDlg.data.newsLink = $("#newsLink").val();


    NewsEditDlg.data.newsImage = $("#newsImage").val();

    var imgSrc = $("#attatch_url").attr("src");
    NewsEditDlg.data.newsImage = imgSrc;


    NewsEditDlg.data.newsSource = $("#newsSource").val();
    NewsEditDlg.data.newUpTime = $("#newUpTime").val();
    // NewsEditDlg.data.newsContent =$("#newsContent").val();
    var editor_data = CKEDITOR.instances.newsContent.getData();
    editor_data =  encodeURIComponent(editor_data);
    NewsEditDlg.data.newsContent = editor_data;

    NewsEditDlg.data.status = $("#status").val();
    NewsEditDlg.data.language = $("#language").val();


    var data = NewsEditDlg.data;

    if (!data.newsCategory) {
        return "请输入新闻类别";
    }

    if (!data.newsTitle) {
        return "请输入标题";
    }
    if (!data.newsContent) {
        return "请输入文章内容";
    }
    if (!data.newsImage) {
        return "请上传图片";
    }
    if (!data.newsSource) {
        return "请输入来源";
    }
    if (!data.newUpTime) {
        return "请输入上架时间";
    }

    return true;
};

/**
 * 提交添加角色
 */
NewsEditDlg.editSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/news/update", function (data) {
        parent.Feng.success("修改成功!");
        window.parent.News.table.refresh();
        parent.layer.close(window.parent.News.layerIndex);
    }, function (data) {
        parent.Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(NewsEditDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
NewsEditDlg.ensure = function () {
    var result = NewsEditDlg.validateForm();
    if (result === true) {
        NewsEditDlg.editSubmit();
    } else {
        parent.Feng.error("修改失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
NewsEditDlg.close = function () {
    NewsEditDlg.close();
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
    //var xx=$("#newUpTime1").val();
   //var datt= $("#newUpTime").val();
   var da= $("#newUpTime2").val();

//执行一个laydate实例
    laydate.render({
        elem: '#newUpTime' //指定元素
        ,type: 'datetime'
        ,value:da
    });


    //初始化富文本编辑器

    var CKEditor = CKEDITOR.replace('newsContent',
        {
            filebrowserBrowseUrl: Feng.ctxPath + '/api/fileUpload/showImage?Type=Image',
            filebrowserUploadUrl: Feng.ctxPath + '/api/fileUpload/upload?Type=Image'
        });
    var data = $("#newsContent1").val();
    debugger
    CKEDITOR.instances.newsContent.setData(data);

    //设置显示图片
    $("#attatch_url").attr("src", $("#newsImage").val());
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


    //初始化下拉框
    $.get(Feng.ctxPath + '/api/newsCategory/list',function(data){
        var opHtml ="";
        $.each(data,function(){
            var newsCategoryId =  $(this).attr("newsCategoryId");
            var newsCategoryName =  $(this).attr("newsCategoryName");
            opHtml += "<option value='"+newsCategoryId+"'>"+newsCategoryName+"</option>";
        });
        $("#newsCategory").html(opHtml);
        $("#newsCategory").val($("#newsCategory1").val());
    });


});



