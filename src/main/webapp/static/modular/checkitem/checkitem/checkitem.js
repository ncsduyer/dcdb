/**
 * 督查类型管理初始化
 */
var Checkitem = {
    id: "CheckitemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Checkitem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '督查项类别（1-事项；2-会议；3-公文；4-信息）', field: 'itemclass', visible: true, align: 'center', valign: 'middle'},
            {title: '项描述', field: 'itemdesc', visible: true, align: 'center', valign: 'middle'},
            {title: '状态（0-停用；1-启用）', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Checkitem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Checkitem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加督查类型
 */
Checkitem.openAddCheckitem = function () {
    var index = layer.open({
        type: 2,
        title: '添加督查类型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/checkitem/checkitem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看督查类型详情
 */
Checkitem.openCheckitemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '督查类型详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/checkitem/checkitem_update/' + Checkitem.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除督查类型
 */
Checkitem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/checkitem/delete", function (data) {
            Feng.success("删除成功!");
            Checkitem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("checkitemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询督查类型列表
 */
Checkitem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Checkitem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Checkitem.initColumn();
    var table = new BSTable(Checkitem.id, "/checkitem/list", defaultColunms);
    table.setPaginationType("client");
    Checkitem.table = table.init();
});
