/**
 * 通知管理初始化
 */
var JobImage = {
    id: "JobImageTable",	//表格id
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
JobImage.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'jobImageId', visible: false, align: 'center', valign: 'middle'},
        {title: '职位名称', field: 'jobName', align: 'center', valign: 'middle', sortable: true},
        {
            title: '职位图示',
            field: 'jobImageUrl',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function (value, row, index) {
                return '<img src="' + value + '" width="80px" height="80px;">';
            }
        },
        {title: '发布时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function (value,row,index) {
                if(value=="1"){
                    return"已启用";
                }else {
                    return "禁用";
                }
            }
        },
        {
            title: '语言版本',
            field: 'language',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter:function (value,row,index) {
                if(value=="CN"){
                    return"中文";
                }else {
                    return "英文";
                }

            }
        }

    ];
};

/**
 * 检查是否选中
 */
JobImage.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        JobImage.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
JobImage.openAddJobImage = function () {
    var index = layer.open({
        type: 2,
        title: '添加通知',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobImage/jobImage_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
JobImage.openJobImageDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '通知详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobImage/jobImage_update/' + JobImage.seItem.jobImageId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
JobImage.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/jobImage/delete", function (data) {
                Feng.success("删除成功!");
                JobImage.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobImageId", JobImage.seItem.jobImageId);
            ajax.start();
        };

        Feng.confirm("是否删除该条信息?", operation);
    }
};
/**
 * 英文版查询
 */
JobImage.englishQuery = function () {
    var queryData = {};
    queryData['condition'] = "US";
    JobImage.table.refresh({query: queryData});
};

/**
 * 查询通知列表
 */
JobImage.search = function () {
    var queryData = {};
    //queryData['condition'] = jobImage.condition.condition;
    queryData['condition'] = "CN";
    JobImage.table.refresh({query: queryData});
};

$(function () {

    JobImage.app = new Vue({
        el: '#jobImagePage',
        data: JobImage.condition
    });

    var defaultColunms = JobImage.initColumn();
    var table = new BSTable(JobImage.id, "/jobImage/list", defaultColunms);
    table.setPaginationType("client");
    JobImage.table = table.init();
});
