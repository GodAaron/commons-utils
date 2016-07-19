<%--
  Created by IntelliJ IDEA.
  User: wf2311
  Date: 2016/3/25 0025
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>文件上传测试</title>
    <script src="<%=path%>/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<h1>springMVC字节流输入上传文件</h1>

<form name="userForm1" action="<%=path%>/upload" enctype="multipart/form-data" method="post">
    <div fid="newUpload1">
        <input type="file" name="file">
    </div>

    <input type="button" fid="btn_add1" value="增加一行">
    <input type="submit" value="上传" onclick="show_rs()">
</form>

</body>
<script>
    $(function () {
        var i = 1;
        $('#btn_add1').on('click', function () {
            var $newUploadHtml = '<div fid="div_' + i + '"><input  name="file" type="file"  /><input type="button" value="删除"  onclick="del_2(' + i + ')"/></div>';
            $('#newUpload1').append($newUploadHtml);
            i++;
        });

    });
    function del_2(o) {
        $('#div_' + o).remove();
    }

    function show_rs() {
        var rs = "${rs}";
        if (rs.length > 0 && rs != null) {
            alert(rs + "个文件上传成功");
        }
    }
</script>
</html>
