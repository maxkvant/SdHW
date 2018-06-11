package ru.spbau.maxim.comands

import ru.spbau.maxim.comands.Command.StringArgs
import ru.spbau.maxim.model.Model

case class Echo(args: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = args.mkString(" ")
}
