/**
 * 初始化督查责任单位管理详情对话框
 */
var CompanyInfoDlg = {
    companyInfoData: {}
};

/**
 * 清除数据
 */
CompanyInfoDlg.clearData = function () {
    this.companyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyInfoDlg.set = function (key, val) {
    this.companyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanyInfoDlg.close = function () {
    parent.layer.close(window.parent.Company.layerIndex);
}

/**
 * 收集数据
 */
CompanyInfoDlg.collectData = function () {
    this
        .set('id')
        .set('title')
        .set('abbTitle')
        .set('type')
        .set('order')
        .set('adress')
        .set('tel')
        .set('contact')
        .set('contactPhone')
        .set('remarks')
        .set('status');
}

/**
 * 提交添加
 */
CompanyInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apicompany/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Company.table.refresh();
        CompanyInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CompanyInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/apicompany/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Company.table.refresh();
        CompanyInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyInfoData);
    ajax.start();
}

$(function () {

});
