package basics

import java.io.InputStream
import java.nio.charset.Charset
import scala.collection.{ Map => ImmutableMap }
import scala.collection.mutable.{ Map => MutableMap }
import scala.io.Source

/**
 *
 */
object WordCountMutableMap {

  def getWordCount(in: InputStream): ImmutableMap[String, Int] = {
    val source = Source.fromInputStream(in)
    val wordCountMap = MutableMap.empty[String, Int].withDefaultValue(0)
    val lines = source.getLines
    val words = lines.flatMap(l => l.split(" "))
    words.foreach { word => wordCountMap(word.toUpperCase) += 1 }
    source.close()
    ImmutableMap() ++ wordCountMap
  }

}