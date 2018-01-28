package net.github.andriimartynov.browscap

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.prop.PropertyChecks

class RegexConverterTest extends FlatSpec
  with Matchers
  with MockitoSugar
  with PropertyChecks {

  val cut = new RegexConverter

  "convert" should " * convert to .*?" in {
    val res = cut.convert("*")
    res should be("^.*?$")
  }

  "convert" should " ? convert to ." in {
    val res = cut.convert("?")
    res should be("^.$")
  }

  "convert" should "do not convert a1" in {
    val res = cut.convert("a1")
    res should be("^a1$")
  }

  "convert" should "escape each char" in {
    val res = cut.convert("!@#")
    res should be("^\\!\\@\\#$")
  }
}
