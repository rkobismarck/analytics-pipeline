package com.fake

import scala.util.Random

package object utils {

  val loremBlock = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
  val loremWords = loremBlock.split(" ")

  val r = new Random()

  def getWords(words: Int): String = {
    val paragraphCount = words / loremWords.size
    val wordCount = words % loremWords.size

    val wordsPara = loremWords.slice(0, wordCount).mkString(" ")

    val paras = for (i <- 0 until paragraphCount) yield (loremBlock)

    val fullList = paras.toList ::: wordsPara :: Nil
    fullList.mkString(" ")
  }

  def getRandomNumberOfWords(range: Range) = {
    getWords(r.nextInt(range.length) + range.start)
  }
}
