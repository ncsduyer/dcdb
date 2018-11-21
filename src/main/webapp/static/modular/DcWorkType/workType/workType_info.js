/**
 * 初始化督查类型管理详情对话框
 */
var WorkTypeInfoDlg = {
    workTypeInfoData: {}
};

/**
 * 清除数据
 */
WorkTypeInfoDlg.clearData = function () {
    this.workTypeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkTypeInfoDlg.set = function (key, val) {
    this.workTypeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkTypeInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkTypeInfoDlg.close = function () {
    parent.layer.close(window.parent.WorkType.layerIndex);
}

/**
 * 收集数据
 */
WorkTypeInfoDlg.collectData = function () {
    this
        .set('id')
        .set('title');
}

/**
 * 提交添加
 */
WorkTypeInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apiworkType/add", function (data) {
        Feng.success("添加成功!");
        window.parent.WorkType.table.refresh();
        WorkTypeInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workTypeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkTypeInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apiworkType/update", function (data) {
        Feng.success("修改成功!");
        window.parent.WorkType.table.refresh();
        WorkTypeInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workTypeInfoData);
    ajax.start();
}

$(function () {

});
