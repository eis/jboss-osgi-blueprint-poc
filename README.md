These are proof-of-concepts of JBoss AS 7, based on work done earlier
by Thomas Diesler and submitted to
[github](https://github.com/tdiesler/jbosgi/tree/d7caf3126fb35b823d083b238e7d974b06865396/testsuite/jbossas).

However, as it no longer contains the examples, and as they were in few
respects out-dated, this is my attempt of replicating some of that.

These are examples using Blueprint.

Build with mvn clean package, deploy with mvn jboss-as:deploy.

Scope of these tests would be


<table>
    <tr>
        <th>Capability</th>
        <th>Status</th>
        <th>Notes</th>
    </tr>
    <tr>
        <td>OSGi bundle</td>
        <td>OK</td>
        <td></td>
    </tr>
    <tr>
        <td>war - osgibundle communication</td>
        <td>tbd</td>
        <td></td>
    </tr>
    <tr>
        <td>ejb - osgibundle communication</td>
        <td>tbd</td>
        <td></td>
    </tr>
    <tr>
        <td>wab (OSGi war)</td>
        <td>tbd</td>
        <td>Note really recommended in JBoss 7.1. JBoss does not directly support at that version, but
		forks a Jetty process for these, so they don't get the full set on features.
		Support will be in 7.2.</td>
    </tr>
    <tr>
        <td>wab - osgibundle communication</td>
        <td>tbd</td>
        <td></td>
    </tr>
    <tr>
        <td>JNDI with OSGi bundle</td>
        <td>OK</td>
        <td>JBoss 7.1 does not support, needs <a href="http://aries.apache.org/modules/jndiproject.html">Aries JNDI</a> installed</td>
    </tr>
    <tr>
        <td>JMS with OSGi bundle</td>
        <td>tbd</td>
        <td></td>
    </tr>
    <tr>
        <td>JTA with OSGi bundle</td>
        <td>tbd</td>
        <td></td>
    </tr>
    <tr>
        <td>JDBC with OSGi bundle</td>
        <td>OK</td>
        <td></td>
    </tr>
    <tr>
        <td>OSGi security</td>
        <td>-</td>
        <td>Related to bundle signing, similar to what applets have. JBoss does not implement.</td>
    </tr>
    <tr>
        <td>Petclinic app - bundle communication</td>
        <td>tbd</td>
        <td></td>
    </tr>
</table>

