/**
 * 通知管理初始化
 */
var NewsCategory = {
    id: "NewsCategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        newsCategory: ""
    }
};

/**
 * 初始化表格的列
 */
NewsCategory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'newsCategoryId', visible: false, align: 'center', valign: 'middle'},
        {title: '新闻分类名称', field: 'newsCategoryName', align: 'center', valign: 'middle', sortable: true},
        {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '禁用';
                } else {
                    return "启用";
                }
            }
        },

    ];
};

/**
 * 检查是否选中
 */
NewsCategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        NewsCategory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
NewsCategory.openAddNewsCategory = function () {
    var index = layer.open({
        type: 2,
        title: '添加分类',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/newsCategory/newsCategory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
NewsCategory.openNewsCategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '分类详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/newsCategory/newsCategory_update/' + NewsCategory.seItem.newsCategoryId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
NewsCategory.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/newsCategory/delete", function (data) {
                Feng.success("删除成功!");
                NewsCategory.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("newsCategoryId", NewsCategory.seItem.newsCategoryId);
            ajax.start();
        };

        Feng.confirm("确认删除所选信息? ", operation);
    }
};

/**
 * 查询通知列表
 */
NewsCategory.search = function () {
    var queryData = {};
    var xx=$("#newsCategory").val();
    queryData['newsCategory'] = xx;
    debugger
    NewsCategory.table.refresh({query: queryData});
};

$(function () {

    NewsCategory.app = new Vue({
        el: '#newsCategoryPage',
        data: NewsCategory.condition
    });

    var defaultColunms = NewsCategory.initColumn();

    var table = new BSTable(NewsCategory.id, "/newsCategory/list", defaultColunms);
    debugger
    table.setPaginationType("client");
    NewsCategory.table = table.init();
});
