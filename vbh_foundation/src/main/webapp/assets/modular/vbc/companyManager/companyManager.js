/**
 * 通知管理初始化
 */
var CompanyManager = {
    id: "CompanyManagerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        language: ""
    }
};

/**
 * 初始化表格的列
 */
CompanyManager.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       {title: 'id', field: 'managerId', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'managerName', align: 'center', valign: 'middle', sortable: true},
        {
            title: '头像',
            field: 'managerImage',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function (value, row, index) {
                return '<img src="' + value + '" width="80px" height="80px;">';
            }
        },
        {title: '职位', field: 'managerJob', align: 'center', valign: 'middle', sortable: true},
        {title: '职位简介', field: 'managerJobDesc', align: 'center', valign: 'middle', sortable: true},
        //{title: '序列', field: 'seq', align: 'center', valign: 'middle', sortable: true},
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
            formatter:function(value, row, index){
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
CompanyManager.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        CompanyManager.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
CompanyManager.openAddCompanyManager = function () {
    var index = layer.open({
        type: 2,
        title: '添加管理团队',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyManager/companyManager_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
CompanyManager.openCompanyManagerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '管理团队详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyManager/companyManager_update/' + CompanyManager.seItem.managerId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
CompanyManager.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/companyManager/delete", function (data) {
                Feng.success("删除成功!");
                CompanyManager.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("managerId", CompanyManager.seItem.managerId);
            ajax.start();
        };

        Feng.confirm("是否删除该条信息?", operation);
    }
};

/**
 * 默认查询中文版列表
 */
CompanyManager.search = function () {
    var queryData = {};
    //queryData['condition'] = CompanyManager.condition.condition;
    var language="CN";
    queryData['language'] = language;
    CompanyManager.table.refresh({query: queryData});
};
//查询英文版列表
CompanyManager.searchEnglish = function () {
    var queryData = {};
    //queryData['condition'] = CompanyManager.condition.condition;
    var language="US";
    queryData['language'] = language;
    CompanyManager.table.refresh({query: queryData});
};

$(function () {

    CompanyManager.app = new Vue({
        el: '#noticePage',
        data: CompanyManager.condition
    });

    var defaultColunms = CompanyManager.initColumn();
    var table = new BSTable(CompanyManager.id, "/companyManager/list", defaultColunms);
    table.setPaginationType("client");
    CompanyManager.table = table.init();
});
