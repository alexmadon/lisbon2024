# project

a test project to exhibit the issue of the refresh token expiration

https://github.com/Alfresco/alfresco-java-sdk/tree/develop/samples


https://docs.alfresco.com/content-services/latest/develop/oop-sdk/#restapijavawrapperproject


# errors


```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ mvn clean package -Dlicense.skip=true
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/alfresco/alfresco-java-sdk-samples/6.2.0/alfresco-java-sdk-samples-6.2.0.pom
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[FATAL] Non-resolvable parent POM for net.madon:madon-sdk-sample:1.0: Could not find artifact org.alfresco:alfresco-java-sdk-samples:pom:6.2.0 in central (https://repo.maven.apache.org/maven2) and 'parent.relativePath' points at wrong local POM @ line 8, column 11
 @ 
[ERROR] The build could not read 1 project -> [Help 1]
[ERROR]   
[ERROR]   The project net.madon:madon-sdk-sample:1.0 (/home/madon/hylandtools/sdk6/alextoken/pom.xml) has 1 error
[ERROR]     Non-resolvable parent POM for net.madon:madon-sdk-sample:1.0: Could not find artifact org.alfresco:alfresco-java-sdk-samples:pom:6.2.0 in central (https://repo.maven.apache.org/maven2) and 'parent.relativePath' points at wrong local POM @ line 8, column 11 -> [Help 2]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/ProjectBuildingException
[ERROR] [Help 2] http://cwiki.apache.org/confluence/display/MAVEN/UnresolvableModelException
```


thisis fixed using:

Maven needs to know about the Alfresco Artifacts Repository (Nexus) so add the following to ~/.m2/settings.xml:

```
<repositories>
    <repository>
      <id>alfresco-public</id>
      <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
    </repository>
  
  </repositories>
```

see https://docs.alfresco.com/content-services/latest/develop/oop-sdk/#restapijavawrapperproject

https://maven.apache.org/guides/mini/guide-multiple-repositories.html


## first possibility, in pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="http://maven.apache.org/POM/4.0.0"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>


  <parent>
    <groupId>org.alfresco</groupId>
    <artifactId>alfresco-java-sdk-samples</artifactId>
    <version>6.2.0</version>
  </parent>
  <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-java-sdk-samples -->
  <!-- NEXUS: amadon + miscrosoft passwd -->
  
  <groupId>net.madon</groupId>
  <artifactId>madon-sdk-sample</artifactId>
  <version>1.0</version>
  <packaging>pom</packaging>
  <name>Madon :: Java SDK :: Samples</name>
  <description>Alex Sample application of the Java SDK</description>

  <dependencies>
    <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-java-event-api-spring-boot-starter -->
    <dependency>
      <groupId>org.alfresco</groupId>
      <artifactId>alfresco-java-event-api-spring-boot-starter</artifactId>
      <version>6.2.0</version>
    </dependency>
    
    <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-acs-java-rest-api-spring-boot-starter -->
    <dependency>
      <groupId>org.alfresco</groupId>
      <artifactId>alfresco-acs-java-rest-api-spring-boot-starter</artifactId>
      <version>6.2.0</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

  
<repositories>
    <repository>
      <id>alfresco-public</id>
      <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
    </repository>
  
  </repositories>

  
</project>

```

## second possibility in settings.xml


```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">

  <!-- https://maven.apache.org/guides/mini/guide-multiple-repositories.html -->
  <profiles>
    <profile>
       <id>myprofile</id>
      <repositories>
	<repository>
	  <id>alfresco-public</id>
	  <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
	</repository>
	
      </repositories>
    </profile>
  </profiles>
  

  <activeProfiles>
    <activeProfile>myprofile</activeProfile>
  </activeProfiles>
  
 
</settings>

```


# use -X to debug:

```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ mvn clean package -Dlicense.skip=true -e -X
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /home/madon/alfrescosoft/apache-maven-3.8.6
Java version: 17.0.4, vendor: Eclipse Adoptium, runtime: /home/madon/alfrescobin/jdk-17.0.4+8
Default locale: en_GB, platform encoding: UTF-8
OS name: "linux", version: "6.1.0-9-amd64", arch: "amd64", family: "unix"
[DEBUG] Created new class realm maven.api
[DEBUG] Importing foreign packages into class realm maven.api
[DEBUG]   Imported: javax.annotation.* < plexus.core
[DEBUG]   Imported: javax.annotation.security.* < plexus.core
[DEBUG]   Imported: javax.inject.* < plexus.core
[DEBUG]   Imported: org.apache.maven.* < plexus.core
[DEBUG]   Imported: org.apache.maven.artifact < plexus.core
[DEBUG]   Imported: org.apache.maven.classrealm < plexus.core
[DEBUG]   Imported: org.apache.maven.cli < plexus.core
[DEBUG]   Imported: org.apache.maven.configuration < plexus.core
[DEBUG]   Imported: org.apache.maven.exception < plexus.core
[DEBUG]   Imported: org.apache.maven.execution < plexus.core
[DEBUG]   Imported: org.apache.maven.execution.scope < plexus.core
[DEBUG]   Imported: org.apache.maven.lifecycle < plexus.core
[DEBUG]   Imported: org.apache.maven.model < plexus.core
[DEBUG]   Imported: org.apache.maven.monitor < plexus.core
[DEBUG]   Imported: org.apache.maven.plugin < plexus.core
[DEBUG]   Imported: org.apache.maven.profiles < plexus.core
[DEBUG]   Imported: org.apache.maven.project < plexus.core
[DEBUG]   Imported: org.apache.maven.reporting < plexus.core
[DEBUG]   Imported: org.apache.maven.repository < plexus.core
[DEBUG]   Imported: org.apache.maven.rtinfo < plexus.core
[DEBUG]   Imported: org.apache.maven.settings < plexus.core
[DEBUG]   Imported: org.apache.maven.toolchain < plexus.core
[DEBUG]   Imported: org.apache.maven.usability < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.* < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.authentication < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.authorization < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.events < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.observers < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.proxy < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.repository < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.resource < plexus.core
[DEBUG]   Imported: org.codehaus.classworlds < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.* < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.classworlds < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.component < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.configuration < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.container < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.context < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.lifecycle < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.logging < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.personality < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.Xpp3Dom < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlPullParser < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlPullParserException < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlSerializer < plexus.core
[DEBUG]   Imported: org.eclipse.aether.* < plexus.core
[DEBUG]   Imported: org.eclipse.aether.artifact < plexus.core
[DEBUG]   Imported: org.eclipse.aether.collection < plexus.core
[DEBUG]   Imported: org.eclipse.aether.deployment < plexus.core
[DEBUG]   Imported: org.eclipse.aether.graph < plexus.core
[DEBUG]   Imported: org.eclipse.aether.impl < plexus.core
[DEBUG]   Imported: org.eclipse.aether.installation < plexus.core
[DEBUG]   Imported: org.eclipse.aether.internal.impl < plexus.core
[DEBUG]   Imported: org.eclipse.aether.metadata < plexus.core
[DEBUG]   Imported: org.eclipse.aether.repository < plexus.core
[DEBUG]   Imported: org.eclipse.aether.resolution < plexus.core
[DEBUG]   Imported: org.eclipse.aether.spi < plexus.core
[DEBUG]   Imported: org.eclipse.aether.transfer < plexus.core
[DEBUG]   Imported: org.eclipse.aether.version < plexus.core
[DEBUG]   Imported: org.fusesource.jansi.* < plexus.core
[DEBUG]   Imported: org.slf4j.* < plexus.core
[DEBUG]   Imported: org.slf4j.event.* < plexus.core
[DEBUG]   Imported: org.slf4j.helpers.* < plexus.core
[DEBUG]   Imported: org.slf4j.spi.* < plexus.core
[DEBUG] Populating class realm maven.api
[INFO] Error stacktraces are turned on.
[DEBUG] Message scheme: color
[DEBUG] Message styles: debug info warning error success failure strong mojo project
[DEBUG] Reading global settings from /home/madon/alfrescosoft/apache-maven-3.8.6/conf/settings.xml
[DEBUG] Reading user settings from /home/madon/.m2/settings.xml
[DEBUG] Reading global toolchains from /home/madon/alfrescosoft/apache-maven-3.8.6/conf/toolchains.xml
[DEBUG] Reading user toolchains from /home/madon/.m2/toolchains.xml
[DEBUG] Using local repository at /home/madon/.m2/repository
[DEBUG] Using manager EnhancedLocalRepositoryManager with priority 10.0 for /home/madon/.m2/repository
[INFO] Scanning for projects...
[DEBUG] Extension realms for project net.madon:madon-sdk-sample:pom:1.0: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.alfresco:alfresco-java-sdk-samples:pom:6.2.0: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.springframework.boot:spring-boot-starter-parent:pom:3.2.4: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.springframework.boot:spring-boot-dependencies:pom:3.2.4: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] === REACTOR BUILD PLAN ================================================
[DEBUG] Project: net.madon:madon-sdk-sample:pom:1.0
[DEBUG] Tasks:   [clean, package]
[DEBUG] Style:   Regular
[DEBUG] =======================================================================
[INFO] 
[INFO] ---------------------< net.madon:madon-sdk-sample >---------------------
[INFO] Building Madon :: Java SDK :: Samples 1.0
[INFO] --------------------------------[ pom ]---------------------------------
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] === PROJECT BUILD PLAN ================================================
[DEBUG] Project:       net.madon:madon-sdk-sample:1.0
[DEBUG] Dependencies (collect): [compile+runtime]
[DEBUG] Dependencies (resolve): [compile+runtime]
[DEBUG] Repositories (dependencies): [alfresco-public (https://artifacts.alfresco.com/nexus/content/groups/public, default, releases+snapshots), central (https://repo.maven.apache.org/maven2, default, releases)]
[DEBUG] Repositories (plugins)     : [central (https://repo.maven.apache.org/maven2, default, releases)]
[DEBUG] -----------------------------------------------------------------------
[DEBUG] Goal:          org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean (default-clean)
[DEBUG] Style:         Regular
[DEBUG] Configuration: <?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <directory default-value="${project.build.directory}"/>
  <excludeDefaultDirectories default-value="false">${maven.clean.excludeDefaultDirectories}</excludeDefaultDirectories>
  <failOnError default-value="true">${maven.clean.failOnError}</failOnError>
  <fast default-value="false">${maven.clean.fast}</fast>
  <fastDir>${maven.clean.fastDir}</fastDir>
  <fastMode default-value="background">${maven.clean.fastMode}</fastMode>
  <followSymLinks default-value="false">${maven.clean.followSymLinks}</followSymLinks>
  <outputDirectory default-value="${project.build.outputDirectory}"/>
  <reportDirectory default-value="${project.build.outputDirectory}"/>
  <retryOnError default-value="true">${maven.clean.retryOnError}</retryOnError>
  <session default-value="${session}"/>
  <skip default-value="false">${maven.clean.skip}</skip>
  <testOutputDirectory default-value="${project.build.testOutputDirectory}"/>
  <verbose>${maven.clean.verbose}</verbose>
</configuration>
[DEBUG] -----------------------------------------------------------------------
[DEBUG] Goal:          org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage (repackage)
[DEBUG] Style:         Regular
[DEBUG] Configuration: <?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <attach default-value="true"/>
  <excludeDevtools default-value="true">${spring-boot.repackage.excludeDevtools}</excludeDevtools>
  <excludeDockerCompose default-value="true">${spring-boot.repackage.excludeDockerCompose}</excludeDockerCompose>
  <excludeGroupIds default-value="">${spring-boot.excludeGroupIds}</excludeGroupIds>
  <excludes>${spring-boot.excludes}</excludes>
  <executable default-value="false"/>
  <finalName default-value="${project.build.finalName}"/>
  <includeSystemScope default-value="false"/>
  <includes>${spring-boot.includes}</includes>
  <layout>${spring-boot.repackage.layout}</layout>
  <mainClass>${start-class}</mainClass>
  <outputDirectory default-value="${project.build.directory}"/>
  <outputTimestamp default-value="${project.build.outputTimestamp}"/>
  <project default-value="${project}"/>
  <session default-value="${session}"/>
  <skip default-value="false">${spring-boot.repackage.skip}</skip>
</configuration>
[DEBUG] =======================================================================
[DEBUG] Using mirror maven-default-http-blocker (http://0.0.0.0/) for apache.snapshots (http://repository.apache.org/snapshots).
[DEBUG] Using mirror maven-default-http-blocker (http://0.0.0.0/) for ow2-snapshot (http://repository.ow2.org/nexus/content/repositories/snapshots).
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=1276690, ConflictMarker.markTime=669180, ConflictMarker.nodeCount=240, ConflictIdSorter.graphTime=1041616, ConflictIdSorter.topsortTime=590463, ConflictIdSorter.conflictIdCount=90, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=11751528, ConflictResolver.conflictItemCount=200, DefaultDependencyCollector.collectTime=568233439, DefaultDependencyCollector.transformTime=16990951}
[DEBUG] net.madon:madon-sdk-sample:pom:1.0
[DEBUG]    org.alfresco:alfresco-java-event-api-spring-boot-starter:jar:6.2.0:compile
[DEBUG]       org.alfresco:alfresco-java-event-api-spring-boot:jar:6.2.0:compile
[DEBUG]          org.springframework.boot:spring-boot:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework:spring-core:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-jcl:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]             org.springframework:spring-context:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-aop:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-beans:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-expression:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                io.micrometer:micrometer-observation:jar:1.12.4:compile (version managed from 1.12.4)
[DEBUG]                   io.micrometer:micrometer-commons:jar:1.12.4:compile (version managed from 1.12.4)
[DEBUG]          org.springframework.boot:spring-boot-autoconfigure:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]          org.springframework.boot:spring-boot-starter-integration:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework.boot:spring-boot-starter-aop:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                org.aspectj:aspectjweaver:jar:1.9.21:compile (version managed from 1.9.21)
[DEBUG]             org.springframework.integration:spring-integration-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                org.springframework:spring-messaging:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-tx:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework.retry:spring-retry:jar:2.0.5:compile (version managed from 2.0.5)
[DEBUG]                io.projectreactor:reactor-core:jar:3.6.4:compile (version managed from 3.6.4)
[DEBUG]                   org.reactivestreams:reactive-streams:jar:1.0.4:compile (version managed from 1.0.4)
[DEBUG]          org.springframework.boot:spring-boot-starter-activemq:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework.boot:spring-boot-starter:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                org.springframework.boot:spring-boot-starter-logging:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                   ch.qos.logback:logback-classic:jar:1.4.14:compile (version managed from 1.4.14)
[DEBUG]                      ch.qos.logback:logback-core:jar:1.4.14:compile (version managed from 1.4.14)
[DEBUG]                   org.apache.logging.log4j:log4j-to-slf4j:jar:2.21.1:compile (version managed from 2.21.1)
[DEBUG]                      org.apache.logging.log4j:log4j-api:jar:2.21.1:compile (version managed from 2.21.1)
[DEBUG]                   org.slf4j:jul-to-slf4j:jar:2.0.12:compile (version managed from 2.0.12)
[DEBUG]                jakarta.annotation:jakarta.annotation-api:jar:2.1.1:compile (version managed from 2.1.1)
[DEBUG]                org.yaml:snakeyaml:jar:2.2:compile (version managed from 2.2)
[DEBUG]             org.springframework:spring-jms:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]             org.apache.activemq:activemq-client-jakarta:jar:5.18.3:compile (version managed from 5.18.3)
[DEBUG]                jakarta.jms:jakarta.jms-api:jar:3.1.0:compile (version managed from 3.1.0)
[DEBUG]                org.fusesource.hawtbuf:hawtbuf:jar:1.11:compile
[DEBUG]          org.springframework.integration:spring-integration-jms:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]          org.alfresco:alfresco-java-event-api-integration:jar:6.2.0:compile
[DEBUG]             com.fasterxml.jackson.core:jackson-databind:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]                com.fasterxml.jackson.core:jackson-annotations:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]                com.fasterxml.jackson.core:jackson-core:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]             org.slf4j:slf4j-api:jar:2.0.12:compile (version managed from 2.0.12)
[DEBUG]          org.alfresco:alfresco-java-event-api-handling:jar:6.2.0:compile
[DEBUG]          org.alfresco:acs-event-model:jar:0.0.25:compile
[DEBUG]    org.alfresco:alfresco-acs-java-rest-api-spring-boot-starter:jar:6.2.0:compile
[DEBUG]       org.alfresco:alfresco-acs-java-rest-api-spring-boot:jar:6.2.0:compile
[DEBUG]          io.github.openfeign:feign-httpclient:jar:13.1:compile
[DEBUG]             io.github.openfeign:feign-core:jar:13.1:compile
[DEBUG]             org.apache.httpcomponents:httpclient:jar:4.5.14:compile
[DEBUG]                org.apache.httpcomponents:httpcore:jar:4.4.16:compile (version managed from 4.4.16)
[DEBUG]                commons-logging:commons-logging:jar:1.2:compile
[DEBUG]                commons-codec:commons-codec:jar:1.16.1:compile (version managed from 1.11)
[DEBUG]          org.alfresco:alfresco-core-rest-api:jar:6.2.0:compile
[DEBUG]             io.swagger:swagger-annotations:jar:1.5.20:compile
[DEBUG]             org.springframework.cloud:spring-cloud-starter-openfeign:jar:4.1.0:compile
[DEBUG]                org.springframework.cloud:spring-cloud-starter:jar:4.1.0:compile
[DEBUG]                   org.springframework.cloud:spring-cloud-context:jar:4.1.0:compile
[DEBUG]                   org.springframework.security:spring-security-rsa:jar:1.1.1:compile
[DEBUG]                      org.bouncycastle:bcprov-jdk18on:jar:1.74:compile
[DEBUG]                org.springframework.cloud:spring-cloud-openfeign-core:jar:4.1.0:compile
[DEBUG]                   io.github.openfeign.form:feign-form-spring:jar:3.8.0:compile
[DEBUG]                      io.github.openfeign.form:feign-form:jar:3.8.0:compile
[DEBUG]                   commons-fileupload:commons-fileupload:jar:1.5:compile
[DEBUG]                      commons-io:commons-io:jar:2.11.0:compile
[DEBUG]                org.springframework:spring-web:jar:6.1.5:compile (version managed from 6.1.1)
[DEBUG]                org.springframework.cloud:spring-cloud-commons:jar:4.1.0:compile
[DEBUG]                   org.springframework.security:spring-security-crypto:jar:6.2.3:compile (version managed from 6.2.0)
[DEBUG]                io.github.openfeign:feign-slf4j:jar:13.1:compile
[DEBUG]             org.alfresco:alfresco-java-rest-api-common:jar:6.2.0:compile
[DEBUG]                org.springframework.security:spring-security-oauth2-client:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-oauth2-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-web:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   com.nimbusds:oauth2-oidc-sdk:jar:9.43.3:compile
[DEBUG]                      com.github.stephenc.jcip:jcip-annotations:jar:1.0-1:compile
[DEBUG]                      com.nimbusds:content-type:jar:2.2:compile
[DEBUG]                      net.minidev:json-smart:jar:2.5.0:compile (version managed from [1.3.3,2.4.10])
[DEBUG]                         net.minidev:accessors-smart:jar:2.5.0:compile
[DEBUG]                            org.ow2.asm:asm:jar:9.3:compile
[DEBUG]                      com.nimbusds:lang-tag:jar:1.7:compile
[DEBUG]                      com.nimbusds:nimbus-jose-jwt:jar:9.24.4:compile
[DEBUG]                org.springframework.security:spring-security-config:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]             com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]          org.alfresco:alfresco-auth-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-discovery-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-governance-core-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-governance-classification-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-model-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-search-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-search-sql-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-event-gateway-api-client:jar:6.2.0:compile
[INFO] 
[INFO] --- maven-clean-plugin:3.3.2:clean (default-clean) @ madon-sdk-sample ---
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=78167, ConflictMarker.markTime=100612, ConflictMarker.nodeCount=2, ConflictIdSorter.graphTime=58219, ConflictIdSorter.topsortTime=14019, ConflictIdSorter.conflictIdCount=2, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=101431, ConflictResolver.conflictItemCount=2, DefaultDependencyCollector.collectTime=8321209, DefaultDependencyCollector.transformTime=420762}
[DEBUG] org.apache.maven.plugins:maven-clean-plugin:jar:3.3.2
[DEBUG]    org.codehaus.plexus:plexus-utils:jar:4.0.0:compile
[DEBUG] Created new class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG] Importing foreign packages into class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG]   Imported:  < maven.api
[DEBUG] Populating class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG]   Included: org.apache.maven.plugins:maven-clean-plugin:jar:3.3.2
[DEBUG]   Included: org.codehaus.plexus:plexus-utils:jar:4.0.0
[DEBUG] Configuring mojo org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean from plugin realm ClassRealm[plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2, parent: jdk.internal.loader.ClassLoaders$AppClassLoader@5cb0d902]
[DEBUG] Configuring mojo 'org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean' with basic configurator -->
[DEBUG]   (f) directory = /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG]   (f) excludeDefaultDirectories = false
[DEBUG]   (f) failOnError = true
[DEBUG]   (f) fast = false
[DEBUG]   (f) fastMode = background
[DEBUG]   (f) followSymLinks = false
[DEBUG]   (f) outputDirectory = /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG]   (f) reportDirectory = /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG]   (f) retryOnError = true
[DEBUG]   (f) session = org.apache.maven.execution.MavenSession@3c0fbd3a
[DEBUG]   (f) skip = false
[DEBUG]   (f) testOutputDirectory = /home/madon/hylandtools/sdk6/alextoken/target/test-classes
[DEBUG] -- end configuration --
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/test-classes
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/classes
[INFO] 
[INFO] --- spring-boot-maven-plugin:3.2.4:repackage (repackage) @ madon-sdk-sample ---
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=92929, ConflictMarker.markTime=74081, ConflictMarker.nodeCount=57, ConflictIdSorter.graphTime=65295, ConflictIdSorter.topsortTime=51849, ConflictIdSorter.conflictIdCount=39, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=1375490, ConflictResolver.conflictItemCount=56, DefaultDependencyCollector.collectTime=125266150, DefaultDependencyCollector.transformTime=1711249}
[DEBUG] org.springframework.boot:spring-boot-maven-plugin:jar:3.2.4
[DEBUG]    org.springframework.boot:spring-boot-buildpack-platform:jar:3.2.4:runtime
[DEBUG]       com.fasterxml.jackson.core:jackson-databind:jar:2.14.2:runtime
[DEBUG]          com.fasterxml.jackson.core:jackson-annotations:jar:2.14.2:runtime
[DEBUG]          com.fasterxml.jackson.core:jackson-core:jar:2.14.2:runtime
[DEBUG]       com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.14.2:runtime
[DEBUG]       net.java.dev.jna:jna-platform:jar:5.13.0:runtime
[DEBUG]          net.java.dev.jna:jna:jar:5.13.0:runtime
[DEBUG]       org.apache.commons:commons-compress:jar:1.21:runtime
[DEBUG]       org.apache.httpcomponents.client5:httpclient5:jar:5.2.3:runtime
[DEBUG]          org.apache.httpcomponents.core5:httpcore5:jar:5.2.4:runtime
[DEBUG]          org.apache.httpcomponents.core5:httpcore5-h2:jar:5.2.4:runtime
[DEBUG]       org.tomlj:tomlj:jar:1.0.0:runtime
[DEBUG]          org.antlr:antlr4-runtime:jar:4.7.2:runtime
[DEBUG]          com.google.code.findbugs:jsr305:jar:3.0.2:runtime
[DEBUG]    org.springframework.boot:spring-boot-loader-tools:jar:3.2.4:runtime
[DEBUG]    org.apache.maven.shared:maven-common-artifact-filters:jar:3.3.2:runtime
[DEBUG]       org.slf4j:slf4j-api:jar:1.7.36:compile
[DEBUG]    org.springframework:spring-core:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-jcl:jar:6.1.5:runtime
[DEBUG]    org.springframework:spring-context:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-aop:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-beans:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-expression:jar:6.1.5:runtime
[DEBUG]       io.micrometer:micrometer-observation:jar:1.12.4:runtime
[DEBUG]          io.micrometer:micrometer-commons:jar:1.12.4:runtime
[DEBUG]    org.sonatype.plexus:plexus-build-api:jar:0.0.7:runtime
[DEBUG]       org.codehaus.plexus:plexus-utils:jar:1.5.8:compile
[DEBUG]    org.apache.maven.plugins:maven-shade-plugin:jar:3.5.0:compile (optional)
[DEBUG]       org.ow2.asm:asm:jar:9.5:compile (optional)
[DEBUG]       org.ow2.asm:asm-commons:jar:9.5:compile (optional)
[DEBUG]          org.ow2.asm:asm-tree:jar:9.5:compile (optional)
[DEBUG]       org.jdom:jdom2:jar:2.0.6.1:compile (optional)
[DEBUG]       org.apache.maven.shared:maven-dependency-tree:jar:3.2.1:compile (optional)
[DEBUG]          org.eclipse.aether:aether-util:jar:1.0.0.v20140518:compile (optional)
[DEBUG]             org.eclipse.aether:aether-api:jar:1.0.0.v20140518:compile (optional)
[DEBUG]       commons-io:commons-io:jar:2.13.0:compile (optional)
[DEBUG]       org.vafer:jdependency:jar:2.8.0:compile (optional)
[DEBUG]       org.apache.commons:commons-collections4:jar:4.4:compile (optional)
[DEBUG] Created new class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG] Importing foreign packages into class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG]   Imported:  < maven.api
[DEBUG] Populating class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG]   Included: org.springframework.boot:spring-boot-maven-plugin:jar:3.2.4
[DEBUG]   Included: org.springframework.boot:spring-boot-buildpack-platform:jar:3.2.4
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-databind:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-annotations:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-core:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.14.2
[DEBUG]   Included: net.java.dev.jna:jna-platform:jar:5.13.0
[DEBUG]   Included: net.java.dev.jna:jna:jar:5.13.0
[DEBUG]   Included: org.apache.commons:commons-compress:jar:1.21
[DEBUG]   Included: org.apache.httpcomponents.client5:httpclient5:jar:5.2.3
[DEBUG]   Included: org.apache.httpcomponents.core5:httpcore5:jar:5.2.4
[DEBUG]   Included: org.apache.httpcomponents.core5:httpcore5-h2:jar:5.2.4
[DEBUG]   Included: org.tomlj:tomlj:jar:1.0.0
[DEBUG]   Included: org.antlr:antlr4-runtime:jar:4.7.2
[DEBUG]   Included: com.google.code.findbugs:jsr305:jar:3.0.2
[DEBUG]   Included: org.springframework.boot:spring-boot-loader-tools:jar:3.2.4
[DEBUG]   Included: org.apache.maven.shared:maven-common-artifact-filters:jar:3.3.2
[DEBUG]   Included: org.springframework:spring-core:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-jcl:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-context:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-aop:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-beans:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-expression:jar:6.1.5
[DEBUG]   Included: io.micrometer:micrometer-observation:jar:1.12.4
[DEBUG]   Included: io.micrometer:micrometer-commons:jar:1.12.4
[DEBUG]   Included: org.sonatype.plexus:plexus-build-api:jar:0.0.7
[DEBUG]   Included: org.codehaus.plexus:plexus-utils:jar:1.5.8
[DEBUG]   Included: org.apache.maven.plugins:maven-shade-plugin:jar:3.5.0
[DEBUG]   Included: org.ow2.asm:asm:jar:9.5
[DEBUG]   Included: org.ow2.asm:asm-commons:jar:9.5
[DEBUG]   Included: org.ow2.asm:asm-tree:jar:9.5
[DEBUG]   Included: org.jdom:jdom2:jar:2.0.6.1
[DEBUG]   Included: org.apache.maven.shared:maven-dependency-tree:jar:3.2.1
[DEBUG]   Included: org.eclipse.aether:aether-util:jar:1.0.0.v20140518
[DEBUG]   Included: commons-io:commons-io:jar:2.13.0
[DEBUG]   Included: org.vafer:jdependency:jar:2.8.0
[DEBUG]   Included: org.apache.commons:commons-collections4:jar:4.4
[DEBUG] Configuring mojo org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage from plugin realm ClassRealm[plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4, parent: jdk.internal.loader.ClassLoaders$AppClassLoader@5cb0d902]
[DEBUG] Configuring mojo 'org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage' with basic configurator -->
[DEBUG]   (f) attach = true
[DEBUG]   (f) excludeDevtools = true
[DEBUG]   (f) excludeDockerCompose = true
[DEBUG]   (f) excludes = []
[DEBUG]   (f) executable = false
[DEBUG]   (f) finalName = madon-sdk-sample-1.0
[DEBUG]   (f) includeSystemScope = false
[DEBUG]   (f) includes = []
[DEBUG]   (f) outputDirectory = /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG]   (f) project = MavenProject: net.madon:madon-sdk-sample:1.0 @ /home/madon/hylandtools/sdk6/alextoken/pom.xml
[DEBUG]   (f) session = org.apache.maven.execution.MavenSession@3c0fbd3a
[DEBUG]   (f) skip = false
[DEBUG] -- end configuration --
[DEBUG] repackage goal could not be applied to pom project.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.300 s
[INFO] Finished at: 2024-07-11T11:00:59+02:00
[INFO] ------------------------------------------------------------------------
```


# tree structure

https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html



# [WARNING] The goal is skip due to packaging 'pom'



change:

<packaging>pom</packaging>


into

<packaging>jar</packaging>




# missing priperties file


```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ java -jar /home/madon/hylandtools/sdk6/alextoken/target/sdk-alex-6.2.0.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

2024-07-11T11:38:08.438+02:00  INFO 35272 --- [           main] net.madon.tutorial.SdkDemoApplication    : Starting SdkDemoApplication v6.2.0 using Java 17.0.7 with PID 35272 (/home/madon/hylandtools/sdk6/alextoken/target/sdk-alex-6.2.0.jar started by madon in /home/madon/hylandtools/sdk6/alextoken)
2024-07-11T11:38:08.446+02:00  INFO 35272 --- [           main] net.madon.tutorial.SdkDemoApplication    : No active profile set, falling back to 1 default profile: "default"
2024-07-11T11:38:09.903+02:00  INFO 35272 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=1bd049a9-7c05-347f-887f-3b905d1ede4d
2024-07-11T11:38:09.909+02:00  WARN 35272 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'org.alfresco.governance.classification.handler.ClassificationReasonsApiClient' defined in null: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
2024-07-11T11:38:09.951+02:00  INFO 35272 --- [           main] .s.b.a.l.ConditionEvaluationReportLogger : 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-07-11T11:38:09.984+02:00 ERROR 35272 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'org.alfresco.governance.classification.handler.ClassificationReasonsApiClient' defined in null: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
	at org.springframework.beans.factory.config.PlaceholderConfigurerSupport.doProcessProperties(PlaceholderConfigurerSupport.java:230) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.processProperties(PropertySourcesPlaceholderConfigurer.java:207) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.postProcessBeanFactory(PropertySourcesPlaceholderConfigurer.java:173) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:363) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:189) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:788) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:606) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:334) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1354) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at net.madon.tutorial.SdkDemoApplication.main(SdkDemoApplication.java:27) ~[!/:6.2.0]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91) ~[sdk-alex-6.2.0.jar:6.2.0]
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53) ~[sdk-alex-6.2.0.jar:6.2.0]
	at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58) ~[sdk-alex-6.2.0.jar:6.2.0]
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
	at org.springframework.util.PropertyPlaceholderHelper.parseStringValue(PropertyPlaceholderHelper.java:180) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.util.PropertyPlaceholderHelper.replacePlaceholders(PropertyPlaceholderHelper.java:126) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.core.env.AbstractPropertyResolver.doResolvePlaceholders(AbstractPropertyResolver.java:239) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.core.env.AbstractPropertyResolver.resolveRequiredPlaceholders(AbstractPropertyResolver.java:210) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.lambda$processProperties$0(PropertySourcesPlaceholderConfigurer.java:200) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.resolveStringValue(BeanDefinitionVisitor.java:293) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.resolveValue(BeanDefinitionVisitor.java:219) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.visitPropertyValues(BeanDefinitionVisitor.java:147) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.visitBeanDefinition(BeanDefinitionVisitor.java:85) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.PlaceholderConfigurerSupport.doProcessProperties(PlaceholderConfigurerSupport.java:227) ~[spring-beans-6.1.5.jar!/:6.1.5]
	... 19 common frames omitted

```
