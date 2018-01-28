# browscap-scala
A simple to use Scala code classes, for embedding into your own projects, using the [Browser Capabilities Project](http://browscap.org/) **browscap.csv** file, which can be download from [here](http://browscap.org/stream?q=BrowsCapCSV)

## Getting browscap-scala

To resolve artifacts through Artifactory, simply add the following code snippet to your build.sbt file:

```scala
resolvers += "JFrog OSS Release Local" at "https://oss.jfrog.org/artifactory/oss-release-local"
```

The current version is 0.0.1, which is cross-built against Scala 2.11.x and 2.12.x.

```scala
libraryDependencies += "net.github.andriimartynov.browscap" %% "browscap-scala" % "0.0.1"
```

## Usage example
```scala
val iterator = Source
    .fromFile("./PATH_TO_BROWSCAP_CSV")
    .getLines()
val dataStore = BrowscapDataStore(iterator)
val parser = new BrowscapUserAgentParser(dataStore)

val browscapOpt = parser
    .parse("Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20130917 Firefox/17.0")   
```
Example for a possible output (2018-01-26):

**Actual Properties**:
```scala
browscapOpt.map(browscap => {
    println(browscap.userAgentString)
    println(browscap.propertyName)
    println(browscap.isMasterParent)
    println(browscap.isLiteMode)
    println(browscap.parent)
    println(browscap.comment)
    println(browscap.browser)
    println(browscap.browserType)
    println(browscap.browserBits)
    println(browscap.browserMaker)
    println(browscap.browserModus)
    println(browscap.version)
    println(browscap.majorVersion)
    println(browscap.minorVersion)
    println(browscap.platform)
    println(browscap.platformVersion)
    println(browscap.platformDescription)
    println(browscap.platformBits)
    println(browscap.platformMaker)
    println(browscap.isSyndicationReader)
    println(browscap.isFake)
    println(browscap.isAnonymized)
    println(browscap.isModified)
    println(browscap.cssVersion)
    println(browscap.deviceName)
    println(browscap.deviceMaker)
    println(browscap.deviceType)
    println(browscap.devicePointingMethod)
    println(browscap.deviceCodeName)
    println(browscap.deviceBrandName)
    println(browscap.renderingEngineName)
    println(browscap.renderingEngineVersion)
    println(browscap.renderingEngineDescription)
    println(browscap.renderingEngineMaker)
  })
```

## Result example
The output of the parent code for the used User-Agent-String looks like this:

```scala
 *browscap.userAgentString                  Mozilla/5.0 (X11; Linux x86_64; rv:17.0) Gecko/20130917 Firefox/17.0
 *browscap.propertyName                     Mozilla/5.0 (*Linux*x86_64*) Gecko* Firefox/17.0*
 *browscap.isMasterParent                   false
 *browscap.isLiteMode                       false
 *browscap.parent                           Firefox 17.0
 *browscap.comment                          Firefox 17.0
 *browscap.browser                          Firefox
 *browscap.browserType                      Browser
 *browscap.browserBits                      64
 *browscap.browserMaker                     Mozilla Foundation
 *browscap.browserModus 
 *browscap.version                          17.0
 *browscap.majorVersion                     17
 *browscap.minorVersion                     0
 *browscap.platform                         Linux
 *browscap.platformVersion 
 *browscap.platformDescription              Linux
 *browscap.platformBits                     64
 *browscap.platformMaker                    Linux Foundation
 *browscap.isSyndicationReader              false
 *browscap.isFake                           false
 *browscap.isAnonymized                     false
 *browscap.isModified                       false
 *browscap.cssVersion                       3
 *browscap.deviceName                       Linux Desktop
 *browscap.deviceMaker 
 *browscap.deviceType                       Desktop
 *browscap.devicePointingMethod             mouse
 *browscap.deviceCodeName                   Linux Desktop
 *browscap.deviceBrandName 
 *browscap.renderingEngineName              Gecko
 *browscap.renderingEngineVersion           17.0
 *browscap.renderingEngineDescription       For Firefox, Camino, K-Meleon, SeaMonkey, Netscape, and other Gecko-based browsers.
 *browscap.renderingEngineMaker             Mozilla Foundation
```

## Thanks to
Thanks for the inspiration to me to realize this project:

- [Browser Capabilities Project](http://browscap.org/)
- [Klaus Tachtler](https://github.com/tachtler/browscap4jFileReader)
- [Ankush Sharma](https://github.com/ankushs92/Browscap4j)