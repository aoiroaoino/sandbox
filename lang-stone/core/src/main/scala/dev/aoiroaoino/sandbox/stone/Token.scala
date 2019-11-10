package dev.aoiroaoino.sandbox.stone


/**
 * リスト3.1
 *
 * @param lineNumber
 */
abstract class Token(lineNumber: Int) {

  def getLineNumber: Int = lineNumber

  def isIdentifier: Boolean = false
  def isNumber: Boolean = false
  def isString: Boolean = false

  def getNumber: Int = throw new StoneException("not number token")
  def getText: String = ""
}

object Token {
  final val EOF = new Token(-1) {}
  final val EOL = "\\n"
}

/**
 * p.33, Lexer.java より切り出し
 */
final case class NumToken(line: Int, v: Int) extends Token(line) {
  override def isNumber: Boolean = true
  override def getText: String = v.toString
}
final case class IdToken(line: Int, id: String) extends Token(line) {
  override def isIdentifier: Boolean = true
  override def getText: String = id
}
final case class StrToken(line: Int, str: String) extends Token(line) {
  override def isString: Boolean = true
  override def getText: String = str
}

