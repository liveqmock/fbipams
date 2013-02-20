<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="pub.platform.security.OperatorManager" %>
<%@ page import="pub.platform.form.config.SystemAttributeNames" %>
<%@ page import="pub.platform.db.ConnectionManager" %>
<%@ page import="pub.platform.db.DatabaseConnection" %>
<%@ page import="pub.platform.db.RecordSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    OperatorManager om = (OperatorManager) session.getAttribute(SystemAttributeNames.USER_INFO_NAME);
    String contextPath = request.getContextPath();

    if (om == null) {
        om = new OperatorManager();
        session.setAttribute(SystemAttributeNames.USER_INFO_NAME, om);
    }

    String username = "";
    String deptname = "";
    String operid = "";

    String rolesall = null;

    if (om != null) {
        if (om.getOperator() != null) {
            username = om.getOperator().getOpername();
            operid = om.getOperator().getOperid();
            if (om.getOperator().getPtDeptBean() != null)
                deptname = om.getOperator().getPtDeptBean().getDeptname();

            //角色
            List roles = new ArrayList();
            DatabaseConnection conn = ConnectionManager.getInstance().get();
            RecordSet rs = conn.executeQuery("select a.roledesc from ptoperrole b right join ptrole a on b.roleid = a.roleid  where b.operid='" + operid + "'");
            while (rs.next()) {
                roles.add(rs.getString("roledesc"));
            }
            ConnectionManager.getInstance().release();
            rolesall = " ";
            for (int i = 0; i < roles.size(); i++) {
                rolesall += roles.get(i) + " ";
            }

        }

    }
%>
<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=gb2312">
    <LINK href="<%=contextPath%>/css/ccb.css" type="text/css" rel="stylesheet">
    <LINK href="<%=contextPath%>/css/diytabbar.css" type="text/css" rel="stylesheet">
    <script type="text/javascript">
        function Relogin() {
            parent.window.reload = "true";
            parent.window.location.replace("<%=contextPath%>/pages/security/logout.jsp");
        }
        function changepwd() {
            var sfeature = "dialogwidth:400px; dialogheight:200px;center:yes;help:no;resizable:no;scroll:no;status:no";
            window.showModalDialog("<%=contextPath%>/UI/system/deptUser/Passwordedit.jsp", "test", sfeature);
        }
        var dividary = new Array('bizlayout', 'syslayout', 'helplayout', 'verlayout');
        function tabbarclk(obj) {
            var active = obj.getAttribute("active");
            if (active == 'false') {
                setclass(obj.getAttribute("id"));
                obj.setAttribute("active", "true");
                obj.setAttribute("className", "tabs-item-active");
                //切换父窗口tree
                parent.document.getElementById("accordItemid").value = obj.getAttribute("id");
                parent.document.getElementById("btnExchangeAccd").click();
            }
        }
        function setclass(activeid) {
            for (var i = 0; i < dividary.length; i++) {
                if (dividary[i] != activeid) {
                    document.getElementById(dividary[i]).setAttribute("className", "tabs-item");
                    document.getElementById(dividary[i]).setAttribute("active", "false");
                }
            }
        }
    </script>

    <style type="text/css">
        html, body {
            background-color: #FFF;
            color: #7387A0;
            margin: 0px;
            padding: 0px;
            overflow: auto;
        }

        b.rtop b, b.rbottom b {
            display: block;
            height: 1px;
            overflow: hidden;
            background: #7387A0
        }
    </style>
</head>
<body leftmargin="0" topmargin="0" rightmargin="0" bottommargin="0" onunload="Relogin()">
<input id="hhidOperatorID" type="hidden" value="<%=operid%>">
<table width="100%" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;">
    <tr width="100%" height="25px">
        <td width="10%" rowspan="2">
            &nbsp;&nbsp;
            <img src="../../images/hfccrm.gif" height="45px">
        </td>
        <td width="30%">
            <img src="../../images/systitle.jpg" height="25px">
        </td>
        <td style="height:25px;text-align:right">
            <span>您好,<%=username%>! </span>
            <span onclick="changepwd()"
                  onMouseOver="this.style.cursor='hand'">|&nbsp;&nbsp;修改密码</span>
             <span onclick="Relogin()"
                   onMouseOver="this.style.cursor='hand'">|  退出 &nbsp;&nbsp;</span>
        </td>
    </tr>
    <tr height="25px">
        <td colspan="2" width="100%" style="height: 100%;">
            <div onclick="tabbarclk(this);" active="true" id="bizlayout" class="tabs-item-active"
                 style="float:left;width:80px;">
                <span style="width:100%;">业务管理</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="syslayout" class="tabs-item"
                 style="float:left;width:80px;">
                <span style="width:100%;">系统管理</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="helplayout" class="tabs-item"
                 style="float:left;width:80px;">
                <span style="width:100%;">系统帮助</span>
            </div>
            <div style="float:left;width:2px;"></div>
            <div onclick="tabbarclk(this);" active="false" id="verlayout" class="tabs-item"
                 style="float:left;width:80px;">
                <span style="width:100%;">版本控制</span>
            </div>
            <%--<%=" " + deptname + " | " + operid + " | <" + rolesall + ">" %>--%>
        </td>
    </tr>
    <tr>
        <td width="100%" style="height:4px;background-color: #3169AD;" colspan="3"></td>
    </tr>
</table>

</body>
</html>