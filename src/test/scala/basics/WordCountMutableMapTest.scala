package basics

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.Matchers._
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class WordCountMutableMapTest extends FunSuite {
  
  test("words.txt counts") {
    val wordsUrl = "/inputs/words.txt"
    val wordsIn = getClass.getResourceAsStream(wordsUrl)
    val wordsMap = WordCountMutableMap.getWordCount(wordsIn)
    //expected words capitalized!
    val expectedPairs = List("1C" -> 1, "2C" -> 2, "3C" -> 3)
    assert(wordsMap.size === expectedPairs.size)
    for(expectedPair <- expectedPairs) {
      val (key, value) = expectedPair
      assert(wordsMap.contains(key))
      assert(wordsMap(key) === value)
    }
    wordsIn.close()
  }
  
}