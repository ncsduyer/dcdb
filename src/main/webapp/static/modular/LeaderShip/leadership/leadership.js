/**
 * 领导列表管理初始化
 */
var Leadership = {
    id: "LeadershipTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Leadership.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        // {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '领导姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '部门', field: 'department', visible: true, align: 'center', valign: 'middle'},
        {title: '职务', field: 'title', visible: true, align: 'center', valign: 'middle'},
        {title: '电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Leadership.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Leadership.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加领导列表
 */
Leadership.openAddLeadership = function () {
    var index = layer.open({
        type: 2,
        title: '添加领导列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/apileadership/leadership_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看领导列表详情
 */
Leadership.openLeadershipDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '领导列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/apileadership/leadership_update/' + Leadership.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除领导列表
 */
Leadership.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/apileadership/delete", function (data) {
            Feng.success("删除成功!");
            Leadership.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("leadershipId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询领导列表列表
 */
Leadership.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['department'] = $("#department").val();
    queryData['title'] = $("#title").val();
    queryData['phone'] = $("#phone").val();
    Leadership.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Leadership.initColumn();
    var table = new BSTable(Leadership.id, "/apileadership/list", defaultColunms);
    table.setPaginationType("client");
    Leadership.table = table.init();
});
