package ru.spbau.maxim.Parser

sealed trait Command {
  val input: CommandInput = StdIn
}

sealed trait CommandInput
object StdIn extends CommandInput
case class StringsInput(strs: Seq[String]) extends CommandInput

case class Echo(override val input: StringsInput) extends Command

case class Wc(override val input: CommandInput) extends Command

case object Pwd extends Command

case object Exit extends Command

case class Cat(override val input: CommandInput) extends Command

case class Assignment(variable: String, value: String) extends Command

case class ExternalCommand(cmd: String) extends Command