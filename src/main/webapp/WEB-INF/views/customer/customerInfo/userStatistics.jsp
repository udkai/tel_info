<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>电话管理系统</title>
    <%@ include file="../../common/header.jsp" %>
    <style>
        .overlay-text {
            text-align: center;
            color: white;
        }
    </style>
</head>

<body class="no-skin">
<%@ include file="../../common/top.jsp" %>
<div class="main-container" id="main-container">
    <%@ include file="../../common/menu.jsp" %>
    <div class="main-content">
        <div class="main-content-inner">
            <!-- #section:basics/content.breadcrumbs -->
            <div class="breadcrumbs  breadcrumbs-fixed" id="breadcrumbs">
                <ul class="breadcrumb">
                    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a></li>
                    <li class="active">客户信息管理</li>
                    <li class="active">客户信息查询</li>
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <!-- /section:basics/content.breadcrumbs -->
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="widget-box widget-color-blue">
                            <!-- #section:custom/widget-box.options -->
                            <div class="widget-header">
                                <h5 class="widget-title bigger lighter">
                                    <i class="ace-icon fa fa-table"></i> 操作面板
                                </h5>
                            </div>

                            <!-- /section:custom/widget-box.options -->
                            <div class="widget-body">
                                <div class="widget-main">
                                    <table class="searchField" style="margin: 4px; padding: 4px;">
                                        <tr>
                                            <td>姓名</td>
                                            <td><input type="text" id="txtName" maxlength="15"
                                                       class="form-control  "
                                                       placeholder=""
                                                       value="${customerInfoSearchVO.name }"></td>
                                            <td>手机号</td>
                                            <td><input type="text" id="mobile" maxlength="13"
                                                       class="form-control  "
                                                       placeholder=""
                                                       value="${customerInfoSearchVO.mobile }"></td>
                                            <td>分配状态</td>
                                            <td>
                                                <select id="status" value="${customerInfoSearchVO.status}"
                                                        class="width-100">
                                                    <option value=" ">全部</option>
                                                    <option value="1"
                                                            <c:if test="${customerInfoSearchVO.status==1}">selected</c:if>>
                                                        已分配
                                                    </option>
                                                    <option value="0"
                                                            <c:if test="${customerInfoSearchVO.status==0}">selected</c:if>>
                                                        未分配
                                                    </option>
                                                </select>
                                            </td>
                                            <td>业务员</td>
                                            <td>
                                                <select id="user_id" name="user_id" class="width-100">
                                                    <option value="">请选择</option>
                                                    <c:forEach items="${userList}" var="user">
                                                        <option value="${user.id}"
                                                                <c:if test="${user.id==customerInfoSearchVO.user_id}">selected</c:if>>${user.realname}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>


                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnExport">
                                                    导出
                                                </button>
                                            </td>
                                            <td>
                                                <a href="#student-modal" class="btn btn-primary btn-sm"
                                                   data-backdrop="static"
                                                   data-toggle="modal">
                                                    导入</a>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>客户编号</td>
                                            <td><input type="text" id="customer_id_start"
                                                       class="form-control  "
                                                       placeholder="" maxlength="8"
                                                       value="${customerInfoSearchVO.customer_id_start }"></td>
                                            <td> &nbsp&nbsp&nbsp&nbsp;至</td>
                                            <td><input type="text" id="customer_id_end" class="form-control "
                                                       placeholder="" maxlength="8"
                                                       value="${customerInfoSearchVO.customer_id_end }"></td>

                                            <td>客户状态</td>
                                            <td>
                                                <select id="customer_status" name="customer_status" class="width-100">
                                                    <option value=" "> 全部</option>
                                                    <option value="0" <c:if test="${customerInfoSearchVO.customer_status==0}">selected</c:if>>为空</option>
                                                    <option value="1" <c:if test="${customerInfoSearchVO.customer_status==1}">selected</c:if>>空号</option>
                                                    <option value="2" <c:if test="${customerInfoSearchVO.customer_status==2}">selected</c:if>>拒接</option>
                                                    <option value="3" <c:if test="${customerInfoSearchVO.customer_status==3}">selected</c:if>>无人接听</option>
                                                    <option value="4" <c:if test="${customerInfoSearchVO.customer_status==4}">selected</c:if>>尝试加微信</option>
                                                    <option value="5" <c:if test="${customerInfoSearchVO.customer_status==5}">selected</c:if>>加微信通过</option>
                                                    <option value="6" <c:if test="${customerInfoSearchVO.customer_status==6}">selected</c:if>>已经邀约</option>
                                                    <option value="7" <c:if test="${customerInfoSearchVO.customer_status==7}">selected</c:if>>不需要</option>
                                                </select>
                                            </td>
                                            <td>名单来源</td>
                                            <td><input type="text" id="resources_search"
                                                       class="form-control  "
                                                       placeholder="" maxlength="8"
                                                       value="${customerInfoSearchVO.resources }"></td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnSearch">
                                                    查询
                                                </button>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnDel">
                                                    删除
                                                </button>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td>分配时间起</td>
                                            <td><input type="text" id="allot_at_start"
                                                       class="form-control " placeholder=""
                                                       value="${customerInfoSearchVO.allot_at_start }"></td>
                                            <td>分配时间止</td>
                                            <td><input type="text" id="allot_at_end"
                                                       class="form-control " placeholder=""
                                                       value="${customerInfoSearchVO.allot_at_end }"></td>
                                            <td>操作时间起</td>
                                            <td><input type="text" id="operate_at_start"
                                                       class="form-control " placeholder=""
                                                       value="${customerInfoSearchVO.operate_at_start }"></td>
                                            <td>操作时间止</td>
                                            <td><input type="text" id="operate_at_end"
                                                       class="form-control " placeholder=""
                                                       value="${customerInfoSearchVO.operate_at_end }"></td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnUserStatistics">
                                                    业务员统计显示
                                                </button>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnArchive">
                                                    客户归档
                                                </button>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- PAGE CONTENT BEGINS -->
                <div class="row">
                    <!-- 业务员统计显示-->
                    <div class="col-xs-12 " id="userStatistics">
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width=10>序号</th>
                                <th width=60>业务员</th>
                                <th width=60>分配情况</th>
                                <th width="241">操作</th>
                            </tr>
                            </thead>
                            <tbody id="tbody1">
                            <c:forEach items="${list }" var="userSection" varStatus="st">
                                <tr>
                                    <td width=40>${st.index+1}</td>
                                    <td>${userSection.user_name}</td>
                                    <td>${userSection.id_section}</td>
                                    <td>
                                        <div class="col-md-3">
                                            <button class="btn btn-primary btn-sm" id="btnCancel" onclick="relieve(this)">
                                                 解除分配
                                            </button>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="col-md-4">
                                            <span >转分配给</span>
                                            </div>
                                            <div class="col-md-8">
                                            <select  name="user_id" class="width-100 ">
                                                <option value="">请选择</option>
                                                <c:forEach items="${userList}" var="user">
                                                    <option value="${user.id}" >${user.realname}</option>
                                                </c:forEach>
                                            </select>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <button class="btn btn-primary btn-sm" id="btnAllot" onclick="allot(this)">
                                                分配
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
        <%@ include file="../../common/js.jsp" %>
        <script src="<c:url value="/assets/js/jquery.form.js"/>"></script>
        <script src="<c:url value="/assets/components/bootstrap-datepicker/js/bootstrap-datepicker.js"/>"></script>
        <script src="<c:url value="/assets/components/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"/>"></script>

        <script type="text/javascript">
            $(function () {
                $("#btnDel").bind('click', delModule);
                $("#btnSearch").bind('click', searchModule);
                $("#btnExport").bind('click', exportModule);
                $("#btnArchive").bind('click', ArchiveModule);
                $("#btnUserStatistics").bind('click', userStatisticsModule);//业务员统计显示

                $("#all_select").change(function () {
                    //全选复选框事件
                    $(":checkbox", $("#treeTable")).prop("checked", $(this).prop("checked"));
                });

                $('#txtStartTime').datepicker({
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayHighlight: true,
                    language: "zh-CN",
                    orientation: "bottom auto"
                });
                $('#txtEndTime').datepicker({
                    format: "yyyy-mm-dd",
                    autoclose: true,
                    todayHighlight: true,
                    language: "zh-CN",
                    orientation: "bottom auto"
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
            /**
             * 解除分配该号段
             * @param btn
             */
            var relieve=function(btn){
                var idSection=$(btn).parent().parent().prev().html();
                var backurl="${dynamicServer}/customer/customerInfo/listNumberSection.htm";
                var url="relieve.htm?&idSection="+idSection+"&backUrl="+backurl;
                bootbox.confirm("您确定要解除分配吗？", function (result) {
                    if (result) {
                        window.location = encodeURI(url);
                    }
                })
            }
            /**
             * 分配该号段给业务员
             * @param btn
             */
            var allot=function(btn){
                var idSection=$(btn).parent().parent().prev().html();
                var userId=$(btn).parent().prev().find("select").val();
                var userName=$(btn).parent().prev().find("select").find("option:selected").html();
                var backurl="${dynamicServer}/customer/customerInfo/listNumberSection.htm";
                var url="allot.htm?&idSection="+idSection+"&userId="+userId+"&userName="+userName+"&backUrl="+backurl;
                window.location = encodeURI(url);
            }
            //业务员统计显示
            var userStatisticsModule = function () {
                var url = "listNumberSection.htm";
                window.location = encodeURI(url);
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
                if ($("#txtStartTime").val() != '') {
                    url += "&start_time=" + $("#txtStartTime").val();
                }
                if ($("#txtEndTime").val() != '') {
                    url += "&end_time=" + $("#txtEndTime").val();
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
                if ($("#txtStartTime").val() != '') {
                    url += "&start_time=" + $("#txtStartTime").val();
                }
                if ($("#txtEndTime").val() != '') {
                    url += "&end_time=" + $("#txtEndTime").val();
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
