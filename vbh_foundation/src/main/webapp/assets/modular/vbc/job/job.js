/**
 * 通知管理初始化
 */
var Job = {
    id: "JobTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    condition: {
        jobname: ""
    }
};

/**
 * 初始化表格的列
 */
Job.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'jobId', visible: false, align: 'center', valign: 'middle'},
        {title: '职位分类', field: 'jobCategoryId', align: 'center', valign: 'middle', sortable: true},
        {title: '岗位名称', field: 'jobName', align: 'center', valign: 'middle', sortable: true},
        {title: '工作地点', field: 'jobLocation', align: 'center', valign: 'middle', sortable: true},
        {title: '工作年限', field: 'jobYear', align: 'center', valign: 'middle', sortable: true},
        {title: '薪资待遇', field: 'salary', align: 'center', valign: 'middle', sortable: true},
        {title: '学历要求', field: 'education', align: 'center', valign: 'middle', sortable: true},
        {title: '招聘人数', field: 'jobNum', align: 'center', valign: 'middle', sortable: true},
        {
            title: '发布时间',
            field: 'lastUpdateTime',
            align: 'center',
            valign: 'middle',
            sortable: true
        },
        {
            title: '审核',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function (value, row, index) {
                if (value == "0") {
                    return "未上架";
                } else {
                    return "上架";
                }
            }
        }

    ];
};

/**
 * 检查是否选中
 */
Job.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Job.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加通知
 */
Job.openAddJob = function () {
    var index = layer.open({
        type: 2,
        title: '添加职位',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/job/job_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看通知详情
 */
Job.openJobDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '职位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/job/job_update/' + Job.seItem.jobId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除通知
 */
Job.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/job/delete", function (data) {
                Feng.success("删除成功!");
                Job.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobId", Job.seItem.jobId);
            ajax.start();
        };

        Feng.confirm("是否删除该条信息?", operation);
    }
};

/**
 * 查询通知列表
 */
Job.search = function () {
    var queryData = {};
    queryData['jobname'] = $("#jobname").val();
    Job.table.refresh({query: queryData});
};
/**
 * 据语言查询列表
 */
Job.queryByLanguage = function () {
    var queryData = {};
    queryData['jobname'] = "US";
    Job.table.refresh({query: queryData});
};

$(function () {

    Job.app = new Vue({
        el: '#jobPage',
        data: Job.condition
    });

    var defaultColunms = Job.initColumn();
    var table = new BSTable(Job.id, "/job/list", defaultColunms);
    //debugger;
    table.setPaginationType("client");
    Job.table = table.init();
});
