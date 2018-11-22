var common = {
    tp: {},
    fn: {},
    exfn: function (name) {
        var fn = common.fn[name];
        if (fn) fn();
    },
    refn: function (name, fn) {
        common.fn[name] = fn;
    },
    get: function (url, data, complete, faile, opt) {
        common.ajax(url, "GET", data, complete, faile, opt);
    },
    post: function (url, data, complete, faile, opt) {
        common.ajax(url, "POST", data, complete, faile, opt);
    },
    ajax: function (url, type, data, complete, faile, opt) {
        opt = $.extend({
            url: url,
            type: type,
            dataType: 'json',
            mask: true,
            data: data,
            cbcomplete: complete,
            cbfaile: faile
        }, (opt || {}));
        $.ajax(common.ajaxoptbind(opt));
    },
    fixed: function (obj, value, digitlength) {
        var $obj = $(obj);
        if (!$obj.val() && $obj.attr('num-null') == 'true') return;
        if (!digitlength && digitlength != 0) {
            digitlength = $obj.attr('num-length') || 2;
        }
        value = $.trim(value);
        value = (value && !isNaN(value)) || value == '0' ? parseFloat(value) : parseFloat($obj.attr('num-dfvalue') || 1);
        var minvalue = $obj.attr('num-minvalue');
        if (minvalue) {
            minvalue = parseFloat(minvalue);
            if (value < minvalue) value = minvalue;
        }
        var maxvalue = $obj.attr('num-maxvalue');
        if (maxvalue) {
            maxvalue = parseFloat(maxvalue);
            if (value > maxvalue) value = maxvalue;
        }
        $obj.val(value.toFixed(digitlength));
    },
    initnum: function () {
        $(document.body).on('keypress', '.input-num', function (e) {
            return !isNaN(this.value + String.fromCharCode(e.keyCode));
        }).on('change', '.input-num', function (e) {
            common.fixed(this, this.value);
        });
    },
    ajaxoptbind: function (opt) {
        opt = $.extend({dataType: 'json', mask: true, autofaile: true, autologin: true, cache: false}, opt);
        opt.beforeSend = function () {
            if (opt.mask) opt.layerindex = common.load();
        };
        opt.success = function (data) {
            if (data.ResponseCode == 'Success' && opt.cbcomplete) opt.cbcomplete(data);
            else {
                if (opt.cbfaile) opt.cbfaile(data);
                if (opt.autofaile && data.ResponseCode != 'NoSession' && data.ResponseCode != 'NoPwr' && data.ResponseContent) {
                    // alert(data.ResponseCode);
                    common.alert(data.ResponseContent);
                }
            }
            if (data.ResponseCode == 'NoPwr' && opt.autologin) {
                window.alert(data.ResponseContent);
                common.gologin();
            }
            if (data.ResponseCode == 'NoSession' && opt.autologin) {
                window.alert(data.ResponseContent);
                common.gologin();
            }
        };
        opt.complete = function () {
            if (opt.mask) layer.close(opt.layerindex);
        };
        opt.error = function (XMLHttpRequest, textStatus, errorThrown) {
            common.alert('数据请求失败！');
        };
        return opt;
    },
    gologin: function () {
        //window.location = '/Portal/Login';
    },
    getprm: function (name) {
        var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
        if (result == null || result.length < 0) return "";
        return result[1];
    },
    getdata: function (obj) {
        var d = {};
        $($(obj).serializeArray()).each(function (i, item) {
            var itemvalue = d[item.name];
            if (itemvalue) d[item.name] = itemvalue + ',' + item.value;
            else d[item.name] = item.value;
        });
        return d;
    },
    setdata: function (obj, data) {
        if (data) {
            var curform = $(obj);
            $.each(data, function (name, value) {
                var formobjs = curform.find('[name="' + name + '"]');
                var type = formobjs.attr("type");
                if (type == "checkbox") formobjs.filter('[value="' + value + '"]').prop("checked", "checked").siblings().removeProp("checked");
                else if (type == "radio") formobjs.filter('[value="' + value + '"]').prop("checked", "checked");
                else formobjs.val(value);
            });
        }
    },

    bindselect: function (obj, data, name, value) {
        obj = $(obj);
        $(data).each(function (i, item) {
            obj.append("<option value='" + item[name] + "'>" + item[value] + "</option>");
        });
    },
    bindinfo: function (a, av, t, url, fun) {
        common.get(url, {InfoType: t}, function (data) {
            common.bindselect(a, data.ResponseContent, 'ID', 'Name');
            if (av) {
                a.val(av);
                if (fun != null) fun();
            }
        });
    },
    bindadr: function (a, b, c, av, bv, cv, url1, url2, url3) {
        a.off('change').find('option:gt(0)').remove();
        b.off('change').find('option:gt(0)').remove();
        c.off('change').find('option:gt(0)').remove();
        a.change(function () {
            b.val('').find('option:gt(0)').remove();
            c.val('').find('option:gt(0)').remove();
            if ($(this).val()) {
                common.get(url2, {ProvinceName: $(this).val()}, function (data) {
                    common.bindselect(b, data.ResponseContent, 'Name', 'Name');
                    if (bv) {
                        b.val(bv);
                        b.change();
                        bv = null;
                    }
                });
            }
        });
        b.change(function () {
            c.val('').find('option:gt(0)').remove();
            if ($(this).val()) {
                common.get(url3, {ProvinceName: a.val(), CityName: $(this).val()}, function (data) {
                    common.bindselect(c, data.ResponseContent, 'Name', 'Name');
                    if (cv) {
                        c.val(cv);
                        cv = null;
                    }
                });
            }
        });
        common.get(url1, null, function (data) {
            common.bindselect(a, data.ResponseContent, 'Name', 'Name');
            if (av) {
                a.val(av);
                a.change();
            }
        });
    },
    serform: function (obj) {
        return common.serjson(common.getdata(obj));
    },
    //序列化对象
    serjson: function (obj) {
        return JSON.stringify(obj);
    },
    //Json反序列化
    dserjson: function (obj) {
        return JSON.parse(obj);
    },
    //初始化回车键
    enterkey: function (obj) {
        var $obj = $(obj);
        $obj.find(':input,textarea').data('data-enterform', obj).keydown(function (event) {
            if (event.keyCode == 13 && this.tagName != 'TEXTAREA') {
                var enterinputs = $($(this).data('data-enterform')).find(':input:enabled:visible,textarea:enabled:visible');
                var index = enterinputs.index($(this));
                enterinputs.eq(index < enterinputs.length - 1 ? index + 1 : 0).focus();
            }
        });
        return $obj;
    },
    load: function (a, b) {
        return layer.load(a || 1, $.extend({shade: [0.5, '#fff']}, b));
    },
    alert: function (a, b, c) {
        return layer.alert(a, $.extend({shade: [0.5, '#fff'], skin: 'layui-layer-molv'}, b), c);
    },
    msg: function (a, b, c) {
        return layer.msg(a, b, c);
    },
    confirm: function (a, b, c, d) {
        return layer.confirm(a, $.extend({shade: [0.5, '#fff'], skin: 'layui-layer-molv', btn: ['确定', '取消']}, b), c, d);
    },
    close: function (i) {
        return layer.close(i);
    },
    open: function (title, url, dataid) {
        $("#openurl").html(title);
        $("#openurl").attr("data-id", dataid);
        $("#openurl").attr("href", url);
        $("#openurl").click();
    }

};


