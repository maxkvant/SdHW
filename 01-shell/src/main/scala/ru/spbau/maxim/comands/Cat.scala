package ru.spbau.maxim.comands

import ru.spbau.maxim.comands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

case class Cat(files: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    def inputTexts(files: StringArgs): Seq[String] = {
      files match {
        case Nil => stdIn :: Nil
        case _ => files.map { file => Source.fromFile(file).getLines().mkString("\n") }
      }
    }

    inputTexts(files).mkString("\n")
  }
}
