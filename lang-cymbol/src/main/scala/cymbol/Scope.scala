package cymbol

trait Scope {
  def name: String
  def enclosingScope: Option[Scope]
  def define(sym: Symbol): Unit
  def resolve(name: String): Option[Symbol]
}
