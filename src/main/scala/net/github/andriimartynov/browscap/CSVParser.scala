package net.github.andriimartynov.browscap

import java.util.regex.Pattern

import scala.annotation.tailrec
import scala.collection.Iterator
import scala.util.{Failure, Success, Try}

class CSVParser(val regexConverter: RegexConverter) {

  def parse(
             iterator: Iterator[String]
           ): (Map[Pattern, Position], String) =
    parse(iterator, Map(), 0, new StringBuilder)

  @tailrec
  private def parse(iterator: Iterator[String],
                    map: Map[Pattern, Position],
                    offset: Int,
                    sb: StringBuilder
                   ): (Map[Pattern, Position], String) = {
    if (!iterator.hasNext) (map, sb.toString)
    else {
      val row = iterator.next()
      val parseResult = parseRow(row, offset)
      if (parseResult.isFailure) (map, sb.toString)
      else {
        val rowMap = parseResult.get
        val newOffset = offset + row.length

        parse(iterator, map ++ rowMap, newOffset, sb.append(row))
      }
    }
  }

  private def compilePattern(pattern: String) =
    Try(
      Pattern.compile(
        regexConverter.convert(pattern)))

  private def getPattern(row: String) =
    row
      .dropWhile(_ == '"')
      .takeWhile(_ != '"')

  private def parseRow(
                        row: String,
                        offset: Int
                      ): Try[Map[Pattern, Position]] = {
    val patternString = getPattern(row)
    val pattern = compilePattern(patternString)
    pattern match {
      case Success(pattern) => parseRow (pattern, row, offset)
      case Failure(ex) => Failure(ex)
    }
  }

  private def parseRow(
                        pattern: Pattern,
                        row: String,
                        offset: Int) = Success {
    val position = Position(offset, offset + row.length)
    Map(pattern -> position)
  }
}
