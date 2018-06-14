package ru.spbau.maxim.commands
import ru.spbau.maxim.model.Model

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.matching.Regex

/** searches for pattern in input
  * returns matching lines
  */
case class Grep(pattern: String,
                linesAfterMatch: Int = 0,
                caseInsensitive: Boolean = false,
                matchOnlyWords: Boolean = false,
                files: List[String] = List()
) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    def inputTexts: Seq[String] =
      files match {
        case Nil => stdIn :: Nil
        case _ => files.map { file =>
          try {
            Source.fromFile(file).getLines().mkString("\n")
          } catch {
            case e: Exception =>
              throw CommandExecutionException(s"error while reading $file: " + e.getMessage, e)
          }
        }
      }

    val regex: Regex = {
      var patternRenew = pattern
      if (caseInsensitive) {
        patternRenew = patternRenew.toLowerCase()
      }
      if (matchOnlyWords) {
        patternRenew = s"\\b$pattern\\b"
      }
      try {
        patternRenew.r
      } catch {
        case e: Exception =>
          throw CommandExecutionException(s"wrong regex: '$pattern'", e)
      }
    }

    val res = ArrayBuffer[String]()

    for (text <- inputTexts) {
      val lines: Seq[String] = text.split("\n")
      var lastMatch: Int = -1

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
    }
    res.mkString("\n")
  }
}
