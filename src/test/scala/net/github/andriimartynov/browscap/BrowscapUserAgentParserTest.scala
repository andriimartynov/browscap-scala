package net.github.andriimartynov.browscap

import java.util.regex.{Matcher, Pattern}

import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.prop.PropertyChecks

class BrowscapUserAgentParserTest extends FlatSpec
  with Matchers
  with MockitoSugar
  with PropertyChecks
  with BeforeAndAfterEach {

  val browscapDataStore = mock[BrowscapDataStore]


  override def afterEach() {
    reset(browscapDataStore)
  }

  "parse" should " return None because datastore is empty" in {
    when(browscapDataStore.map).thenReturn(Map[Pattern, Position]())
    when(browscapDataStore.str).thenReturn("")
    val cut = new BrowscapUserAgentParser(browscapDataStore)

    val res = cut.parse("userAgent")
    res should be(None)
  }

  "parse" should " return None because user agent not matched" in {
    val position = mock[Position]
    val pattern = Pattern.compile("^&$")

    when(browscapDataStore.map).thenReturn(Map(pattern -> position))
    when(browscapDataStore.str).thenReturn("")
    val cut = new BrowscapUserAgentParser(browscapDataStore)

    val res = cut.parse("userAgent")
    res should be(None)
  }

  "parse" should " return user agent info" in {
    val position = mock[Position]
    val pattern = Pattern.compile("^.*?$")
    val str = "\"CFNetwork/*\",\"false\",\"false\",\"CFNetwork Generic\",\"CFNetwork Generic\",\"CFNetwork\",\"Application\",\"32\",\"Apple Inc\",\"\",\"0.0\",\"0\",\"0\",\"Darwin\",\"\",\"Darwin is a Core Component of MacOSX and iOS\",\"32\",\"Apple Inc\",\"false\",\"false\",\"false\",\"false\",\"false\",\"true\",\"true\",\"true\",\"true\",\"false\",\"true\",\"false\",\"true\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"false\",\"0\",\"0\",\"Macintosh\",\"Apple Inc\",\"Desktop\",\"mouse\",\"Macintosh\",\"Apple\",\"WebKit\",\"\",\"For Google Chrome, iOS (including both mobile Safari, WebViews within third-party apps, and web clips), Safari, Arora, Midori, OmniWeb, Shiira, iCab since version 4, Web, SRWare Iron, Rekonq, and in Maxthon 3.\",\"Apple Inc\""

    when(position.offset).thenReturn(0)
    when(position.length).thenReturn(str.length)
    when(browscapDataStore.map).thenReturn(Map(pattern -> position))
    when(browscapDataStore.str).thenReturn(str)
    val cut = new BrowscapUserAgentParser(browscapDataStore)

    val res = cut.parse("userAgent")
    res.isDefined should be(true)
    val userAgentInfo = res.get

    userAgentInfo.userAgentString should be("userAgent")
    userAgentInfo.propertyName should be("CFNetwork/*")
    userAgentInfo.isMasterParent should be(false)
    userAgentInfo.isLiteMode should be(false)
    userAgentInfo.parent should be("CFNetwork Generic")
    userAgentInfo.comment should be("CFNetwork Generic")
    userAgentInfo.browser should be("CFNetwork")
    userAgentInfo.browserType should be("Application")
    userAgentInfo.browserBits should be("32")
    userAgentInfo.browserMaker should be("Apple Inc")
    userAgentInfo.browserModus should be("")
    userAgentInfo.version should be("0.0")
    userAgentInfo.majorVersion should be("0")
    userAgentInfo.minorVersion should be("0")
    userAgentInfo.platform should be("Darwin")
    userAgentInfo.platformVersion should be("")
    userAgentInfo.platformDescription should be("Darwin is a Core Component of MacOSX and iOS")
    userAgentInfo.platformBits should be("32")
    userAgentInfo.platformMaker should be("Apple Inc")
    userAgentInfo.isSyndicationReader should be(false)
    userAgentInfo.isFake should be(false)
    userAgentInfo.isAnonymized should be(false)
    userAgentInfo.isModified should be(false)
    userAgentInfo.cssVersion should be("0")
    userAgentInfo.deviceName should be("Macintosh")
    userAgentInfo.deviceMaker should be("Apple Inc")
    userAgentInfo.deviceType should be("Desktop")
    userAgentInfo.devicePointingMethod should be("mouse")
    userAgentInfo.deviceCodeName should be("Macintosh")
    userAgentInfo.deviceBrandName should be("Apple")
    userAgentInfo.renderingEngineName should be("WebKit")
    userAgentInfo.renderingEngineVersion should be("")
    userAgentInfo.renderingEngineDescription should be("For Google Chrome, iOS (including both mobile Safari, WebViews within third-party apps, and web clips), Safari, Arora, Midori, OmniWeb, Shiira, iCab since version 4, Web, SRWare Iron, Rekonq, and in Maxthon 3.")
    userAgentInfo.renderingEngineMaker should be("Apple Inc")
  }

}
