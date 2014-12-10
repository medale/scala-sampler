package basics

//Functional Programming in Scala, Week 5.2 Higher Order Functions
//Solutions to in-lecture exercises
object CombinatorFunctions {

  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case x :: xs1 => {
      val (same, diff) = xs.span(y => y == x)
      same :: pack(diff)
    }
  }

  def encode[T](xs: List[T]): List[(T, Int)] = {
    val packed = pack(xs)
    packed.map {
      case ys => (ys.head, ys.size)
    }
  }

  def main(args: Array[String]) {
    println("Combinators...")
    println("span (takeWhile, dropWhile)")
    val packed = pack(List("a", "a", "a", "b", "c", "c", "a"))
    println(packed)

    val encoded = encode(List("a", "a", "a", "b", "c", "c", "a"))
    println(encoded)
  }
}