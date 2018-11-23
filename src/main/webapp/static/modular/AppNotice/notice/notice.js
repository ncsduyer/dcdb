/**
 * 消息记录管理初始化
 */
var Notice = {
    id: "NoticeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Notice.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
        {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'creater', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Notice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Notice.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加消息记录
 */
Notice.openAddNotice = function () {
    var index = layer.open({
        type: 2,
        title: '添加消息记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/notice/notice_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看消息记录详情
 */
Notice.openNoticeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '消息记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/notice/notice_update/' + Notice.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除消息记录
 */
Notice.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/notice/delete", function (data) {
            Feng.success("删除成功!");
            Notice.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("noticeId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询消息记录列表
 */
Notice.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Notice.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Notice.initColumn();
    var table = new BSTable(Notice.id, "/notice/list", defaultColunms);
    table.setPaginationType("client");
    Notice.table = table.init();
});
