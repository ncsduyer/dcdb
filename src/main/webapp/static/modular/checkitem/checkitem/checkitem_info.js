/**
 * 初始化督查类型详情对话框
 */
var CheckitemInfoDlg = {
    checkitemInfoData : {}
};

/**
 * 清除数据
 */
CheckitemInfoDlg.clearData = function() {
    this.checkitemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckitemInfoDlg.set = function(key, val) {
    this.checkitemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CheckitemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CheckitemInfoDlg.close = function() {
    parent.layer.close(window.parent.Checkitem.layerIndex);
}

/**
 * 收集数据
 */
CheckitemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('itemclass')
    .set('itemdesc')
    .set('status');
}

/**
 * 提交添加
 */
CheckitemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkitem/add", function(data){
        Feng.success("添加成功!");
        window.parent.Checkitem.table.refresh();
        CheckitemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkitemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CheckitemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/checkitem/update", function(data){
        Feng.success("修改成功!");
        window.parent.Checkitem.table.refresh();
        CheckitemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.checkitemInfoData);
    ajax.start();
}

$(function() {

});
