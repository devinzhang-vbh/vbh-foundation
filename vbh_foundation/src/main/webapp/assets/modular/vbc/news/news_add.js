/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var NewsAddDlg = {
    editor: null,
    data: {
        newsCategory: "",
        newsTitle: "",
        newsLink: "",
        newsImage: "",
        newsSource: "",
        newUpTime: "",
        newsContent: "",
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
NewsAddDlg.close = function () {
    parent.layer.close(window.parent.News.layerIndex);
};

/**
 * 验证表单
 */
NewsAddDlg.validateForm = function () {

    //接收数据
    //NewsAddDlg.data.title = $("#title").val();
    // NewsAddDlg.data.content = NewsAddDlg.editor.txt.html();
    NewsAddDlg.data.newsCategory = $("#newsCategory").val();
    NewsAddDlg.data.newsTitle = $("#newsTitle").val();
    NewsAddDlg.data.newsLink = $("#newsLink").val();

    //图片路径
    var imgSrc = $("#attatch_url").attr("src");
    NewsAddDlg.data.newsImage = imgSrc;

    NewsAddDlg.data.newsSource = $("#newsSource").val();
    //富文本
    var editor_data = CKEDITOR.instances.newsContent.getData();
    editor_data = encodeURIComponent(editor_data);
    NewsAddDlg.data.newsContent = editor_data;

    NewsAddDlg.data.status = $("#status").val();
    NewsAddDlg.data.language = $("#language").val();
    NewsAddDlg.data.newUpTime = $("#newUpTime").val();

    var data = NewsAddDlg.data;

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
NewsAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/news/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.News.table.refresh();
        parent.layer.close(window.parent.News.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(NewsAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
NewsAddDlg.ensure = function () {
    var result = NewsAddDlg.validateForm();
    if (result === true) {
        NewsAddDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
        //Feng.alert(result);
    }
};

/**
 * 取消按钮
 */
NewsAddDlg.close = function () {
    NewsAddDlg.close();
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


    $("#newUpTime").val("");    //执行一个laydate实例
    laydate.render({
        elem: '#newUpTime' //指定元素
        ,type: 'datetime'
    });
    //初始化富文本编辑器

    CKEDITOR.replace('newsContent',
        {
            filebrowserBrowseUrl: Feng.ctxPath + '/api/fileUpload/showImage?Type=Image',
            filebrowserUploadUrl: Feng.ctxPath + '/api/fileUpload/upload?Type=Image'
        });

    //文件上传
    var count = -1;
    $("#upload").bind('change', function () {
        count++;
        upload(count)
    })


    //初始化下拉框
    $.get(Feng.ctxPath + '/api/newsCategory/list',function(data){
        var opHtml ="";
       $.each(data,function(){
           var newsCategoryId =  $(this).attr("newsCategoryId");
           var newsCategoryName =  $(this).attr("newsCategoryName");
           opHtml += "<option value='"+newsCategoryId+"'>"+newsCategoryName+"</option>";
       });
        $("#newsCategory").html(opHtml);
    });
});

