/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var CompanyManagerAddDlg = {
    editor: null,
    data: {
         managerName: "",
        managerEnglishName: "",
 managerImage: "",
 managerJob: "",
 managerJobDesc: "",
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
CompanyManagerAddDlg.close = function () {
    parent.layer.close(window.parent.CompanyManager.layerIndex);
};

/**
 * 验证表单
 */
CompanyManagerAddDlg.validateForm = function () {

    //接收数据
    //CompanyManagerAddDlg.data.title = $("#title").val();
   // CompanyManagerAddDlg.data.content = CompanyManagerAddDlg.editor.txt.html();
    CompanyManagerAddDlg.data.managerName = $("#managerName").val();
    CompanyManagerAddDlg.data.managerEnglishName = $("#managerEnglishName").val();
    //var data = CompanyManagerAddDlg.data;
    //debugger;
    /*if(!data.managerName){
        //layer.msg('hello');
        return "姓名为必填项";
    }*/
    //图片路径
    var imgSrc = $("#attatch_url").attr("src");

    CompanyManagerAddDlg.data.managerImage = imgSrc;
    CompanyManagerAddDlg.data.managerJob = $("#managerJob").val();
    CompanyManagerAddDlg.data.managerJobDesc = $("#managerJobDesc").val();
    CompanyManagerAddDlg.data.seq = $("#seq").val();
    CompanyManagerAddDlg.data.language = $("#language").val();


    var data = CompanyManagerAddDlg.data;

    if (!data.managerName) {
        return "请输入名称";
    }
    if (!data.managerEnglishName) {
        return "请输入英文名称";
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
CompanyManagerAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/companyManager/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.CompanyManager.table.refresh();
        parent.layer.close(window.parent.CompanyManager.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(CompanyManagerAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
CompanyManagerAddDlg.ensure = function () {
    var result = CompanyManagerAddDlg.validateForm();
    if (result === true) {
        CompanyManagerAddDlg.addSubmit();
    } else {
        window.parent.Feng.error("添加失败!" + result + "!");
    }
};

/**
 * 取消按钮
 */
CompanyManagerAddDlg.close = function () {
    CompanyManagerAddDlg.close();
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
    //CompanyManagerAddDlg.editor = editor2;

});
