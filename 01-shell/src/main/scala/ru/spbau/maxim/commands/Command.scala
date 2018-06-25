package ru.spbau.maxim.commands

import ru.spbau.maxim.model.Model

/** Basic trait for any command
  */
trait Command {
  def execute(stdIn: String)(implicit model: Model): String
}

object Command {
  type StringArgs = Seq[String]
}

