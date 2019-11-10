package dev.aoiroaoino.sandbox.stone

import java.io.{IOException, LineNumberReader, Reader}
import java.util
import java.util.regex.{Matcher, Pattern}

/**
 * リスト3.3
 */
class Lexer(
  reader: LineNumberReader,
  var hasMore: Boolean
) {
  import Lexer._

  private val queue: util.ArrayList[Token] = new util.ArrayList()

  @throws[ParseException]
  def read(): Token =
    if (fillQueue(0))
      queue.remove(0)
    else
      Token.EOF

  @throws[ParseException]
  def peek(i: Int): Token =
    if (fillQueue(i))
      queue.get(i)
    else
      Token.EOF

  @throws[ParseException]
  private def fillQueue(i: Int): Boolean = {
    while (i >= queue.size()) {
      if (hasMore)
        readLine()
      else
        false
    }
    true
  }

  @throws[ParseException]
  protected def readLine(): Unit = {
    var line: String = null
    try {
      line = reader.readLine()
    } catch {
      case e: IOException => throw ParseException(e)
    }
    if (line == null) {
      hasMore = false
      return
    }
    val lineNo = reader.getLineNumber
    val matcher = pattern.matcher(line)
    matcher.useTransparentBounds(true).useAnchoringBounds(false)
    var pos: Int = 0
    val endPos = line.length
    while (pos < endPos) {
      matcher.region(pos, endPos)
      if (matcher.lookingAt()) {
        addToken(lineNo, matcher)
        pos = matcher.end()
      } else {
        throw ParseException(s"bad token at line $lineNo")
      }
    }
    queue.add(IdToken(lineNo, Token.EOL))
  }

  @throws[ParseException]
  protected def addToken(lineNo: Int, matcher: Matcher): Unit = {
    val m = matcher.group(1)
    if (m != null)
      if (matcher.group(2) == null) {
        var token: Token = null
        if (matcher.group(3) != null)
          token = NumToken(lineNo, m.toInt)
        else if (matcher.group(4) != null)
          token = StrToken(lineNo, toStringLiteral(m))
        else
          token = IdToken(lineNo, m)
        queue.add(token)
      }
  }

  @throws[ParseException]
  protected def toStringLiteral(s: String): String = {
    val sb = new StringBuilder()
    val len = s.length - 1
    var i: Int = 1
    while (i < len) {
      var c = s.charAt(i)
      if (c == '\\' && i + 1 < len) {
        val c2 = s.charAt(i + 1)
        if (c2 == '"' || c2 == '\\') {
          i += 1
          c = s.charAt(i)
        } else if (c2 == 'n') {
          i += 1
          c = '\n'
        }
      }
      sb.append(c)
      i += 1
    }
    sb.toString
  }
}

object Lexer {
  def apply(r: Reader): Lexer = new Lexer(
    hasMore = true,
    reader = new LineNumberReader(r)
  )

  final val regexPat =
    "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?"

  final val pattern: Pattern = Pattern.compile(regexPat)
}
