/**
 * 初始化app菜单管理详情对话框
 */
var AppMenuInfoDlg = {
    appMenuInfoData: {}
};

/**
 * 清除数据
 */
AppMenuInfoDlg.clearData = function () {
    this.appMenuInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppMenuInfoDlg.set = function (key, val) {
    this.appMenuInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppMenuInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AppMenuInfoDlg.close = function () {
    parent.layer.close(window.parent.AppMenu.layerIndex);
}

/**
 * 收集数据
 */
AppMenuInfoDlg.collectData = function () {
    this
        .set('id')
        .set('menuId')
        .set('type')
        .set('statue')
        .set('pcUrl')
        .set('order')
        .set('wapUrl');
}

/**
 * 提交添加
 */
AppMenuInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appMenu/add", function (data) {
        Feng.success("添加成功!");
        window.parent.AppMenu.table.refresh();
        AppMenuInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appMenuInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AppMenuInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appMenu/update", function (data) {
        Feng.success("修改成功!");
        window.parent.AppMenu.table.refresh();
        AppMenuInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appMenuInfoData);
    ajax.start();
}

$(function () {

});
