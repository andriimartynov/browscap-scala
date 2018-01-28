package net.github.andriimartynov.browscap

import java.util.regex.Pattern

import scala.util.Try

class BrowscapUserAgentParser(
                               browscapDataStore: BrowscapDataStore
                             ) {

  private val patterns = browscapDataStore
    .map
    .keys
    .toSeq.
    sortWith(_.pattern().length > _.pattern().length)
    .par

  def parse(
             userAgent: String
           ): Option[UserAgentInfo] = {
    val patterns = findPatterns(userAgent.toLowerCase)
    if (patterns.isEmpty) None
    else build(patterns.head, userAgent)
  }

  private def build(
                     pattern: Pattern,
                     userAgent: String
                   ): Option[UserAgentInfo] = {
    browscapDataStore.map.get(pattern).map(possition => {
      val result = browscapDataStore.str.substring(possition.offset, possition.length).split("\",\"")
      build(result, userAgent)
    })
  }

  private def build(
                     result: Array[String],
                     userAgent: String
                   ) = UserAgentInfo(
    userAgentString = userAgent,
    propertyName = result(0).dropWhile(_ == '"'),
    isMasterParent = Try(result(1).toBoolean).getOrElse(false),
    isLiteMode = Try(result(2).toBoolean).getOrElse(false),
    parent = result(3),
    comment = result(4),
    browser = result(5),
    browserType = result(6),
    browserBits = result(7),
    browserMaker = result(8),
    browserModus = result(9),
    version = result(10),
    majorVersion = result(11),
    minorVersion = result(12),
    platform = result(13),
    platformVersion = result(14),
    platformDescription = result(15),
    platformBits = result(16),
    platformMaker = result(17),
    isSyndicationReader = Try(result(34).toBoolean).getOrElse(false),
    isFake = Try(result(36).toBoolean).getOrElse(false),
    isAnonymized = Try(result(37).toBoolean).getOrElse(false),
    isModified = Try(result(38).toBoolean).getOrElse(false),
    cssVersion = result(39),
    deviceName = result(41),
    deviceMaker = result(42),
    deviceType = result(43),
    devicePointingMethod = result(44),
    deviceCodeName = result(45),
    deviceBrandName = result(46),
    renderingEngineName = result(47),
    renderingEngineVersion = result(48),
    renderingEngineDescription = result(49),
    renderingEngineMaker = result(50).substring(0, result(50).length() - 1)
  )

  private def findPatterns(userAgent: String) =
    patterns.filter(
      _.matcher(userAgent).matches()
    ).take(1)
}
