<#assign pageTitle = "View Previous Appsnaps">
<#assign appsnapDir = "/appsnaps/">
<html>
<head>
    <title>${pageTitle}</title>
    <meta name="pageID" content="viewappsnap-Page"/>
    <content tag="pagetitle">${pageTitle}</content>
    <content tag="pageID">viewappsnap-Page</content>
    <content tag="pagehelp">
        <h3>${pageTitle}</h3>
    </content>

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
    <div class="jive-table">
        <table border="0" cellpadding="3" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th align="center"><@s.text name="admin.decorator.menu.system.appsnap.view.file.id"/></th>
                <th align="center"><@s.text name="admin.decorator.menu.system.appsnap.view.file.name"/></th>
                <th align="center"><@s.text name="admin.decorator.menu.system.appsnap.view.file.desc"/></th>
                <th style="padding-left:2px; padding-right:2px;text-align:center">Actions</th>
            </tr>
            </thead>
            <tbody>

                <#list filelist as flist>
                <tr>
                    <td>${flist_index+1}</td>
                    <td width="30%">
                        <a href=" <@s.url action="download-appsnap" method="grabFile">
                               <@s.param name="filename">${flist?html}</@s.param>
                                </@s.url>">${flist}</a>
                    </td>

                    <td width="20%"> <#if flist_index+1=1><span class="jive-icon-sml jive-icon-check"></span>
                        <@s.text name="admin.decorator.menu.system.appsnap.view.file.latest"/>
                        </span><#else><@s.text name="admin.decorator.menu.system.appsnap.view.file.backup"/></#if></td>
                    <td nowrap style="padding-left:2px; padding-right:2px;" width="40%" align="center">
                        &nbsp;&nbsp;
                        <a href=" <@s.url action="download-appsnap" method="grabFile">
                                  <@s.param name="filename">${flist?html}</@s.param>
                                  </@s.url>"><img src="../images/arrow-down-blue-16x16.gif" border="0" alt="Download Appsnap"/></a>

                        &nbsp;&nbsp;
                        <a href="#" onclick="return deleteAppsnap('${flist?html}')" alt="delete">
                            <img src="<@s.url value='/admin/images/delete-16x16.gif' />" alt="delete" border="0"/>
                        </a>

                        <form id="appsnap-delete-form-${flist?html}" name="appsnap-delete-form-${flist?html}"
                              action="<@s.url action='appsnap-view-previous' method='delete' />" method="post">
                            <input type="hidden" name="fileName" value="${flist}"/>
                            <@jive.token name="plugin-delete-{flist?html}"/>
                        </form>
                    </td>

                </#list>
            </tbody>
        </table>
    </div>
</div>
<p><@s.text name="admin.decorator.menu.system.appsnap.view.backup_param_days"><@s.param>${tidyHouseProperty}</@s.param></@s.text></p>
<#else>
<p><b><@s.text name="admin.decorator.menu.system.appsnap.view.file.exist"/> <a
        href="<@s.url value='appsnap-grab-form.jspa'/>"><@s.text name="admin.decorator.menu.system.appsnap.view.file.exist.here"/></a> <@s.text name="admin.decorator.menu.system.appsnap.view.file.exist.capture"/>
</p>
</#if>


</body>
</html>
