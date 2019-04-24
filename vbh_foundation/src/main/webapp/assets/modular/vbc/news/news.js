/**
 * 通知管理初始化
 */
var News = {
    id: "NewsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        titleQuery: ""
    }
};

/**
 * 初始化表格的列
 */
News.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'newsId', visible: false, align: 'center', valign: 'middle'},
        {title: '新闻分类', field: 'newsCategoryName', align: 'center', valign: 'middle', sortable: true},
        {title: '新闻标题', field: 'newsTitle', align: 'center', valign: 'middle', sortable: true},
        {
            title: '新闻图片',
            field: 'newsImage',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function (value, row, index) {
                return '<img src="' + value + '" width="80px" height="80px;">';
            }
        },
        {title: '新闻来源', field: 'newsSource', align: 'center', valign: 'middle', sortable: true},
        // {title: '新闻内容', field: 'newsContent', align: 'center', valign: 'middle', sortable: true},
        // {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true},

        {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function (value, row, index) {
                if (value == 0) {
                    return '待发布';
                } else if (value == 1) {
                    return "已发布";
                } else {
                    return "已下架";
                }
            }
        },

        {title: '发布日期', field: 'newUpTime', align: 'center', valign: 'middle', sortable: true},
        {title: '创建人', field: 'createBy', align: 'center', valign: 'middle', sortable: true},
        {title: '创建日期', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '最后修改人', field: 'lastUpdateBy', align: 'center', valign: 'middle', sortable: true},
        {title: '最后修改日期', field: 'lastUpdateTime', align: 'center', valign: 'middle', sortable: true},

        {
            title: '语言',
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

        // {title: '语言', field: 'language', align: 'center', valign: 'middle', sortable: true}

    ];
};

/**
 * 检查是否选中
 */
News.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        News.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
News.openAddNews = function () {
    var index = layer.open({
        type: 2,
        title: '添加新闻',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/news/news_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
News.openNewsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新闻详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/news/news_update/' + News.seItem.newsId
            //content: Feng.ctxPath + '/news/news_update/' + News.seItem.newsId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
News.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/news/delete", function (data) {
                Feng.success("删除成功!");
                News.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("newsId", News.seItem.newsId);
            ajax.start();
        };

        Feng.confirm("确认删除所选信息?" , operation);
    }
};

/**
 * 查询通知列表
 */
News.search = function () {
    var queryData = {};
    queryData['titleQuery'] = $("#titleQuery").val();
    debugger;
    News.table.refresh({query: queryData});
};

/*发布和下架新闻
     */
News.changeNewsStatus = function (op) {
    var id = op;
    console.log(id);
    if (this.check()) {
        var operation = function () {
            var url = Feng.ctxPath + "/api/news/changeStatus?newsId=" + News.seItem.newsId + "&op=" + id;
            $.get(url, function (data) {
                if (data.status == 1) {
                    Feng.success(data.msg);
                    News.search();
                } else {
                    Feng.error(data.msg);
                }
            });
        }

        var tips = "下架"
        if (id == "upStatus") {
            tips = "发布";
        }
        Feng.confirm("是否" + tips + "该条新闻?", operation);
    }

};

$(function () {

    News.app = new Vue({
        el: '#newsPage',
        data: News.condition
    });

    var defaultColunms = News.initColumn();
    var table = new BSTable(News.id, "/news/list", defaultColunms);
    table.setPaginationType("client");
    News.table = table.init();


});
