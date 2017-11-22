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
                    <li class="active">资料分配</li>
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
                                            <td>客户编号：</td>
                                            <td><input type="text" id="customer_id_start"
                                                       class="form-control input-small"
                                                       placeholder="" maxlength="8"
                                                       value="${customerAllotSearchVO.customer_id_start }"></td>
                                            <td>至：</td>
                                            <td><input type="text" id="customer_id_end" class="form-control input-small"
                                                       style="width:100px;"
                                                       placeholder="" maxlength="8"
                                                       value="${customerAllotSearchVO.customer_id_end }"></td>
                                            <td>分配状态：</td>
                                            <td>
                                                <select id="status" value="${customerAllotSearchVO.status}">
                                                    <option value=" ">全部</option>
                                                    <option value="1"
                                                            <c:if test="${customerAllotSearchVO.status==1}">selected</c:if>>
                                                        已分配
                                                    </option>
                                                    <option value="0"
                                                            <c:if test="${customerAllotSearchVO.status==0}">selected</c:if>>
                                                        未分配
                                                    </option>
                                                </select>
                                            </td>
                                            <td>业务员：</td>
                                            <td>
                                                <select id="user_id_search">
                                                    <option value="">请选择</option>
                                                    <c:forEach items="${userList}" var="user">
                                                        <option value="${user.id}"
                                                                <c:if test="${user.id==customerAllotSearchVO.user_id}">selected</c:if>>${user.realname}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>

                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnSearch">
                                                    <i class="ace-icon fa fa-search"></i> 查询
                                                </button>
                                                <%-- <c:if test="${bln:isP('CustomerInfoAdd')}">
                                                     <button type="button" class="btn btn-success btn-sm" id="btnAdd">
                                                         <i class="ace-icon fa fa-plus bigger-110"></i>新增
                                                     </button>
                                                 </c:if>--%>
                                            </td>
                                            <td>分配给：</td>
                                            <td id="choose">
                                                <select id="user_id">
                                                    <option>请选择</option>
                                                    <c:forEach items="${userList}" var="list">
                                                        <option value=" ${list.id}">${list.realname}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnAllot">
                                                    <i class="ace-icon fa fa-search"></i> 分发
                                                </button>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnCancel">
                                                    <i class="ace-icon fa fa-search"></i> 取消分发
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
                    <div class="col-xs-12">
                        <table id="treeTable" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <%--<th width=60>--%>
                                <%--<label> <input name="module" class="ace father" type="checkbox" id="all_select"/>--%>
                                <%--<span class="lbl"></span>--%>
                                <%--</label>--%>
                                <%--</th>--%>
                                <th width=60>序号</th>
                                <th width=60>客户编号</th>
                                <th width=80>姓名</th>
                                <th width=180>电话号码</th>
                                <th width=180>备注</th>
                                <th width=180>所属业务员编号</th>
                                <th width=180>所属业务员姓名</th>
                                <th width=100>归档状态</th>
                                <th width=100>分发状态</th>
                                <th width="241">操作时间</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            <c:forEach items="${customerInfoList }" var="customer" varStatus="st">
                                <tr>
                                        <%--<td>--%>
                                        <%--<label>--%>
                                        <%--<input name="module" class="ace father" type="checkbox" />--%>
                                        <%--<span class="lbl"></span>--%>
                                        <%--</label>--%>
                                        <%--</td>--%>
                                    <td>${st.index+1}</td>
                                    <td>${customer.id}</td>
                                    <td>${customer.name}</td>
                                    <td>${customer.mobile}</td>
                                    <td>${customer.remark}</td>
                                    <td>${customer.user_id}</td>
                                    <td>${customer.user_name}</td>
                                    <td><c:if test="${customer.archive_status==0}">未归档</c:if>
                                        <c:if test="${customer.archive_status==1}">已归档</c:if>
                                    </td>
                                    <td><c:if test="${customer.status==0}"><span style="color: red;">未分配</span></c:if>
                                        <c:if test="${customer.status==1}">已分配</c:if>
                                    </td>
                                    <td><fmt:formatDate value="${customer.last_modify_at}"
                                                        pattern="yyyy-MM-dd HH:mm:ss"/></td>
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

        <!-- /.main-container -->
        <%@ include file="../../common/js.jsp" %>
        <script src="<c:url value="/assets/js/jquery.form.js"/>"></script>

        <script type="text/javascript">
            $(function () {
                $("#btnSearch").bind('click', searchModule);
                $("#btnAllot").bind('click', allotCustomer);
                $("#btnCancel").bind('click', cancelAllot);
//                $("#all_select").change(function () {
//                    //全选复选框事件
//                    $(":checkbox", $("#treeTable")).prop("checked", $(this).prop("checked"));
//                });
            })
            //取消分发
            var cancelAllot = function () {
//                var $trs = $("#tbody").children();
//                var customer_id = "";
//                for (var i = 0; i < $trs.length; i++) {
//                    var $tds = $trs.eq(i).children();
//                    if ($tds.eq(0).children().children().eq(0).is(':checked')) {
//                        customer_id += $tds.eq(1).html() + ',';
//                    }
//                }
//                if(customer_id==""){
//                    bootbox.alert("请选择取消分发的客户资料");
//                    return;
//                }
                var user_id = $("#user_id").val();
                var customer_id_start = $("#customer_id_start").val().trim();
                var customer_id_end = $("#customer_id_end").val().trim();
                if (customer_id_start == "" || customer_id_end == "") {
                    bootbox.alert("请填写客户编号段");
                    return;
                }

                if (isNaN(customer_id_start) || isNaN(customer_id_end)) {
                    bootbox.alert("请正确填写客户编号");
                    return;
                }
                var backUrl = "${dynamicServer}/customer/allot/index.htm";
                var url2 = "${dynamicServer}/customer/allot/saveCancel.htm?backUrl=" + backUrl + "&customer_id_start=" + customer_id_start + "&customer_id_end=" + customer_id_end;
                bootbox.confirm("您确定要取消分发吗？", function (result) {
                    if (result) {
                        window.location = encodeURI(url2);
                    }
                })
            }
            //分发
            var allotCustomer = function () {
                var user_id = $("#user_id").val();
                var customer_id_start = $("#customer_id_start").val();
                var customer_id_end = $("#customer_id_end").val();
                if (customer_id_start == "" || customer_id_end == "") {
                    bootbox.alert("请填写客户编号段");
                    return;
                }

                if (isNaN(customer_id_start) || isNaN(customer_id_end)) {
                    bootbox.alert("请正确填写客户编号");
                    return;
                }
                if ($("#user_id").val() == "请选择") {
                    $("#choose").css("border", "2px solid #ee7700");
                    return;
                }

                var username = $("#user_id option:selected").text();
                var backUrl = "${dynamicServer}/customer/allot/index.htm";
                var url = "${dynamicServer}/customer/allot/saveAllot.htm?backUrl=" + backUrl + "&customer_id_start=" + customer_id_start + "&customer_id_end=" + customer_id_end + "&user_id=" + user_id + "&username=" + username;
                window.location = encodeURI(url);
            }
            // 查询方法
            var searchModule = function () {

                var url = "index.htm?___=_";

                if ($("#customer_id_start").val() != '')
                    url += "&customer_id_start=" + $("#customer_id_start").val();

                if ($("#customer_id_end").val() != '')
                    url += "&customer_id_end=" + $("#customer_id_end").val();
                if ($("#status").val() != '')
                    url += "&status=" + $("#status").val();
                if ($("#user_id_search").val() != '') {
                    url += "&user_id=" + $("#user_id_search").val();
                }
                window.location = encodeURI(url);
            }

        </script>
</body>
</html>
