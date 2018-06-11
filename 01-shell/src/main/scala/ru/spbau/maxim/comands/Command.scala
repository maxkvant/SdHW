package ru.spbau.maxim.comands

import java.io.{File, PrintWriter}

import ru.spbau.maxim.comands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

/** Basic trait for any command
  */
trait Command {
  def execute(stdIn: String)(implicit model: Model): String
}

object Command {
  type StringArgs = Seq[String]
}

