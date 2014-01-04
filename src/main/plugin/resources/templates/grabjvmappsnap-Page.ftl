<#assign pageTitle = "Grab Appsnap">
<html>
<head>
    <title>${pageTitle}</title>
    <meta name="pageID" content="grabjvmappsnap-Page"/>
    <content tag="pagetitle">${pageTitle}</content>
    <content tag="pageID">grabjvmappsnap-Page</content>
    <content tag="pagehelp">
        <h3>${pageTitle}</h3>
    </content>

</head>

<body>
<form id="grabappsnapform" action="<@s.url action='appsnap-capture'/>" method="post">
    <p></p>

    <p></p>
    <fieldset>
        <legend><@s.text name="admin.decorator.menu.system.appsnap.grab"/></legend>
        <p></p>

        <p></p>

        <p></p>

        <p></p>
        <label><@s.text name="admin.decorator.menu.system.appsnap.grab.interval"/></label>
        <select id="appsnapinterval" name="appsnapinterval">
            <option value="1">1</option>
            <option value="2" selected>2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
        </select>
        &nbsp;
        <label><@s.text name="admin.decorator.menu.system.appsnap.grab.count"/></label>
        <select id="appsnapcount" name="appsnapcount">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5" selected>5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
        </select>
        &nbsp;
        <input id="grab-appsnap-buttom" name="grab-appsnap-buttom" type="submit" value="Execute"
               onclick="this.disabled=true; this.value='Please Wait...'; this.form.submit();">

        <p></p>

        <p></p>

        <p></p>

        <p></p>

        <p></p>
    </fieldset>

</form>
<p></p>

<p></p>
</body>
</html>
