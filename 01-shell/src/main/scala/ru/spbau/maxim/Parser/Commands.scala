package ru.spbau.maxim.Parser

import ru.spbau.maxim.Parser.Command.StringArgs

/** Basic trait for any command
  */
sealed trait Command

sealed trait CommandInput
object StdIn extends CommandInput
case class StringsInput(strings: Seq[String]) extends CommandInput

case class Echo(args: StringArgs) extends Command

case class Wc(files: StringArgs) extends Command

case object Pwd extends Command

case object Exit extends Command

case class Cat(files: StringArgs) extends Command

case class Assignment(variable: String, value: String) extends Command

case class ExternalCommand(tokens: StringArgs) extends Command

object Command {
  type StringArgs = Seq[String]
}