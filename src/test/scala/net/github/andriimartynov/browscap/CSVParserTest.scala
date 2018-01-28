package net.github.andriimartynov.browscap

import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.prop.PropertyChecks

import scala.collection.Iterator
import scala.io.BufferedSource

class CSVParserTest extends FlatSpec
  with Matchers
  with MockitoSugar
  with PropertyChecks
  with BeforeAndAfterEach {

  val regexConverter = mock[RegexConverter]
  val cut = new CSVParser(regexConverter)

  override def afterEach() {
    reset(regexConverter)
  }

  "parse" should " return empty data store" in {
    val iterator = mock[Iterator[String]]
    val row = "\"CFNetwork/*\",\"false\",\"false\",\"CFNetwork Generic\""

    when(iterator.hasNext).thenReturn(true)
    when(iterator.next()).thenReturn(row)
    when(regexConverter.convert("CFNetwork/*")).thenReturn("[a-z][A-Z][0-9]!@#$%^&;*()_+=|{}[];:'\"<>;,.?/`~§ -")

    val (map, str) = cut.parse(iterator)
    map.size should be(0)
    str.length should be(0)
    verify(regexConverter, times(1)).convert("CFNetwork/*")
    verify(iterator, times(1)).hasNext
    verify(iterator, times(1)).next
  }

  "parse" should " return data store with one element" in {
    val iterator = mock[Iterator[String]]
    val row = "\"CFNetwork/*\",\"false\",\"false\",\"CFNetwork Generic\""

    when(iterator.hasNext).thenReturn(true).thenReturn(false)
    when(iterator.next()).thenReturn(row).thenReturn("")
    when(regexConverter.convert("CFNetwork/*")).thenReturn("^.$")

    val (map, str) = cut.parse(iterator)
    map.size should be(1)
    str should be(row)
    verify(regexConverter, times(1)).convert("CFNetwork/*")
    verify(iterator, times(2)).hasNext
    verify(iterator, times(1)).next
  }

  "parse" should " stop after invalid pattern" in {
    val iterator = mock[Iterator[String]]
    val row = "\"CFNetwork/*\",\"false\",\"false\",\"CFNetwork Generic\""

    when(iterator.hasNext).thenReturn(true)
    when(iterator.next()).thenReturn(row)
    when(regexConverter.convert("CFNetwork/*"))
      .thenReturn("^.$")
      .thenReturn("[a-z][A-Z][0-9]!@#$%^&;*()_+=|{}[];:'\"<>;,.?/`~§ -")

    val (map, str) = cut.parse(iterator)
    map.size should be(1)
    str should be(row)
    verify(regexConverter, times(2)).convert("CFNetwork/*")
    verify(iterator, times(2)).hasNext
    verify(iterator, times(2)).next
  }
}
