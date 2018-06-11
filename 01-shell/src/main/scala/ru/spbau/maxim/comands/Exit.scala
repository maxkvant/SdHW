package ru.spbau.maxim.comands

import ru.spbau.maxim.model.Model

case object Exit extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    model.finish()
    "Exit"
  }
}
