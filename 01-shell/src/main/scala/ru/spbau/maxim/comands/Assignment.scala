package ru.spbau.maxim.comands

import ru.spbau.maxim.model.Model

case class Assignment(variable: String, value: String) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    model.putEnv(variable, value)
    ""
  }
}
