package basics

object ForComprehensions {

  /**
   * <- generator expression
   * if - guard (can have multiple if lines to and)
   * can create immutable vars with = assignment in for
   * yield provides new collection with elements
   */
  def main(args: Array[String]): Unit = {
    
    val names = List("Anna","Bertha","Carla","Dieter")
    val capFilter = for {
      name <- names if (name.startsWith("A") || name.startsWith("D"))
      allCapsName = name.toUpperCase()
    } yield allCapsName
    println(capFilter)
  }

}