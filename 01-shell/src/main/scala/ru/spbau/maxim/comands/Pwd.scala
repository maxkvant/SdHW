package ru.spbau.maxim.comands

import java.io.File

import ru.spbau.maxim.model.Model

case object Pwd extends Command {
  override def execute(stdIn: String)(implicit model: Model): String =
    new File(".").getCanonicalPath
}
