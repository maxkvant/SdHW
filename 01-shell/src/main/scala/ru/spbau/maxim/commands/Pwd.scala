package ru.spbau.maxim.commands

import java.io.File

import ru.spbau.maxim.model.Model

/** Prints current directory
  */
case object Pwd extends Command {
  override def execute(stdIn: String)(implicit model: Model): String =
    new File(".").getCanonicalPath
}
