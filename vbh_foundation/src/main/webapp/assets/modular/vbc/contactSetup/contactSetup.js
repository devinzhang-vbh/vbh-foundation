/**
 * 通知管理初始化
 */
var ContactSetup = {
    id: "ContactSetupTable",	//表格id
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
ContactSetup.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       {title: 'id', field: 'contactId', visible: false, align: 'center', valign: 'middle'},
{title: '官方渠道', field: 'contactType', align: 'center', valign: 'middle', sortable: true},
{
    title: '图标',
    field: 'contactLogo',
    align: 'center',
    valign: 'middle',
    sortable: true,
    formatter:function (value,row,index) {
        return '<img src="' + value + '" width="80px" height="80px;">';
    }

},
{
    title: '二维码',
    field: 'contactQrcode',
    align: 'center',
    valign: 'middle',
    sortable: true,
    formatter:function (value,row,index) {
        return '<img src="' + value + '" width="80px" height="80px;">';
    }

},
{title: '链接地址', field: 'contactUrl', align: 'center', valign: 'middle', sortable: true},
//{title: '排序', field: 'seq', align: 'center', valign: 'middle', sortable: true},
//{title: '创建人', field: 'createBy', align: 'center', valign: 'middle', sortable: true},
{title: '发布时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
//{title: '最后修改人', field: 'lastUpdateBy', align: 'center', valign: 'middle', sortable: true},
//{title: '最后修改日期', field: 'lastUpdateTime', align: 'center', valign: 'middle', sortable: true},
{
    title: '语种',
    field: 'language',
    align: 'center',
    valign: 'middle',
    sortable: true,
    formatter:function (value, row, index) {
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
ContactSetup.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ContactSetup.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
ContactSetup.openAddContactSetup = function () {
    var index = layer.open({
        type: 2,
        title: '添加联系方式',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/contactSetup/contactSetup_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
ContactSetup.openContactSetupDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '联系方式详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/contactSetup/contactSetup_update/' + ContactSetup.seItem.contactId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
ContactSetup.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/contactSetup/delete", function (data) {
                Feng.success("删除成功!");
                ContactSetup.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("contactId", ContactSetup.seItem.contactId);
            ajax.start();
        };

        Feng.confirm("是否删除 " + ContactSetup.seItem.title + "?", operation);
    }
};

/**
 * 查询通知列表
 */
ContactSetup.search = function () {
    var queryData = {};
    queryData['condition'] = ContactSetup.condition.condition;
    ContactSetup.table.refresh({query: queryData});
};

$(function () {

    ContactSetup.app = new Vue({
        el: '#contactSetupPage',
        data: ContactSetup.condition
    });

    var defaultColunms = ContactSetup.initColumn();
    var table = new BSTable(ContactSetup.id, "/contactSetup/list", defaultColunms);
    table.setPaginationType("client");
    ContactSetup.table = table.init();
});
