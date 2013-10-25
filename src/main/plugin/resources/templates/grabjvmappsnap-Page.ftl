<html>
<head>
    <title>Grab Appsnap</title>
    <meta name="pageID" content="grabjvmappsnap-Page"/>
    <content tag="pagetitle">Grab appsnap</content>
    <content tag="pageID">grabjvmappsnap-Page</content>
    <content tag="pagehelp">
        <h4>Grab appsnap</h4>
    </content>

</head>

<body>
<div class="pace-progress" id="pace-progress"></div>
<form id="grabappsnapform" action="<@s.url action='jvmappsnap'/>" method="post">
    <p></p>

    <p></p>
    <fieldset>
        <legend>Select interval (in seconds) and count</legend>
        <p></p>
        <label>Interval</label>
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
        <label>Count</label>
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

    </fieldset>
</form>
<p></p>

<p></p>
</body>
</html>
