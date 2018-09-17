//检查input字数
var checkLength = function ($v,limit) {
    if($v.val().length>limit) $v.addClass("error");
    else $v.removeClass("error");
};
var checkNum = function ($v) {
    var reg = /^\d+(?=\.{0,1}\d+$|$)/;
    if(reg.test($v.val())){
        $v.removeClass("error");
        $v.removeAttr("style");
    }else{
        $v.addClass("error");
        $v.attr("style","border-color:#ff7870");
    }
};
String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
}
$(function() {
    //初始化编辑器1
    var editor = UE.getEditor('editor', {
        toolbars: [
            ['fontsize', 'fontfamily', 'forecolor', 'bold', 'italic', 'underline', 'fullscreen', 'simpleupload', 'link']
        ],
        enableAutoSave: false, //禁止自动保存
        autoSyncData: false,//自动同步编辑器要提交的数据
        elementPathEnabled: false,//是否启用元素路径
        wordCount: false,          //是否开启字数统计
        autoHeightEnabled: false
    });
    //初始化编辑器2
    var editor2 = UE.getEditor('editor2', {
        toolbars: [
            ['fontsize', 'fontfamily', 'forecolor', 'bold', 'italic', 'underline', 'fullscreen', 'undo', 'redo', 'simpleupload', 'link']
        ],
        enableAutoSave: false, //禁止自动保存
        autoSyncData: false,//自动同步编辑器要提交的数据
        elementPathEnabled: false,//是否启用元素路径
        wordCount: false,          //是否开启字数统计
        autoHeightEnabled: false
    });
    //延迟加载
    setTimeout(function () {
        //富文本编辑完后返回页面事件
        editor.addListener('fullscreenchanged', function (event,isFullScreen) {
            if(!isFullScreen){
                $(".editor").removeClass("hide").addClass("hide");
                setTimeout(function () {
                    editorFullScreen(1);
                },100);
            }
        });
        //富文本编辑完后返回页面事件
        editor2.addListener('fullscreenchanged', function (event,isFullScreen) {
            if(!isFullScreen){
                $(".editor").removeClass("hide").addClass("hide");
                setTimeout(function () {
                    editorFullScreen(2);
                },100);
            }
        });
        //点击编辑器全屏事件
        var editorFullScreen = function (type) {
            if (type == "1"){
                $("#content1").text(subs(editor.getContentTxt()));
            } else{
                $("#content2").text(subs(editor2.getContentTxt()));
            }
            $(".ueditor-box").removeClass("error");
            $(".ueditor-box").unbind("click").bind("click",(function () {
                $(this).unbind("click");
                $(this).children(".editor").removeClass("hide");
                $(this).find("div[title='全屏']").click();
            }));
        };
        var subs = function (str) {
            if(str.length>15) return str.substr(0,12) + "......";
            else return str;
        };

        editorFullScreen();

        //展开选择项
        $(".choose-box").bind("click", function () {
            $(this).removeClass("error");
            $(this).toggleClass("open");
            $(this).next(".check-show").toggleClass("hide");
        });

        //点击选择项
        $(".check-node span").click(function () {
            var $parent = $($(this).parents(".wd_line")[0]);
            var isMultiple = $parent.attr("isMultiple");
            if (isMultiple == '1'){
                var isCheck = $(this).hasClass("check");
                var content = $parent.find(".choose-content").text();
                if (isCheck){
                    $(this).removeClass("check");
                }else{
                    $(this).addClass("check");
                }
                var nodes = $parent.find(".check-node");
                var val = "";var name1 = "";var index = 0;
                $.each(nodes, function (name,value) {
                    var span = $(this).children("span");
                    if(span.hasClass("check")){
                        index++;
                        if(index < 3){
                            name1 = name1 + "," + span.attr("name");
                        }else if(index == 3){
                            name1 = name1 + "...";
                        }
                        val = val + "," + span.attr("val");
                    }
                });
                $parent.find(".choose-content").text(name1!=""?name1.substr(1,name1.length):"请选择");
                $parent.children("input").val(val.substr(1,val.length));
            }else{
                var isCheck = $(this).hasClass("check");
                var sf = $(this).attr("flag");
                if(isCheck){
                    $(this).removeClass("check");
                    if(sf=="sf"){
                        $(".price-div").addClass("hide");
                        $(".price-div").children("input").val("");
                    }
                    $parent.find(".choose-content").text("请选择");
                    $parent.children("input").val($(this).attr(""));
                }else{
                    var nodes = $parent.find(".check-node");
                    $.each(nodes, function (name,value) {
                        $(this).children("span").removeClass("check");
                    });
                    $(this).addClass("check");
                    if(sf=="sf"){
                        $(".price-div").removeClass("hide");
                        $(".price-div").children("input").focus();
                    }else if(sf=="mf"){
                        $(".price-div").addClass("hide");
                        $(".price-div").children("input").val("");
                    }
                    $parent.find(".choose-content").text($(this).attr("name"));
                    $parent.children("input").val($(this).attr("val"));
                }
            }
        });
        //防止输入框被软键盘挡住
        $(".wd_line[name='zy'],.wd_line[name='tzyj'],.wd_line[name='gdly']").click(function () {
           this.scrollIntoView();
        });
        //保存事件
        $("#save").click(function () {
            var params = getParams();
            if(!validParams(params)) return;
            $.ajax({
                url: "/app/saveView",
                dataType: "json",
                data: params,
                type: 'POST',
                success: function (ret) {
                    if(ret["O_CODE"]==1 || ret["code"]==1){
                        $("#save").removeClass("bottom_btn");
                        $("#save").addClass("bottom_c");
                        $("#save").val("已   保   存");
                        $("#save").unbind("click");
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        });
        var gdid = "";
        //获取输入参数
        var getParams = function () {
            var params = {};
            params["gdid"] = gdid;
            params["bt"] = $("#bt").val();
            params["gknr"] = editor.getContent();
            params["smnr"] = editor2.getContent();
            params["gdly"] = $("#gdly").val();
            params["tzyj"] = $("#tzyj").val();
            params["zy"] = $("#zy").val();
            params["jg"] = $("#jg").val();
            $(".choose-div").each(function () {
                var name = $(this).attr("name");
                var value = $(this).children("input").val();
                params[""+name+""] = value;
            });
            return params;
        };
        //验证参数
        var validParams = function (params) {
            var reg = /^\d+(?=\.{0,1}\d+$|$)/;
            if(params["bt"]=="" || params["bt"]==null || params["bt"].length>30){
                $(".wd_line[name='bt']").children("input").addClass("error").focus();
                $(".wd_line[name='bt']")[0].scrollIntoView();
                return false;
            }
            if(params["lx"]=="" || params["lx"]==null){
                $(".wd_line[name='lx']").children(".choose-box").addClass("error");
                $(".wd_line[name='lx']")[0].scrollIntoView();
                return false;
            }
            if(params["gknr"]=="" || params["gknr"]==null){
                $(".wd_line[name='gknr']").children(".ueditor-box").addClass("error");
                $(".wd_line[name='gknr']")[0].scrollIntoView();
                return false;
            }
            if(params["fxdj"]=="" || params["fxdj"]==null){
                $(".wd_line[name='fxdj']").children(".choose-box").addClass("error");
                $(".wd_line[name='fxdj']")[0].scrollIntoView();
                return false;
            }
            if(params["sfsf"]=="" || params["sfsf"]==null){
                $(".wd_line[name='sfsf']").children(".choose-box").addClass("error");
                $(".wd_line[name='sfsf']")[0].scrollIntoView();
                return false;
            }
            if(params["sfsf"]=="1" && (params["jg"]=="" || params["jg"]==null || !reg.test(params["jg"]))){
                $(".wd_line[name='sfsf']").children(".check-show").removeClass("hide");
                $(".wd_line[name='sfsf']")[0].scrollIntoView();
                $("#jg").addClass("error");
                $("#jg").attr("style","border-color:#ff7870");
                return false;
            }
            if(params["gdly"].length>10){
                $(".wd_line[name='gdly']").children("input").addClass("error");
                $(".wd_line[name='gdly']")[0].scrollIntoView();
                return false;
            }
            if(params["zy"].length>40){
                $(".wd_line[name='zy']").children("input").addClass("error");
                $(".wd_line[name='zy']")[0].scrollIntoView();
                return false;
            }
            if(params["gdbq"]=="" || params["gdbq"]==null){
                $(".wd_line[name='gdbq']").children(".choose-box").addClass("error");
                $(".wd_line[name='gdbq']")[0].scrollIntoView();
                return false;
            }
            return true;
        };
        var init = function () {
            var str = $("#hidden").val();
            if (str!=""){
                var json = jQuery.parseJSON(str);
                gdid = json.VIEWPOINT_ID;
                $("#bt").val(json.TITLE);
                $(".wd_line[name='lx']").find("span[val="+json.VIEWPOINT_TYPE+"]").click();
                editor.setContent(json.OPEN_CONTENT);
                $("#content1").text(subs(editor.getContentTxt()));
                editor2.setContent(json.CLOSE_CONTENT);
                $("#content2").text(subs(editor2.getContentTxt()));
                $(".wd_line[name='fxdj']").find("span[val="+json.RISK_LEVEL+"]").click();
                if(json.IS_CHARGEABLE=="1"){
                    $(".wd_line[name='sfsf']").find("span[val=1]").click();
                }
                $("#jg").val(json.CHARGE_AMT);
                $("#gdly").val(json.SOURCE);
                $("#tzyj").val(json.BASIS);
                $("#zy").val(json.ABSTRACT);
                var labels = json.LABEL_ID.split(",");
                for (var x in labels){
                    $(".wd_line[name='gdbq']").find("span[val="+labels[x]+"]").click();
                }
            }
        };
        init();
    },500);
});