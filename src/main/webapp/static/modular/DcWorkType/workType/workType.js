/**
 * 督查类型管理管理初始化
 */
var WorkType = {
    id: "WorkTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '交办事件类型名称', field: 'title', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WorkType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        WorkType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加督查类型管理
 */
WorkType.openAddWorkType = function () {
    var index = layer.open({
        type: 2,
        title: '添加督查类型管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/apiworkType/workType_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看督查类型管理详情
 */
WorkType.openWorkTypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '督查类型管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/apiworkType/workType_update/' + WorkType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除督查类型管理
 */
WorkType.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/apiworkType/delete", function (data) {
            Feng.success("删除成功!");
            WorkType.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workTypeId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询督查类型管理列表
 */
WorkType.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    WorkType.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WorkType.initColumn();
    var table = new BSTable(WorkType.id, "/apiworkType/list", defaultColunms);
    table.setPaginationType("client");
    WorkType.table = table.init();
});
