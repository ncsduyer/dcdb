/**
 * 初始化资源管理详情对话框
 */
var AssetInfoDlg = {
    assetInfoData : {}
};

/**
 * 清除数据
 */
AssetInfoDlg.clearData = function() {
    this.assetInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AssetInfoDlg.set = function(key, val) {
    this.assetInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AssetInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AssetInfoDlg.close = function() {
    parent.layer.close(window.parent.Asset.layerIndex);
}

/**
 * 收集数据
 */
AssetInfoDlg.collectData = function() {
    this
    .set('id')
    .set('userId')
    .set('fileSize')
    .set('createTime')
    .set('status')
    .set('downloadTimes')
    .set('fileKey')
    .set('filename')
    .set('filePath')
    .set('fileMd5')
    .set('fileSha1')
    .set('suffix')
    .set('more');
}

/**
 * 提交添加
 */
AssetInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/asset/add", function(data){
        Feng.success("添加成功!");
        window.parent.Asset.table.refresh();
        AssetInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assetInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AssetInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/asset/update", function(data){
        Feng.success("修改成功!");
        window.parent.Asset.table.refresh();
        AssetInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.assetInfoData);
    ajax.start();
}

$(function() {

});
