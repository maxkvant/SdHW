package ru.spbau.maxim.commands

import ru.spbau.maxim.model.Model

/** Exits cli
  */
case object Exit extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    model.finish()
    "Exit"
  }
}
