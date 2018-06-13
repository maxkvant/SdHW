package ru.spbau.maxim.commands
import ru.spbau.maxim.model.Model

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

class Grep(val pattern: String,
           val linesAfterMatch: Int = 0,
           val caseInsensitive: Boolean = false,
           val matchOnlyWords: Boolean = false
) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    val regex: Regex = {
      var patternRenew = pattern
      if (caseInsensitive) {
        patternRenew = patternRenew.toLowerCase()
      }
      if (matchOnlyWords) {
        patternRenew = s"\\b$pattern\\b"
      }
      patternRenew.r
    }

    val lines: Seq[String] = stdIn.split("\n")
    var lastMatch: Int = -1
    val res = ArrayBuffer[String]()

    for (i <- lines.indices) {
      val line: String = lines(i)
      val searchLine = if (caseInsensitive) line.toLowerCase() else line
      val matches: Boolean = regex.findFirstIn(searchLine).isDefined
      if (matches) {
        lastMatch = i
      }
      if (matches || (lastMatch >= 0 && i <= linesAfterMatch + lastMatch)) {
        res += line
      }
    }
    res.mkString("\n")
  }
}
