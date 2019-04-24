/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var NewsCategoryAddDlg = {
    editor: null,
    data: {
        newsCategoryName: "",
        seq: "",
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
NewsCategoryAddDlg.close = function () {
    parent.layer.close(window.parent.NewsCategory.layerIndex);
};

/**
 * 上传图片
 */
function upload_cover(obj) {
    debugger;
    ajax_upload(obj.id, function(data) { //function(data)是上传图片的成功后的回调方法
        var isrc = data.relatPath.replace(/\/\//g, '/'); //获取的图片的绝对路径

        $('#image').attr('src', Feng.ctxPath+isrc).data('img-src', isrc); //给<input>的src赋值去显示图片
    });
}
function ajax_upload(feid, callback) { //具体的上传图片方法
    debugger;
    if (image_check(feid)) { //自己添加的文件后缀名的验证
        $.ajaxFileUpload({
            fileElementId: feid,    //需要上传的文件域的ID，即<input type="file">的ID。
            url: Feng.ctxPath+'/mgr/upload', //后台方法的路径
            type: 'post',   //当要提交自定义参数时，这个参数要设置成post
            dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
            secureuri: false,   //是否启用安全提交，默认为false。
            async : true,   //是否是异步
            success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                if (callback) callback.call(this, data);
            },
            error: function(data, status, e) {  //提交失败自动执行的处理函数。
                console.error(e);
            }
        });
    }
}
function image_check(feid) { //自己添加的文件后缀名的验证
    var img = document.getElementById(feid);
    return /.(jpg|png|gif|bmp)$/.test(img.value)?true:(function() {
        modals.info('图片格式仅支持jpg、png、gif、bmp格式，且区分大小写。');
        return false;
    })();
}

/**
 * 验证表单
 */
NewsCategoryAddDlg.validateForm = function () {

    //接收数据
    //NewsCategoryAddDlg.data.title = $("#title").val();
    // NewsCategoryAddDlg.data.content = NewsCategoryAddDlg.editor.txt.html();
    var name = $("#newsCategoryName").val();
    NewsCategoryAddDlg.data.newsCategoryName= name.trim();
    //NewsCategoryAddDlg.data.newsCategoryName = $("#newsCategoryName").val();

    debugger;
    //NewsCategoryAddDlg.data.newsCategoryName=newsCategoryName;
    NewsCategoryAddDlg.data.seq = $("#seq").val();
    //NewsCategoryAddDlg.data.status = $("#status").val();
    NewsCategoryAddDlg.data.status =  $('input:radio:checked').val();
    NewsCategoryAddDlg.data.language = $("#language").val();


    var data = NewsCategoryAddDlg.data;

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
NewsCategoryAddDlg.addSubmit = function () {
    var ajax = new $ax(Feng.ctxPath + "/newsCategory/add", function (data) {
        parent.Feng.success("添加成功!");
        window.parent.NewsCategory.table.refresh();
        parent.layer.close(window.parent.NewsCategory.layerIndex);
    }, function (data) {
        parent.Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(NewsCategoryAddDlg.data);
    ajax.start();
};

/**
 * 确认按钮
 */
NewsCategoryAddDlg.ensure = function () {
    var result = NewsCategoryAddDlg.validateForm();
    if (result === true) {
        NewsCategoryAddDlg.addSubmit();
    } else {
        parent.Feng.error("添加失败!" + result + "!");
        //Feng.alert(result);
    }
};

/**
 * 取消按钮
 */
NewsCategoryAddDlg.close = function () {
    NewsCategoryAddDlg.close();
};

$(function () {

    //初始化富文本编辑器
    // var E = window.wangEditor;
    //var editor2 = new E('#editor');
    //editor2.create();
    //NewsCategoryAddDlg.editor = editor2;

});
