/**
 * 初始化app消息通知详情对话框
 */
var AppNoticeInfoDlg = {
    appNoticeInfoData: {}
};

/**
 * 清除数据
 */
AppNoticeInfoDlg.clearData = function () {
    this.appNoticeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppNoticeInfoDlg.set = function (key, val) {
    this.appNoticeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AppNoticeInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AppNoticeInfoDlg.close = function () {
    parent.layer.close(window.parent.AppNotice.layerIndex);
}

/**
 * 收集数据
 */
AppNoticeInfoDlg.collectData = function () {
    this
        .set('id')
        .set('title')
        .set('type')
        .set('content')
        .set('createtime')
        .set('creater');
}

/**
 * 提交添加
 */
AppNoticeInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appNotice/add", function (data) {
        Feng.success("添加成功!");
        window.parent.AppNotice.table.refresh();
        AppNoticeInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appNoticeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AppNoticeInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/appNotice/update", function (data) {
        Feng.success("修改成功!");
        window.parent.AppNotice.table.refresh();
        AppNoticeInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.appNoticeInfoData);
    ajax.start();
}

$(function () {

});
