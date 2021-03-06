package ru.spbau.maxim.commands

import ru.spbau.maxim.commands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

/** If files ins't empty seq, prints content from files,
  * Otherwise prints stdin content
  */
case class Cat(files: StringArgs) extends Command {
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


    inputTexts.mkString("\n")
  }
}
