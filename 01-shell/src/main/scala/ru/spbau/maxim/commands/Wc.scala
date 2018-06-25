package ru.spbau.maxim.commands

import ru.spbau.maxim.commands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

/** If files ins't empty seq, counts lines, words, symbols in files,
  * Otherwise counts lines, words, symbols in stdin
  */
case class Wc(files: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    val inputTexts: Seq[String] =
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

    inputTexts.map { text =>
      val lines = text.split("\n").length
      val words = text.split("\\s").count(!_.isEmpty)
      s"$lines $words ${text.length}"
    }.mkString("\n")
  }
}
