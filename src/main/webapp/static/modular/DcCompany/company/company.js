/**
 * 督查责任单位管理管理初始化
 */
var Company = {
    id: "CompanyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Company.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键id', field: 'id', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'order', align: 'center', valign: 'middle', sortable: true},
        {title: '单位名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
        {title: '简称', field: 'abbTitle', visible: true, align: 'center', valign: 'middle'},
        {title: '地址', field: 'adress', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'tel', visible: true, align: 'center', valign: 'middle'},
        {title: '负责人', field: 'contact', visible: true, align: 'center', valign: 'middle'},
        {title: '负责人电话', field: 'contactPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '详细信息', field: 'remarks', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Company.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Company.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加督查责任单位管理
 */
Company.openAddCompany = function () {
    var index = layer.open({
        type: 2,
        title: '添加督查责任单位管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/apicompany/company_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看督查责任单位管理详情
 */
Company.openCompanyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '督查责任单位管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/apicompany/company_update/' + Company.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除督查责任单位管理
 */
Company.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/apicompany/delete", function (data) {
            Feng.success("删除成功!");
            Company.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("companyId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询督查责任单位管理列表
 */
Company.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Company.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Company.initColumn();
    var table = new BSTable(Company.id, "/apicompany/list", defaultColunms);
    table.setPaginationType("client");
    Company.table = table.init();
});
