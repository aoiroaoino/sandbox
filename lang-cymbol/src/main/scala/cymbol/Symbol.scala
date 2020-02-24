package cymbol

sealed abstract class Symbol extends Product with Serializable {
  def name: String
  def tpe: Option[Type]

  override def toString = tpe.fold(name)(t => s"<$name:${t.name}>")
}

object Symbol {
  final case class Variable(name: String, tpe: Option[Type]) extends Symbol
  object Variable {
    def apply(name: String): Variable            = new Variable(name, None)
    def apply(name: String, tpe: Type): Variable = new Variable(name, Some(tpe))
  }
  final case class BuiltInType(name: String, tpe: Option[Type]) extends Symbol with Type
  object BuiltInType {
    def apply(name: String): BuiltInType            = new BuiltInType(name, None)
    def apply(name: String, tpe: Type): BuiltInType = new BuiltInType(name, Some(tpe))
  }
}
