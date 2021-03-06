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
                    <li class="active">统计分析</li>
                    <li class="active">业务统计</li>
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
                                            <td>起始时间：</td>
                                            <td><input type="text" id="txtStartTime"
                                                       class="form-control input-small" placeholder=""
                                                       value="${statisticsSearchVO.startTime }"></td>
                                            <td>结束时间：</td>
                                            <td><input type="text" id="txtEndTime"
                                                       class="form-control input-small" placeholder=""
                                                       value="${statisticsSearchVO.endTime }"></td>
                                            <td>业务员：</td>
                                            <td>
                                                <select id="user_id" name="user_id" class="width-150" style="width: 100px">
                                                    <option value="">请选择</option>
                                                    <c:forEach items="${userList}" var="user">
                                                        <option value="${user.id}"
                                                                <c:if test="${user.id==statisticsSearchVO.user_id}">selected</c:if>>${user.realname}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnSearch">
                                                    <i class="ace-icon fa fa-search"></i> 生成统计
                                                </button>
                                            </td>
                                            <td>
                                                <button class="btn btn-primary btn-sm" id="btnDownload">
                                                    <i class="ace-icon fa fa-search"></i> 下载报表
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
                                <th width=80>序号</th>
                                <th width=120>业务员</th>
                                <th width=120>名单总数</th>
                                <th width=100>尚未分配名单</th>
                                <th width=100>已经分配名单</th>
                                <th width=100>已经呼叫客户</th>
                                <th width=160>剩余名单</th>
                                <th width=80>空号</th>
                                <th width=80>拒接</th>
                                <th width=160>无人接听</th>
                                <th width="200">尝试家微信</th>
                                <th width="200">加微信通过</th>
                                <th width="160">已经邀约</th>
                                <th width="120">不需要</th>
                            </tr>
                            </thead>
                            <tbody id="tbody">
                            <c:forEach items="${businessList }" var="business" varStatus="st">
                                <c:if test="${businessList.size()>1}">
                                <tr>
                                    <td width=40>${st.index+1}</td>
                                    <td>${business.user_name}</td>
                                    <td><c:if test="${st.index==0}">${total}</c:if></td>
                                    <td><c:if test="${st.index==0}">${notAlloted}</c:if></td>
                                    <td>${business.alloted}</td>
                                    <td>${business.called}</td>
                                    <td>${business.status0}</td>
                                    <td>${business.status1}</td>
                                    <td>${business.status2}</td>
                                    <td>${business.status3}</td>
                                    <td>${business.status4}</td>
                                    <td>${business.status5}</td>
                                    <td>${business.status6}</td>
                                    <td>${business.status7}</td>
                                </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.span -->
                </div>

                <c:if test="${businessList.size()>1}">
                    <div class="row">
                        <div class="col-xs-12">${ pageNavigate.pageModel}</div>
                    </div>
                </c:if>
            </div>
            <!-- /.main-content -->
        </div>
    </div>
        <!-- /.main-container -->
        <%@ include file="../../common/js.jsp" %>
        <script src="<c:url value="/assets/js/jquery.form.js"/>"></script>
        <script src="<c:url value="/assets/components/bootstrap-datepicker/js/bootstrap-datepicker.js"/>"></script>
        <script src="<c:url value="/assets/components/bootstrap-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"/>"></script>

        <script type="text/javascript">
            $(function () {

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
                $("#btnSearch").bind('click', searchModule);
                $("#btnDownload").bind('click', downloadModule);
            })

            //下载报表
            var downloadModule=function () {
                var url = "export.htm?___=_";
                if ($("#txtStartTime").val() != '')
                    url += "&startTime=" + $("#txtStartTime").val();
                if($("#txtStartTime").val()!=''){
                    url+="&endTime="+$("#txtEndTime").val();
                }
                if($("#user_id").val()!=''){
                    url+="&user_id="+$("#user_id").val();
                }
                window.location = encodeURI(url);
            }
            // 查询方法
            var searchModule = function () {
                var url = "index.htm?___=_";

                if ($("#txtStartTime").val() != '')
                    url += "&startTime=" + $("#txtStartTime").val();
                if($("#txtStartTime").val()!=''){
                    url+="&endTime="+$("#txtEndTime").val();
                }
                if($("#user_id").val()!=''){
                    url+="&user_id="+$("#user_id").val();
                }

                window.location = encodeURI(url);
            }


        </script>
</body>
</html>
