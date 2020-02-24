package cymbol

import scala.collection.mutable

class SymbolTable extends Scope {

  private[this] val symbols: mutable.HashMap[String, Symbol] = mutable.HashMap.empty

  def initTypeSystem(): Unit = {
    define(Symbol.BuiltInType("int"))
    define(Symbol.BuiltInType("float"))
  }

  def currentScopeSymbols: Map[String, Symbol] = symbols.toMap

  override val name = "global"

  override val enclosingScope = None

  override def define(sym: Symbol): Unit = symbols.put(sym.name, sym)

  override def resolve(name: String): Option[Symbol] = symbols.get(name)

  override def toString: String = s"$name:$symbols"
}
