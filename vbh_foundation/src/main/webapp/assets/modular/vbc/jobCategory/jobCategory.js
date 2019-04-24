/**
 * 通知管理初始化
 */
var JobCategory = {
    id: "JobCategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        jobtype: ""
    }
};

/**
 * 初始化表格的列
 */
JobCategory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'jobCategoryId', visible: false, align: 'center', valign: 'middle'},
        {title: '职位分类', field: 'JobCategoryName', align: 'center', valign: 'middle', sortable: true},
        {title: '发布时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true}, {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true
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
JobCategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        JobCategory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
JobCategory.openAddJobCategory = function () {
    var index = layer.open({
        type: 2,
        title: '添加职位类别',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/jobCategory/jobCategory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
JobCategory.openJobCategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '职位类别详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/jobCategory/jobCategory_update/' + JobCategory.seItem.jobCategoryId
            ///' + JobCategory.seItem.jobCategoryId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
JobCategory.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/jobCategory/delete", function (data) {
                Feng.success("删除成功!");
                JobCategory.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobCategoryId", JobCategory.seItem.jobCategoryId);
            ajax.start();
        };

        Feng.confirm("是否删除该条信息?", operation);
    }
};

/**
 * 查询英文版
 */
JobCategory.englishQuery = function () {
    var queryData = {};
    queryData['jobtype'] = "US";
    JobCategory.table.refresh({query: queryData});
};
/**
 * 查询通知列表
 */
JobCategory.search = function () {
    var queryData = {};
    //queryData['jobtype'] = $("#jobtype").val();
    queryData['jobtype'] = "CN";
    JobCategory.table.refresh({query: queryData});
};

$(function () {

    JobCategory.app = new Vue({
        el: '#jobCategoryPage',
        data: JobCategory.condition
    });

    var defaultColunms = JobCategory.initColumn();
    var table = new BSTable(JobCategory.id, "/jobCategory/list", defaultColunms);
    table.setPaginationType("client");
    JobCategory.table = table.init();
});
