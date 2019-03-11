/**
 * app版本管理管理初始化
 */
var TTbVersionUpgrade = {
    id: "TTbVersionUpgradeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TTbVersionUpgrade.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '客户端设备id 1安卓pad 2安卓手机 3ios手机 4iospad', field: 'appType', visible: true, align: 'center', valign: 'middle'},
            {title: '大版本号id', field: 'versionId', visible: true, align: 'center', valign: 'middle'},
            {title: '小版本号', field: 'versionMini', visible: true, align: 'center', valign: 'middle'},
            {title: '版本标识 1.2', field: 'versionCode', visible: true, align: 'center', valign: 'middle'},
            {title: '是否升级  1升级，0不升级，2强制升级', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'apkUrl', visible: true, align: 'center', valign: 'middle'},
            {title: 'app更新内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '升级提示', field: 'upgradePoint', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'updateTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TTbVersionUpgrade.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TTbVersionUpgrade.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加app版本管理
 */
TTbVersionUpgrade.openAddTTbVersionUpgrade = function () {
    var index = layer.open({
        type: 2,
        title: '添加app版本管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tTbVersionUpgrade/tTbVersionUpgrade_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看app版本管理详情
 */
TTbVersionUpgrade.openTTbVersionUpgradeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'app版本管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tTbVersionUpgrade/tTbVersionUpgrade_update/' + TTbVersionUpgrade.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除app版本管理
 */
TTbVersionUpgrade.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tTbVersionUpgrade/delete", function (data) {
            Feng.success("删除成功!");
            TTbVersionUpgrade.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tTbVersionUpgradeId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询app版本管理列表
 */
TTbVersionUpgrade.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TTbVersionUpgrade.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TTbVersionUpgrade.initColumn();
    var table = new BSTable(TTbVersionUpgrade.id, "/tTbVersionUpgrade/list", defaultColunms);
    table.setPaginationType("client");
    TTbVersionUpgrade.table = table.init();
});
