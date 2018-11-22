// var apiPrefix = "http://192.168.1.115:8080/api";
var apiPrefix = "http://110.185.102.194:8081/admin/api";
var apiUrl = {
    Login: apiPrefix + "/login",
    // 权限列表
    Permissions: apiPrefix + "/permissions",
    //督查责任单位
    //GET 督查责任单位管理详情 company-controller
    ApicompanyDetail: apiPrefix + "/apicompany/detail",
    //GET 获取督查责任单位列表 company-controller
    ApicompanyList: apiPrefix + "/apicompany/list",

    //会议管理
    //GET和POST 新增会议管理
    MeetingAdd: apiPrefix + "/apimeeting/add",
    //GET和POST 会议管理详情
    MeetingDetail: apiPrefix + "/apimeeting/detail",
    //GET和POST 获取会议管理列表
    MeetingList: apiPrefix + "/apimeeting/list",
    //GET和POST 修改会议管理
    MeetingUpdate: apiPrefix + "/apimeeting/update",

    //流程记录
    //GET和POST 获取流程记录日志
    WorkFlowLogList: apiPrefix + "/apiworkFlowLog/list",

    //督查督办功能API
    //POST 新增督察督办
    ApiassignWorkAdd: apiPrefix + "/apiassignWork/add",
    //GET和POST 获取督察督办列表
    ApiassignWorkList: apiPrefix + "/apiassignWork/list",
    //GET 督察督办单条详情
    ApiassignWorkDetail: apiPrefix + "/apiassignWork/detail",
    //POST 修改督察督办
    ApiassignWorkUpdate: apiPrefix + "/apiassignWork/update",

    //督查单位关联API
    //GET 督查单位关联详情
    ApiworkCompanyDetail: apiPrefix + "/apiworkCompany/detail",
    //GET 督查单位关联列表
    ApiworkCompanyList: apiPrefix + "/apiworkCompany/list",
    //POST 修改督查单位关联
    ApiworkCompanyUpdate: apiPrefix + "/apiworkCompany/update",

    //督查类型
    //GET和POST 获取督查类型列表
    SupervisionTypeList: apiPrefix + "/supervisionType/list",

    //督查类型API
    //GET 获取督查类型管理列表
    ApiworkTypeList: apiPrefix + "/apiworkType/list",
    ApiworkTypeUserList: apiPrefix + "/apiworkType/userlist",
    ApiworkTypeCompanyList: apiPrefix + "/apiworkType/companylist",

    //督查进度控制API
    //POST 新增督查单位进度
    ApiwcInfosAdd: apiPrefix + "/apiwcInfos/add",
    //GET 督查单位进度详情
    ApiwcInfosDetail: apiPrefix + "/apiwcInfos/detail",
    //GET 获取督查单位进度列表
    ApiwcInfosList: apiPrefix + "/apiwcInfos/list",

    //领导列表
    //GET&POST 获取领导列表
    ApileadershipList: apiPrefix + "/apileadership/list"

};
// $.ajaxSetup({
// 	contentType: "application/x-www-form-urlencoded;charset=utf-8",
// 	complete: function(XMLHttpRequest, textStatus) {
// 		//通过XMLHttpRequest取得响应结果
// 		var res = XMLHttpRequest.responseText;
// 		try {
// 			var jsonData = JSON.parse(res);
// 			if (textStatus == 200) {
// 				if (jsonData.code == 500 || jsonData.code == 700) {
// 					//如果超时就处理 ，指定要跳转的页面(比如登陆页)
// 					window.location.replace("/login.html");
// 				}
// 			}
//
// 		} catch (e) {}
// 	}
// });

function getHandleTime(startTime, endTime) {
    if (endTime == '' || endTime == undefined || endTime == null) {
        return "还未完成";
    }
    //结束时间
    end_str = endTime.replace(/-/g, "/"); //一般得到的时间的格式都是：yyyy-MM-dd hh24:mi:ss，所以我就用了这个做例子，是/的格式，就不用replace了。
    var end_date = new Date(end_str); //将字符串转化为时间
    //开始时间
    sta_str = startTime.replace(/-/g, "/");
    var sta_date = new Date(sta_str);
    var num = end_date - sta_date; //求出两个时间的时间差，这个是天数
    //计算出相差天数
    var days = Math.floor(num / (24 * 3600 * 1000))

//计算出小时数

    var leave1 = num % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / (3600 * 1000))
//计算相差分钟数
    var leave2 = leave1 % (3600 * 1000)        //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / (60 * 1000))
//计算相差秒数
    var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000)
    return days + "天 " + hours + "小时 " + minutes + " 分钟" + seconds + " 秒";

}

$.apiAjax = function (options) {
    var index_ts = "";
    /*等待框索引*/
    var defaults = {
        url: "",
        data: null,
        type: "GET",
        dataType: "json",
        success: null,
        error: null,
        isheaders: true,
        IsLoading: false,
        /*是否提示*/
        content: null,
        /*提示内容*/
        IsClose: false,
        alert: true,
        async: false
    };
    var options = $.extend(defaults, options);
    if (options.IsLoading || options.content) {
        options.content = options.content ? options.content : "提交中，请稍等。。。";
        index_ts = layer.open({
            type: 3,
            content: options.content
        });
    }

    if (options.isheaders) {
        options.headers = {
            "Authorization": "Bearer " + localStorage.getItem("token")
        };
    } else {
        options.headers = null;
    }
    $.ajax({
        type: options.type,
        url: options.url,
        data: options.data,
        dataType: "json",
        contentType: "application/json",
        headers: options.headers,
        async: options.async,
        success: function (ret) {
            if (ret.code == 200) {
                if (options.alert) {
                    DialogMsg(ret.message, 1);
                }
                options.success(ret.data);
            } else if (ret.code == 700) {
                window.location.replace("/login.html");
            } else {
                DialogMsg(ret.message);
                return;
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (errorThrown) {
                DialogMsg(errorThrown)
            } else {
                DialogMsg("请求错误");
            }
            if (options.error != null) {
                options.error();
            }
        },
        complete: function () {
            //关闭等待页面
            if (index_ts) {
                layer.close(index_ts);
            }
            //是否关闭当前窗口
            if (options.IsClose) {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);

            }
        }
    })
}


/*下拉选择*/
$.fn.ComboBox = function (options) {
    //options参数：description,height,width,allowSearch,url,param,data,defultSelect
    var $select = $(this);
    if (!$select.attr('id')) {
        return false;
    }
    if (options) {
        if ($select.find('.ui-select-text').length == 0) {
            var $select_html = "";
            $select_html += "<div class=\"ui-select-text\" style='color:#999;'>" + options.description +
                "</div><a oprate='close' class=\"fa fa-close\" href=\"javascript:;\" style=\"text-decoration: none;position:absolute;top: 28%;right:10px;font-size: 16px; color:#ddd\"></a><div>";
            $select_html += "<div class=\"ui-select-option\">";
            $select_html += "<div class=\"ui-select-option-content\" style=\"max-height: " + options.height + "\">" + $select.html() +
                "</div>";
            if (options.allowSearch) {
                $select_html +=
                    "<div class=\"ui-select-option-search\"><input type=\"text\" class=\"form-control\" placeholder=\"搜索关键字\" /><span class=\"input-query\" title=\"Search\"><i class=\"fa fa-search\"></i></span></div>";
            }
            $select_html += "</div>";
            $select.html('');
            $select.html($select_html);
        }
    }
    if (options.height == undefined) {
        options.height = 200;
    }
    var $option_html = $($("<p>").append($select.find('.ui-select-option').clone()).html());
    $option_html.attr('id', $select.attr('id') + '-option');
    $select.find('.ui-select-option').remove();
    if ($option_html.length > 0) {
        $('body').find('#' + $select.attr('id') + '-option').remove();
    }
    $('body').prepend($option_html);
    var $option = $("#" + $select.attr('id') + "-option");
    if (options.url != undefined) {
        $option.find('.ui-select-option-content').html('');
        $.ajax({
            url: options.url,
            data: options.param,
            type: "GET",
            dataType: "json",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            async: false,
            success: function (data) {
                options.data = data.data;
                var json = data.data;
                loadComboBoxView(json, options.defultSelect, options.clear);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                dialogMsg(errorThrown, -1);
            }
        });
    } else if (options.data != undefined) {
        var json = options.data;
        loadComboBoxView(json, options.defultSelect, options.clear);
    } else {
        $option.find('li').css('padding', "0 5px");
        $option.find('li').click(function (e) {
            var data_text = $(this).text();
            var data_value = $(this).attr('data-value');
            $select.attr("data-value", data_value).attr("data-text", data_text);
            $select.find('.ui-select-text').html(data_text).css('color', '#000');
            $option.slideUp(150);
            $select.trigger("change");
            e.stopPropagation();
        }).hover(function (e) {
            if (!$(this).hasClass('liactive')) {
                $(this).toggleClass('on');
            }
            e.stopPropagation();
        });
    }
    $("a[oprate=close]").mousedown(function () {
        var sel = $(this).parent();
        sel.ClearCombValue();
    })

    function loadComboBoxView(json, defultSelect, clear, searchValue, m) {
        var length = clear ? -1 : 0;
        if (json && json.length > length) {
            var $_html = $('<ul style="max-height:' + options.height + 'px"></ul>');
            if (options.description) {
                $_html.append('<li data-value="">' + options.description + '</li>');
            }
            $.each(json, function (i) {
                var row = json[i];
                var title = row[options.title];
                if (title == undefined) {
                    title = "";
                }
                if (searchValue != undefined) {
                    if (row[m.text].indexOf(searchValue) != -1) {

                        $_html.append('<li data-value="' + row[options.id] + '" title="' + title + '">' + row[options.text] + '</li>');
                    }
                } else {
                    if (i == 0 && defultSelect) {
                        $select.attr("data-value", row[options.id]).attr("data-text", row[options.text]);
                        $select.find('.ui-select-text').html(row[options.text]).css('color', '#000');
                    }
                    $_html.append('<li data-value="' + row[options.id] + '" title="' + title + '">' + row[options.text] + '</li>');
                }
            });
            $option.find('.ui-select-option-content').html($_html);
            $option.find('li').css('padding', "0 5px");
            $option.find('li').click(function (e) {
                var data_text = $(this).text();
                var data_value = $(this).attr('data-value');
                if (options.multipleselect && data_value != "") {

                    var nowval = $select.attr("data-value");
                    var nowtext = $select.attr("data-text");
                    if (nowval != undefined && nowval != "" && nowval.indexOf(data_value) == -1) {

                        $option.find('[data-value=' + data_value + ']').addClass('liactive');
                        data_text = nowtext + "," + data_text;
                        data_value = nowval + "," + data_value;
                    } else if (nowval != undefined && nowval.indexOf(data_value) != -1) {
                        $option.find('[data-value=' + data_value + ']').removeClass('liactive');
                        //$option.find('[data-value=' + data_value + ']').removeClass("on");
                        var nowtextarray = nowtext.split(",");
                        nowtextarray.splice(nowtextarray.indexOf(data_text), 1);
                        var nowvalarray = nowval.split(",");
                        nowvalarray.splice(nowvalarray.indexOf(data_value), 1);
                        data_text = nowtextarray.join(",")
                        data_value = nowvalarray.join(",")
                    } else if (nowval == "" || nowval == undefined) {
                        $option.find('[data-value=' + data_value + ']').addClass('liactive');
                    }
                } else {
                    $option.slideUp(150);
                }

                $select.attr("data-value", data_value).attr("data-text", data_text);
                $select.find('.ui-select-text').html(data_text).css('color', '#000');
                if (options.onSelect) {
                    if (data_value == "") {
                        data_text = "";
                    }
                    options.onSelect(data_value, data_text, $select);
                }
                $select.trigger("change");
                e.stopPropagation();
            }).hover(function (e) {
                if (!$(this).hasClass('liactive')) {
                    $(this).toggleClass('on');
                }
                e.stopPropagation();
            });
        }
    }

    //操作搜索事件
    if (options.allowSearch) {
        $option.find('.ui-select-option-search').find('input').bind("keypress", function (e) {
            if (event.keyCode == "13") {
                var value = $(this).val();
                loadComboBoxView($(this)[0].options.data, false, false, value, $(this)[0].options);
            }
        }).focus(function () {
            $(this).select();
        })[0]["options"] = options;
    }

    $select.unbind('click');
    ulclick()

    function ulclick() {
        $select.bind("click", function (e) {
            if ($select.attr('readonly') == 'readonly' || $select.attr('disabled') == 'disabled') {
                return false;
            }
            $(this).addClass('ui-select-focus');
            if ($option.is(":hidden")) {
                $select.find('.ui-select-option').hide();
                $('.ui-select-option').hide();
                var left = $select.offset().left;
                var top = $select.offset().top + 29;
                var width = $select.width();
                if (options.width) {
                    width = options.width;
                }
                if (($option.height() + top) < $(window).height()) {
                    $option.slideDown(150).css({
                        top: top,
                        left: left,
                        width: width
                    });
                } else {
                    var _top = (top - $option.height() - 32)
                    $option.show().css({
                        top: _top,
                        left: left,
                        width: width
                    });
                    $option.attr('data-show', true);
                }
                $option.css('border-top', '1px solid #ccc');
                $option.find('li').removeClass('liactive');
                if ($select.attr('data-value') != undefined) {
                    var valuearray = $select.attr('data-value').split(",")
                    for (var item in valuearray) {
                        $option.find('[data-value=' + valuearray[item] + ']').addClass('liactive');
                    }
                }
                $option.find('[data-value=]').removeClass('liactive');
                $option.find('.ui-select-option-search').find('input').select();
            } else {
                if ($option.attr('data-show')) {
                    $option.hide();
                    $option.removeAttr('data-show')
                } else {
                    $option.slideUp(150);
                    $option.attr('data-show', true)
                }
            }
            e.stopPropagation();
        });
    }

    $(document).click(function (e) {
        var e = e ? e : window.event;
        var tar = e.srcElement || e.target;
        if (!$(tar).hasClass('form-control')) {
            if ($option.attr('data-show')) {
                $option.hide();
            } else {
                $option.slideUp(150);
            }
            $select.removeClass('ui-select-focus');
            e.stopPropagation();
        }
    });
    return $select;

}
$.fn.ClearCombValue = function () {
    var $select = $(this);
    var $option = $("#" + $select.attr('id') + "-option");
    $select.attr('data-value', "");
    $select.attr('data-text', "");
    $option.find(".on").removeClass("on")
    $select.find('.ui-select-text').html("==请选择==").css("color", " #999");
    return $select;
}
$.fn.ComboBoxSetValue = function (value) {
    if ($.isNullOrEmpty(value)) {
        return;
    }
    var $select = $(this);
    var $option = $("#" + $select.attr('id') + "-option");
    $select.attr('data-value', value);
    var data_text = $option.find('ul').find('[data-value=' + value + ']').html();
    if (data_text) {
        $select.attr('data-text', data_text);
        $select.find('.ui-select-text').html(data_text).css('color', '#000');
        $option.find('ul').find('[data-value=' + value + ']').addClass('liactive')
        $option.find('ul').find('[data-value=' + value + ']').click();
    }
    return $select;
}


$.fn.authorizeButton = function () {
    var arrPermissions = localStorage.getItem("Permissions").split(',');
    var $element = $(this);
    $element.css("display", "none");
    $element.each(function () {
        var $a = $(this);
        var id = $a.attr("id");
        if (id) {
            if (arrPermissions.indexOf(id.toString()) > 0) {
                $($a).css("display", "");
            }

        } else {
            $($a).css("display", "");
        }

    });
}


/*获取URL值*/
request = function (keyValue) {
    var search = location.search.slice(1);
    var arr = search.split("&");
    for (var i = 0; i < arr.length; i++) {
        var ar = arr[i].split("=");
        if (ar[0] == keyValue) {
            if (unescape(ar[1]) == 'undefined') {
                return "";
            } else {
                return unescape(ar[1]);
            }
        }
    }
    return "";
}

/*获取表单数据*/
$.fn.GetWebControls = function (keyValue) {
    var reVal = "";
    $(this).find('input,select,textarea,.ui-select,[type=select]').each(function (r) {
        var id = $(this).attr('id');
        var name = $(this).attr('name');
        if (!id) {
            id = name;
        }
        var type = $(this).attr('type');
        switch (type) {
            case "checkbox":
                if ($("#" + id).is(":checked")) {
                    reVal += '"' + name + '"' + ':' + '"1",'
                } else {
                    reVal += '"' + name + '"' + ':' + '"0",'
                }
                break;
            case "select":
                var value = $("#" + id).attr('data-value');
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + name + '"' + ':' + '"' + $.trim(value) + '",'
                break;
            case "selectTree":
                var value = $("#" + id).attr('data-value');
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + name + '"' + ':' + '"' + $.trim(value) + '",'
                break;
            case "radio":
                if (id == name + "_Y") {
                    var radio = $('input:radio[name=' + name + ']:checked');
                    if (radio.attr("id") == id) {
                        reVal += '"' + name + '"' + ':' + '1,'
                    } else {
                        reVal += '"' + name + '"' + ':' + '0,'
                    }
                }
                break;

            default:
                var value = $("#" + id).val();
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + name + '"' + ':' + '"' + $.trim(value) + '",'
                break;
        }
    });
    reVal = reVal.substr(0, reVal.length - 1);
    if (!keyValue) {
        reVal = reVal.replace(/&nbsp;/g, '');
    }
    reVal = reVal.replace(/\\/g, '\\\\');
    reVal = reVal.replace(/\n/g, '\\n');
    var postdata = jQuery.parseJSON('{' + reVal + '}');
    return postdata;
};

/*提醒框*/
DialogMsg = function (content, type) {
    if (!type) {
        type = 0;
    }
    parent.layer.msg(content, {
        icon: type
    }); //0-警告；1-成功；2-失败

}

/*判断*/
$.isNullOrEmpty = function (obj) {
    if ((typeof(obj) == "string" && obj == "") || obj == null || obj == undefined) {
        return true;
    } else {
        return false;
    }
}
