/**
 * 单位类型管理管理初始化
 */
var UnitType = {
    id: "UnitTypeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UnitType.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '类型名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
UnitType.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UnitType.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加单位类型管理
 */
UnitType.openAddUnitType = function () {
    var index = layer.open({
        type: 2,
        title: '添加单位类型管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/unitType/unitType_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看单位类型管理详情
 */
UnitType.openUnitTypeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '单位类型管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/unitType/unitType_update/' + UnitType.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除单位类型管理
 */
UnitType.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/unitType/delete", function (data) {
            Feng.success("删除成功!");
            UnitType.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("unitTypeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询单位类型管理列表
 */
UnitType.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UnitType.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UnitType.initColumn();
    var table = new BSTable(UnitType.id, "/unitType/list", defaultColunms);
    table.setPaginationType("client");
    UnitType.table = table.init();
});
