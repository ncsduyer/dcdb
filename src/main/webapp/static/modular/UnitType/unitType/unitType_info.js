/**
 * 初始化单位类型管理详情对话框
 */
var UnitTypeInfoDlg = {
    unitTypeInfoData : {}
};

/**
 * 清除数据
 */
UnitTypeInfoDlg.clearData = function() {
    this.unitTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnitTypeInfoDlg.set = function(key, val) {
    this.unitTypeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UnitTypeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UnitTypeInfoDlg.close = function() {
    parent.layer.close(window.parent.UnitType.layerIndex);
}

/**
 * 收集数据
 */
UnitTypeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('title')
    .set('status');
}

/**
 * 提交添加
 */
UnitTypeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unitType/add", function(data){
        Feng.success("添加成功!");
        window.parent.UnitType.table.refresh();
        UnitTypeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unitTypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UnitTypeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/unitType/update", function(data){
        Feng.success("修改成功!");
        window.parent.UnitType.table.refresh();
        UnitTypeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.unitTypeInfoData);
    ajax.start();
}

$(function() {

});
