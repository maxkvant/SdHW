package ru.spbau.maxim.comands

import ru.spbau.maxim.comands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

case class Wc(files: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    val inputTexts: Seq[String] =
      files match {
        case Nil => stdIn :: Nil
        case _ => files.map { file => Source.fromFile(file).getLines().mkString("\n") }
      }

    inputTexts.map { text =>
      val lines = text.split("\n").length
      val words = text.split("\\s").count(!_.isEmpty)
      s"$lines $words ${text.length}"
    }.mkString("\n")
  }
}
