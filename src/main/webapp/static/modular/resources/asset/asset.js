/**
 * 资源管理管理初始化
 */
var Asset = {
    id: "AssetTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Asset.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '用户id', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '文件大小,单位B', field: 'fileSize', visible: true, align: 'center', valign: 'middle'},
            {title: '上传时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态;1:可用,0:不可用', field: 'status', visible: true, align: 'center', valign: 'middle'},
            {title: '下载次数', field: 'downloadTimes', visible: true, align: 'center', valign: 'middle'},
            {title: '文件惟一码', field: 'fileKey', visible: true, align: 'center', valign: 'middle'},
            {title: '文件名', field: 'filename', visible: true, align: 'center', valign: 'middle'},
            {title: '文件路径,相对于根目录,可以为url', field: 'filePath', visible: true, align: 'center', valign: 'middle'},
            {title: '文件md5值', field: 'fileMd5', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'fileSha1', visible: true, align: 'center', valign: 'middle'},
            {title: '文件后缀名,不包括点', field: 'suffix', visible: true, align: 'center', valign: 'middle'},
            {title: '其它详细信息,JSON格式', field: 'more', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Asset.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Asset.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加资源管理
 */
Asset.openAddAsset = function () {
    var index = layer.open({
        type: 2,
        title: '添加资源管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/asset/asset_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看资源管理详情
 */
Asset.openAssetDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '资源管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/asset/asset_update/' + Asset.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除资源管理
 */
Asset.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/asset/delete", function (data) {
            Feng.success("删除成功!");
            Asset.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("assetId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询资源管理列表
 */
Asset.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Asset.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Asset.initColumn();
    var table = new BSTable(Asset.id, "/asset/list", defaultColunms);
    table.setPaginationType("client");
    Asset.table = table.init();
});
