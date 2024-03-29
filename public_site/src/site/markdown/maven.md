## Maven Repositories

### wcm.io Repository

The released wcm.io artifacts are available at Maven Central:

http://search.maven.org/#search|ga|1|io.wcm

Snapshots releases are available on the Sonatype snapshot repository - use at your own risk!

https://oss.sonatype.org/content/repositories/snapshots/io/wcm/

The maven artifact coordinates are documented on the index page of each wcm.io module.


### External Maven Repositories

wcm.io depends on the Adobe Public Maven Repository:

```xml
<repositories>
  <repository>
    <id>adobe-public-releases</id>
    <name>Adobe Public Repository</name>
    <url>http://repo.adobe.com/nexus/content/groups/public</url>
    <releases>
      <enabled>true</enabled>
      <updatePolicy>never</updatePolicy>
    </releases>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
  </repository>
</repositories>

<pluginRepositories>
  <pluginRepository>
    <id>adobe-public-releases</id>
    <name>Adobe Public Repository</name>
    <url>http://repo.adobe.com/nexus/content/groups/public</url>
    <releases>
      <enabled>true</enabled>
      <updatePolicy>never</updatePolicy>
    </releases>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
  </pluginRepository>
</pluginRepositories>
```

### Apache Snapshot/Intermediate Release Repositories

These two repositories are hosted on wcm.io and provide snapshots from the Apache SCM trunk which are not released currently. They are replaced with the official versions as soon as they are released by the Apache project.

```xml
<repository>
  <id>wcm-io-apache-intermediate-release</id>
  <url>http://wcm.io/maven/repositories/apache-intermediate-release</url>
  <layout>default</layout>
  <releases>
    <enabled>true</enabled>
    <updatePolicy>never</updatePolicy>
  </releases>
  <snapshots>
    <enabled>false</enabled>
  </snapshots>
</repository>

<repository>
  <id>wcm-ioapache-snapshots</id>
  <url>http://wcm.io/maven/repositories/apache-snapshots</url>
  <layout>default</layout>
  <releases>
    <enabled>false</enabled>
  </releases>
  <snapshots>
    <enabled>true</enabled>
    <updatePolicy>always</updatePolicy>
  </snapshots>
</repository>
```
