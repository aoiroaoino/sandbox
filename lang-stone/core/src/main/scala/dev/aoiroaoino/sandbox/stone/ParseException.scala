package dev.aoiroaoino.sandbox.stone

import java.io.IOException

final class ParseException private(msg: String, cause: Throwable) extends Exception

object ParseException {

  def apply(msg: String = null, cause: Throwable = null): ParseException = new ParseException(msg, cause)

  def apply(e: IOException): ParseException = apply(cause = e)
  def apply(msg: String): ParseException = apply(msg = msg)
  def apply(msg: String, token: Token): ParseException = apply(msg = s"syntax error around ${location(token)}. $msg")
  def apply(token: Token): ParseException = apply("", token)

  private def location(token: Token): String =
    if (token == Token.EOF)
      "the last line"
    else
      s""""${token.getText}" at line ${token.getLineNumber}"""
}
