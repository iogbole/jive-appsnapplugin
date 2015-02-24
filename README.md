

> **UPDATE: This project was not released as a Jive plugin. Jive Engineering/Product Team approved and moved the source code into the core Jive 7.0.1 release.**

[TOC]

The Appsnsp Plugin
=====================
Jive Application Snapshot tool (appsnap) passively gathers information about a running system and applications. The information that is collected by the appsnap tool consist of Java thread dumps and top outs, which are used for troubleshooting performance and general application issues.  

> **Story Line:**
> 
It so happened that I needed an appsnap while I was on a webex with a customer and we had to wait for approximately 30 minutes for the System Admin to grant us access to use the "jive user". In another webex with another customer in the same week, the ssh access was locked down and this means some managerial approvals were needed by the user to get ssh access to the servers just to capture an appnsnap. So I thought it would make sense to utterly eliminate the need to SSH when an appsnap is needed,  to help our customers capture useful appsnaps (bearing in mind that appsnaps are only useful in analysis when they are taken at the time of the problem), and ultimately, to save some precious time. Those three points are the motivations of this project.
 
The Appsnap plugin give Jive customers the ability to capture, download and manage appsnaps from Jive admin console seamlessly. 

----------
How it works
---------
 - The plugin utilizes the appsnap executable, so I assumed that all (5.0.x +) Jive instances have the appsnap script in /usr/local/jive/bin/appsnap
 
 - On start up, the plugin determines the JiveHome directory, checks if $JIVE_HOME/plugins/appsnapplugin/appsnaps exist and creates the directory ***iff*** the directory does not exist.
 
 - Appsnaps are stored in the file system $JIVE_HOME/plugins/appsnapplugin/appsnaps.
 
 - The plugin uses a custom system property; appsnap.file.maxdays . The value of the system property controls the maximum number of days an appsnap backup is kept on the file system before it is automatically deleted. It defaults to 28 days if the system property does not exit, or if the assigned value is invalid. For example, *appsnap.file.maxdays=VoodooDohnut* is invalid. Only long integers are considered valid.
 
 - Appsnaps are distinguished by appending timestamps to them.
 
 - All appsnaps will be deleted if the plugin is removed from admin console.
 - Restarting the application does not delete the appsnaps

Alright, these are some screenshots for your enjoyment:

**Home page**: Displays the history of previous appsnaps.

![enter image description here](https://lh3.googleusercontent.com/fNfv8gpZzVB8tWP3AQR91pgImVVmp6zTki8DI0yR5qk=s0)

**Capture appsnap**: Takes Interval and Count as parameters, the default values are 2 and 5 respectively.

![enter image description here](https://lh3.googleusercontent.com/scywhu7MVYQDNx92UlOqQmt0pZN3d9tBAdY8RXkqrIw=s0)

The Execute button is disabled on click - this is to prevent running multiple commands as this page might take a while to generate the appsnap:

![enter image description here](https://lh3.googleusercontent.com/5EyRGtdsT3xl8nSLdjq_J40mu5enn2Ay1DMCAldatHI=s0)

The Result: Renders the new appsnap. The files are sorted in descending order with respect to their timestamps.
![enter image description here](https://lh3.googleusercontent.com/NrVNWUsdvOVcegFB9OYNDURTtk67yMptmmDXXKAOyeo=s0)

Road Map
---------
So that's the available functionalities to this point, other items that are planned are:

 - To add a bit of [tda][5] flavour.
 
 - To move the "Grab Appsnap" menu to System - Management - Logging Management. Still trying to figure out how to do it without hacking the logging-management.jspa page.
 
 - To take advantage of the enhanced [ProcessBuilder][6] package of Java 7 in Jive 7 version of the plugin.

 - To avoid the temptation of making any enhancement that will require making calls to the database since this plugin comes to play only when there's an issue, so it's important to keep it simple.

 - To take your suggestions/recommendations onboard.
Supported Jive version
-----------------------
This version was tested on 6.0.{1-3}, but it should work on any 5.0 x and 7.x instance because nothing is requires from a specific version.

  [5]: https://java.net/projects/tda
  [6]: http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html

  [8]: http://math.stackexchange.com/
  [9]: http://daringfireball.net/projects/markdown/syntax "Markdown"
  [10]: https://github.com/jmcmanus/pagedown-extra "Pagedown Extra"
  [11]: http://meta.math.stackexchange.com/questions/5020/mathjax-basic-tutorial-and-quick-reference
  [12]: https://code.google.com/p/google-code-prettify/
  [13]: http://highlightjs.org/
