package ru.spbau.maxim.commands

import ru.spbau.maxim.commands.Command.StringArgs
import ru.spbau.maxim.model.Model

/** Prints args, separated by space
  */
case class Echo(args: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = args.mkString(" ")
}
