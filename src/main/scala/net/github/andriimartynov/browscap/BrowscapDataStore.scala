package net.github.andriimartynov.browscap

import java.util.regex.Pattern

import scala.collection.Iterator

trait BrowscapDataStore {
  def map: Map[Pattern, Position]

  def str: String
}

object BrowscapDataStore {

  private case class _BrowscapDataStore(
                                         map: Map[Pattern, Position],
                                         str: String
                                       ) extends BrowscapDataStore

  def apply(
             iterator: Iterator[String]
           ): BrowscapDataStore = {
    val (map, str) = new CSVParser(
      new RegexConverter
    ).parse(iterator)
    _BrowscapDataStore(map, str)
  }
}
