<html>
<head>
    <title>View Appsnaps</title>
    <meta name="pageID" content="td-Page"/>
    <content tag="pagetitle">View Appsnaps</content>
    <content tag="pageID">viewappsnap-Page</content>
    <content tag="pagehelp"><h4>View Appsnap</h4></content>
    <link rel="stylesheet" type="text/css"
          href="${request.contextPath!}/plugins/appsnapplugin/resources/styles/dataurl.css"/>
<@resource.javascript file="/plugins/appsnapplugin/resources/script/appsnappace-min.js"/>
<@resource.javascript output="true"/>
    <script type="text/javascript">

        function deleteAppsnap(fileName) {
            var msg = "Are you sure you want to delete " + fileName + "?";

            if (confirm(msg)) {

                document.getElementById('appsnap-delete-form-' + fileName + '').submit();

            }
            return false;
        }

    </script>

</head>

<body>
<#if filelist?has_content>
<div id="appsnap-table">
    <div id="jive-success-box" class="jive-success-box" style="width:300px" aria-live="polite" aria-atomic="true">
        <span class="jive-icon-med jive-icon-check"></span>
        The webapp appsnap was captured successfully.
    </div>
    <script type="text/javascript">
        $j('#jive-success-box').fadeIn(1000).delay(10000).fadeOut();
    </script>

    <div class="jive-table">

        <table border="0" cellpadding="3" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th align="center"> File ID</th>
                <th align="center"> File Name</th>
                <th align="center">Description</th>
                <th style="padding-left:2px; padding-right:2px;text-align: center">Actions</th>
            </tr>
            </thead>
            <tbody>

                <#list filelist as flist>
                <tr>
                    <td>${flist_index+1}</td>
                    <td width="30%"
                    "><a href="/plugins/appsnapplugin/appsnaps/${flist}">${flist}</a></td>

                    <td width="20%"> <#if flist_index+1=1><span class="jive-icon-sml jive-icon-check"></span>This is the
                        latest file</span><#else>Backup file</#if></td>
                    <td nowrap style="padding-left:2px; padding-right:2px;" width="40%" align="center">
                        &nbsp;&nbsp;
                        <a href="/plugins/appsnapplugin/appsnaps/${flist}"><img
                                src="../images/arrow-down-blue-16x16.gif" border="0" alt="Download Appsnap"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <a href="#" onclick="return deleteAppsnap('${flist?html}')" alt="delete"><img
                                src="<@s.url value='/admin/images/delete-16x16.gif' />" alt="" border="0"/></a>

                        <form id="appsnap-delete-form-${flist?html}" name="appsnap-delete-form-${flist?html}"
                              action="<@s.url action='viewappsnap' method='delete' />" method="post">
                            <input type="hidden" name="fileName" value="${flist}"/>
                        </form>
                    </td>

                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</div>
<p>   <@s.text name="plugin.appsnap.admin.view.backup"/> ${tidyHouseProperty} days. </p>
<#else>
<p><b>No appsnap exist on this node. Click <a href="<@s.url value='grabjvmappsnap.jspa'/>">here</a> to capture a new appsnap</p>
</#if>


</body>
</html>