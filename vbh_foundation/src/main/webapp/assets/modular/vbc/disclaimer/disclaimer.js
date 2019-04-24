/**
 * 通知管理初始化
 */
var Disclaimer = {
    id: "DisclaimerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        condition: ""
    }
};

/**
 * 初始化表格的列
 */
Disclaimer.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       {title: 'id', field: 'disclaimerId',  align: 'center', valign: 'middle'},
       {
           title: '免责声明内容',
           field: 'disclaimerContent',
           align: 'center',
           valign: 'middle',
           formatter:function (value, row, index) {
               debugger;
               if(value.length>=10){
                   var val=value.substring(0,11);
                   return val+"......";
               }
               return value;

           }
       },
/*{title: '创建人', field: 'createBy', align: 'center', valign: 'middle', sortable: true},*/
{title: '发布时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
{
    title: '语言版本',
    field: 'language',
    align: 'center',
    valign: 'middle',
    sortable: true,
    formatter: function (value, row, index) {
        if (value == "CN") {
            return '中文';
        } else {
            return "英文";
        }
    }
}

    ];
};

/**
 * 检查是否选中
 */
Disclaimer.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Disclaimer.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
Disclaimer.openAddDisclaimer = function () {
    var index = layer.open({
        type: 2,
        title: '添加通知',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/disclaimer/disclaimer_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
Disclaimer.openDisclaimerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '通知详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/disclaimer/disclaimer_update/' + Disclaimer.seItem.disclaimerId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
Disclaimer.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/disclaimer/delete", function (data) {
                Feng.success("删除成功!");
                Disclaimer.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("disclaimerId", Disclaimer.seItem.disclaimerId);
            ajax.start();
        };

        Feng.confirm("是否删除该条信息 ?", operation);
    }
};

/**
 * 查询通知列表
 */
Disclaimer.search = function () {
    var queryData = {};
    queryData['condition'] = Disclaimer.condition.condition;
    Disclaimer.table.refresh({query: queryData});
};

$(function () {

    Disclaimer.app = new Vue({
        el: '#disclaimerPage',
        data: Disclaimer.condition
    });

    var defaultColunms = Disclaimer.initColumn();
    var table = new BSTable(Disclaimer.id, "/disclaimer/list", defaultColunms);
    table.setPaginationType("client");
    Disclaimer.table = table.init();
});
