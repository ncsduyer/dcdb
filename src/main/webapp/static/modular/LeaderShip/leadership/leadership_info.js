/**
 * 初始化领导列表详情对话框
 */
var LeadershipInfoDlg = {
    leadershipInfoData: {}
};

/**
 * 清除数据
 */
LeadershipInfoDlg.clearData = function () {
    this.leadershipInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LeadershipInfoDlg.set = function (key, val) {
    this.leadershipInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LeadershipInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LeadershipInfoDlg.close = function () {
    parent.layer.close(window.parent.Leadership.layerIndex);
}

/**
 * 收集数据
 */
LeadershipInfoDlg.collectData = function () {
    this
        .set('id')
        .set('name')
        .set('department')
        .set('title')
        .set('phone')
        .set('email');
}

/**
 * 提交添加
 */
LeadershipInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apileadership/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Leadership.table.refresh();
        LeadershipInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.leadershipInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
LeadershipInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apileadership/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Leadership.table.refresh();
        LeadershipInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.leadershipInfoData);
    ajax.start();
}

$(function () {

});
