package net.github.andriimartynov.browscap

object RegexConverter {
  val regexPrefixStr = "^"
  val regexPostfixStr = "$"

  val * = '*'

  val ? = '?'

  val \ = '\\'

  val dotCharArray = ".".toCharArray

  val starCharArray = ".*?".toCharArray
}

class RegexConverter {

  import RegexConverter._

  def convert(regexPattern: String): String =
    regexPrefixStr +
      convertString(regexPattern) +
      regexPostfixStr

  private def convertChar(char: Char) =
    if (char.isLetterOrDigit || char.isWhitespace) Array(char)
    else Array(\, char)

  private def convertString(str: String) =
    str.toCharArray
      .flatMap(e => e match {
        case * => starCharArray
        case ? => dotCharArray
        case char => convertChar(char)
      }).mkString
      .toLowerCase
}
