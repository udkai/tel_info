<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>电话管理系统</title>
    <%@ include file="../common/header.jsp" %>
    <style>
        .overlay-text {
            text-align: center;
            color: white;
        }
    </style>
</head>

<body class="no-skin">
<%@ include file="../common/top.jsp" %>
<div class="main-container" id="main-container">
    <%@ include file="../common/menu.jsp" %>
    <div class="main-content">
        <div class="main-content-inner">
            <!-- #section:basics/content.breadcrumbs -->
            <div class="breadcrumbs  breadcrumbs-fixed" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
                    <li class="active">客户信息管理</li>
                    <li class="active">来源管理</li>
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">

                <!-- PAGE CONTENT BEGINS -->
                <div class="row">
                    <div class="col-xs-12" id="customerInfo">
                        <table id="treeTable" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width=60>序号</th>
                                <th width=60>名单来源</th>
                                <th width=80>导入日期</th>
                                <th width=100>总条数</th>
                                <th width=180>操作</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            <tr>
                                <td>1</td>
                                <td>名单1</td>
                                <td>20180102</td>
                                <td>262</td>
                                <td>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" id="btnCancel">
                                            <i class="glyphicon glyphicon-trash"></i> 全部解除分配
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" id="btnAllot">
                                            <i class="glyphicon glyphicon-trash"></i> 分配显示
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" id="btnDel">
                                            <i class="glyphicon glyphicon-trash"></i> 全部删除
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>名单1</td>
                                <td>20180102</td>
                                <td>262</td>
                                <td>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 全部解除分配
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 分配显示
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 全部删除
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>1</td>
                                <td>名单1</td>
                                <td>20180102</td>
                                <td>262</td>
                                <td>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 全部解除分配
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 分配显示
                                        </button>
                                    </div>
                                    <div class="col-md-4">
                                        <button class="btn btn-primary btn-sm" >
                                            <i class="glyphicon glyphicon-trash"></i> 全部删除
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    </div>

                    <!-- /.span -->
                </div>

                <div class="row">
                    <div class="col-xs-12">${ pageNavigate.pageModel}</div>
                </div>
            </div>
            <!-- /.main-content -->
        </div>
        <div id="student-modal" class="modal fade" tabindex="-21">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header no-padding">
                        <div class="table-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                <span class="white">&times;</span>
                            </button>
                            导入客户资料

                        </div>

                    </div>
                    <div class="modal-body no-padding">
                        <div class="widget-box" style="border: none">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="form-group">
                                        <div class="col-xs-12" style="margin-bottom: 10px">
                                            <span>请下载模板，按照模板格式整理数据：</span>
                                            <a href="<c:url value="/assets/templates/import-customerinfo-template.xls"/>"
                                               target="_blank">导入客户信息模板.xls</a>
                                        </div>
                                    </div>
                                    <c:url value="/customer/customerInfo/importCustomerInfo.htm" var="import_url"/>
                                    <form:form action="${import_url}"
                                               enctype="multipart/form-data" method="post" id="student-upload-form">
                                        <input type="hidden" name="file_type" value="student">
                                        <div class="input-group input-group-lg" style="margin: 0 auto">
                                            <span class="input-group-addon">名单来源说明:</span>
                                            <input type="text" name="resources" id="resources" class="form-control"
                                                   placeholder="名单来源">
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <input name="excel_file" type="file" id="student-import-input"
                                                       accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/>
                                            </div>
                                            <div class="col-xs-12" align="center">
                                                <button class="btn btn-white btn-primary" type="submit">
                                                    <i class="ace-icon fa fa-cloud-upload bigger-110"></i> 确定
                                                </button>
                                                <button class="btn btn-white btn-primary" type="button"
                                                        id="closeicon-modal"
                                                        data-dismiss="modal">
                                                    <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                                                </button>
                                            </div>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer no-margin-top"></div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <div id="import-result-modal" class="modal fade" tabindex="-21">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header no-padding">
                        <div class="table-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                <span class="white">&times;</span>
                            </button>
                            导入结果
                        </div>
                    </div>
                    <div class="modal-body no-padding">
                        <div class="widget-box" style="border: none">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12" id="result-text">
                                        </div>
                                        <div class="col-xs-12" align="center">
                                            <button class="btn btn-white btn-primary" type="button" id="result-btn">
                                                <i class="ace-icon fa fa-check bigger-110"></i> 确认
                                            </button>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer no-margin-top"></div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal-dialog -->
        </div>
        <!-- /.main-container -->
        <%@ include file="../common/js.jsp" %>
        <script src="<c:url value="/assets/js/jquery.form.js"/>"></script>

        <script type="text/javascript">
            $(function () {
                $("#btnDel").bind('click', delModule);
                $("#btnSearch").bind('click', searchModule);
                $("#btnExport").bind('click', exportModule);
                $("#btnArchive").bind('click', ArchiveModule);
                $("#btnUserStatistics").bind('click', userStatisticsModule);
                $("#all_select").change(function () {
                    //全选复选框事件
                    $(":checkbox", $("#treeTable")).prop("checked", $(this).prop("checked"));
                });

                var $student_import_input = $('#student-import-input');
                $student_import_input.ace_file_input({
                    style: 'well',
                    btn_choose: '点击选择Excel文件',
                    btn_change: null,
                    no_icon: 'ace-icon fa fa-file-excel-o',
                    droppable: false,
                    thumbnail: 'small',//large | fit
                    maxSize: 10240000,
                    allowExt: ['xls', 'xlsx', 'xlt', 'xlw', 'xlc', 'xlm'],
                    allowMime: ['application/vnd.ms-excel', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'],
                    before_remove: function () {
                        return true;
                    }

                }).on('change', function () {
                    if ($student_import_input.val() == '') {
                        $student_import_input.ace_file_input('reset_input');
                        return false;
                    }
                }).on('file.error.ace', function (ev, info) {
                    if (info.error_count['ext'] || info.error_count['mime']) {
                        $.notify("请选择Excel文件!");
                        return false;
                    }
                    if (info.error_count['size']) {
                        $.notify("文件不超过100M!");
                        return false;
                    }
                });

                var student_upload_form = $("#student-upload-form");
                //导入提交
                student_upload_form.on('submit', function () {
                    var upload_check = $student_import_input.val();
                    if (upload_check == '') {
                        $student_import_input.ace_file_input('reset_input');
                        $.notify("请选择文件!");
                        return false;
                    }
                    $student_import_input.ace_file_input('loading', true);
                    $(".ace-file-overlay").append('<div class="overlay-text">正在导入中...</div>');
                    student_upload_form.ajaxSubmit({
                        type: 'post', // 提交方式 get/post
                        url: student_upload_form.attr('action'),
                        success: function (result) {
                            var dataObj = eval("(" + result + ")");
                            console.log(dataObj)
                            console.log(dataObj.success)

                            $student_import_input.ace_file_input('loading', false);
                            var msg = '';
                            if (dataObj.success) {
                                $("#student-modal").modal("hide");  //关闭上传窗口
                                $student_import_input.val('');
                                $student_import_input.ace_file_input('reset_input');

                                msg = '导入成功';
                                $("#result-text").html(msg);
                                $("#import-result-modal").modal("show");
//
                                //$.notify(msg);
                            } else {
                                $("#student-modal").modal("hide");   //关闭上传窗口
                                $student_import_input.val('');
                                $student_import_input.ace_file_input('reset_input');
                                $student_import_input.ace_file_input('loading', false);

                                msg = dataObj.msgText + '<br/>' + dataObj.errorInfo;
                                $("#result-text").html(msg);
                                $("#import-result-modal").modal("show");

                            }
                        }
                    });
                    return false;
                });
                $("#result-btn").on('click', function () {
                    $("#import-result-modal").modal("hide");
                    $("#btnSearch").click();
                })
            })
            //业务员统计显示
           var userStatisticsModule=function () {
               var url="";
               $("#customerInfo").hide();
               $("#userStatistics").removeClass("hidden");
           }
            // 查询方法
            var searchModule = function () {
                var url = "index.htm?___=_";

                if ($("#txtName").val() != '')
                    url += "&name=" + $("#txtName").val();

                if ($("#mobile").val() != '')
                    url += "&mobile=" + $("#mobile").val();
                if ($("#status").val() != '')
                    url += "&status=" + $("#status").val();
                if ($("#user_id").val() != '') {
                    url += "&user_id=" + $("#user_id").val();
                }
                if ($("#customer_id_start").val() != '') {
                    url += "&customer_id_start=" + $("#customer_id_start").val();
                }
                if ($("#customer_id_end").val() != '') {
                    url += "&customer_id_end=" + $("#customer_id_end").val();
                }
                if ($("#resources_search").val() != '') {
                    url += "&resources=" + $("#resources_search").val();
                }
                if ($("#customer_status").val() != '') {
                    url += "&customer_status=" + $("#customer_status").val();
                }
                window.location = encodeURI(url);
            }
            //导出
            var exportModule = function () {
                var url = "export.htm?___=_";

                if ($("#txtName").val() != '')
                    url += "&name=" + $("#txtName").val();

                if ($("#mobile").val() != '')
                    url += "&mobile=" + $("#mobile").val();
                if ($("#status").val() != '')
                    url += "&status=" + $("#status").val();
                if ($("#user_id").val() != '') {
                    url += "&user_id=" + $("#user_id").val();
                }
                if ($("#customer_id_start").val() != '') {
                    url += "&customer_id_start=" + $("#customer_id_start").val();
                }
                if ($("#customer_id_end").val() != '') {
                    url += "&customer_id_end=" + $("#customer_id_end").val();
                }
                if ($("#resources_search").val() != '') {
                    url += "&resources=" + $("#resources_search").val();
                }
                if ($("#customer_status").val() != '') {
                    url += "&customer_status=" + $("#customer_status").val();
                }
                window.location = encodeURI(url);
            }
            // 删除
            var delModule = function () {
                var id_start = $("#customer_id_start").val();
                var id_end = $("#customer_id_end").val();
                if (id_start == "" || id_end == "") {
                    bootbox.alert("<h4 class='red'>请输入删除的客户编号范围</h4>");
                    return;
                }
                if (isNaN(id_start) || isNaN(id_end)) {
                    bootbox.alert("<h4 class='red'>请正确输入客户编号</h4>");
                    return;
                }

                bootbox.confirm("<h4 class='red'>你确定要删除" + id_start + "至" + id_end + "的客户信息吗?</h4>", function (result) {
                    if (result) {
                        window.location = "delete.htm?customer_id_start=" + id_start + "&customer_id_end=" + id_end + "&backUrl=${backUrl}";
                    }
                })
            }
            //新增
            var addUser = function (id) {
                window.location = 'toAdd.htm?backUrl=${backUrl }';
            }
            //客户归档
            var ArchiveModule = function () {
                var $trs = $("#tbody").children();
                var customer_id = "";
                for (var i = 0; i < $trs.length; i++) {
                    var $tds = $trs.eq(i).children();
                    if ($tds.eq(0).children().children().eq(0).is(':checked')) {
                        customer_id += $tds.eq(2).html() + ",";
                    }
                }
                if (customer_id == "") {
                    bootbox.alert("请选择归档客户");
                    return;
                }
                var backUrl = "${dynamicServer}/customer/customerInfo/index.htm";
                var url2 = "${dynamicServer}/customer/customerInfo/archive.htm?backUrl=" + backUrl + "&customer_id=" + customer_id;
                window.location = encodeURI(url2);
            }
        </script>
</body>
</html>
