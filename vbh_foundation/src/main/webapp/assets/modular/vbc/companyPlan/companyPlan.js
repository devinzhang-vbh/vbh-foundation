/**
 * 通知管理初始化
 */
var CompanyPlan = {
    id: "CompanyPlanTable",	//表格id
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
CompanyPlan.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       {title: 'id', field: 'planId', visible: false, align: 'center', valign: 'middle'},
{title: '规划时间', field: 'planDate', align: 'center', valign: 'middle', sortable: true},
{title: '事项1', field: 'planItem1', align: 'center', valign: 'middle', sortable: true},
{title: '事项2', field: 'planItem2', align: 'center', valign: 'middle', sortable: true},
{title: '事项3', field: 'planItem3', align: 'center', valign: 'middle', sortable: true},
{title: '序列', field: 'seq', align: 'center', valign: 'middle', sortable: true},
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
    formatter:function (value,row,index) {
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
CompanyPlan.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        CompanyPlan.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
CompanyPlan.openAddCompanyPlan = function () {
    var index = layer.open({
        type: 2,
        title: '添加规划',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyPlan/companyPlan_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
CompanyPlan.openCompanyPlanDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '规划详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyPlan/companyPlan_update?planId=' + CompanyPlan.seItem.planId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
CompanyPlan.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/companyPlan/delete", function (data) {
                Feng.success("删除成功!");
                CompanyPlan.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("planId", CompanyPlan.seItem.planId);
            ajax.start();
        };

        Feng.confirm("是否删除 " + CompanyPlan.seItem.title + "?", operation);
    }
};

/**
 * 查询通知列表
 */
CompanyPlan.search = function () {
    var queryData = {};
    queryData['condition'] = CompanyPlan.condition.condition;
    CompanyPlan.table.refresh({query: queryData});
};

$(function () {

    CompanyPlan.app = new Vue({
        el: '#companyPlanPage',
        data: CompanyPlan.condition
    });

    var defaultColunms = CompanyPlan.initColumn();
    var table = new BSTable(CompanyPlan.id, "/companyPlan/list", defaultColunms);
    table.setPaginationType("client");
    CompanyPlan.table = table.init();
});
