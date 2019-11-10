package dev.aoiroaoino.sandbox.stone

/**
 * リスト3.5
 */
object Chap3LexerRunner {

  @throws[ParseException]
  def main(args: Array[String]): Unit = {
    val l = Lexer(new CodeDialog())
    Iterator.continually(l.read()).takeWhile(_ != Token.EOF).foreach(t => println("==> " + t.getText))
  }
}
