<%--
  Created by IntelliJ IDEA.
  User: cheng
  Date: 2017/3/22 0022
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
<h1>客户列表</h1>
<table>
    <th>
        <td>名称</td>
        <td>联系人</td>
        <td>电话号码</td>
        <td>邮箱地址</td>
        <td>操作</td>
    </th>
    <c:forEach items="${customerList}" var="cus">
        <tr>
            <td>${cus.name}</td>
            <td>${cus.contact}</td>
            <td>${cus.telephone}</td>
            <td>${cus.email}</td>
            <td>
                <a href="${BASE}/customer_edit?id=${cus.id}">编辑</a>
                <a href="${BASE}/customer_delete?id=${cus.id}">编辑</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
