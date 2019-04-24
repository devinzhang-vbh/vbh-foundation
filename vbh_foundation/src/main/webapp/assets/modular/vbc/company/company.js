/**
 * 通知管理初始化
 */
var Company = {
    id: "CompanyTable",	//表格id
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
Company.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
       {title: 'id', field: 'companyId', visible: false, align: 'center', valign: 'middle'},
{title: '公司名称', field: 'companyName', align: 'center', valign: 'middle', sortable: true},
{title: '公司电话', field: 'companyPhone', align: 'center', valign: 'middle', sortable: true},
{title: '公司传真', field: 'companyFax', align: 'center', valign: 'middle', sortable: true},
{title: '公司邮箱', field: 'companyEmail', align: 'center', valign: 'middle', sortable: true},
{title: '公司地址', field: 'companyAddress', align: 'center', valign: 'middle', sortable: true},
{title: '公司备案号', field: 'companyRecordNo', align: 'center', valign: 'middle', sortable: true},
/*{title: '公司简介', field: 'companyDesc', align: 'center', valign: 'middle', sortable: true},
{title: '公司文化简介', field: 'companyCultureDesc', align: 'center', valign: 'middle', sortable: true},
{title: '公司荣誉简介', field: 'companyRewardDesc', align: 'center', valign: 'middle', sortable: true},
{title: '创建人', field: 'creatBy', align: 'center', valign: 'middle', sortable: true},
{title: '创建日期', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
{title: '最后修改人', field: 'lastUpdateBy', align: 'center', valign: 'middle', sortable: true},
{title: '最后修改日期', field: 'lastUpdateTime', align: 'center', valign: 'middle', sortable: true},*/
{
    title: '语种',
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
Company.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Company.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
Company.openAddCompany = function () {
    var index = layer.open({
        type: 2,
        title: '添加公司',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/company/company_add'
    });
    this.layerIndex = index;
};
/**
 * 点击添加公司详情
 */
Company.openAddCompanyDetail = function () {
    if(this.check()){
        var index = layer.open({
            type: 2,
            title: '添加公司简介',
            area: ['800px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/company/company_update_detail/'+Company.seItem.companyId
        });
    this.layerIndex = index;
    }
};

/**
 * 打开查看通知详情
 */
Company.openCompanyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '公司详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/company/company_update/' + Company.seItem.companyId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
Company.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/company/delete", function (data) {
                Feng.success("删除成功!");
                Company.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("companyId", Company.seItem.companyId);
            ajax.start();
        };

        Feng.confirm("是否删除 " + Company.seItem.title + "?", operation);
    }
};

/**
 * 查询通知列表
 */
Company.search = function () {
    var queryData = {};
    queryData['condition'] = Company.condition.condition;
    Company.table.refresh({query: queryData});
};

$(function () {

    Company.app = new Vue({
        el: '#companyPage',
        data: Company.condition
    });

    var defaultColunms = Company.initColumn();
    var table = new BSTable(Company.id, "/company/list", defaultColunms);
    table.setPaginationType("client");
    Company.table = table.init();
});
