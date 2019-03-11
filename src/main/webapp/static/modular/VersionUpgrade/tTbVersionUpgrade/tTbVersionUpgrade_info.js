/**
 * 初始化app版本管理详情对话框
 */
var TTbVersionUpgradeInfoDlg = {
    tTbVersionUpgradeInfoData : {}
};

/**
 * 清除数据
 */
TTbVersionUpgradeInfoDlg.clearData = function() {
    this.tTbVersionUpgradeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TTbVersionUpgradeInfoDlg.set = function(key, val) {
    this.tTbVersionUpgradeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TTbVersionUpgradeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TTbVersionUpgradeInfoDlg.close = function() {
    parent.layer.close(window.parent.TTbVersionUpgrade.layerIndex);
}

/**
 * 收集数据
 */
TTbVersionUpgradeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('appType')
    .set('versionId')
    .set('versionMini')
    .set('versionCode')
    .set('type')
    .set('apkUrl')
    .set('content')
    .set('upgradePoint')
    .set('status')
    .set('createTime')
    .set('updateTime');
}

/**
 * 提交添加
 */
TTbVersionUpgradeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tTbVersionUpgrade/add", function(data){
        Feng.success("添加成功!");
        window.parent.TTbVersionUpgrade.table.refresh();
        TTbVersionUpgradeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tTbVersionUpgradeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TTbVersionUpgradeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tTbVersionUpgrade/update", function(data){
        Feng.success("修改成功!");
        window.parent.TTbVersionUpgrade.table.refresh();
        TTbVersionUpgradeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tTbVersionUpgradeInfoData);
    ajax.start();
}

$(function() {

});
