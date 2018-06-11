package ru.spbau.maxim.parser

import ru.spbau.maxim.commands._

/** Contains parsers, which parses commands
  */
class CommandParsers extends CommandParsingPrimitives  {
  override def skipWhitespace: Boolean = true

  /** Parser for all commands
    */
  def command: Parser[Command] = assignment | echo | wc | cat | pwd | exit | externalCommand

  def echo: Parser[Echo] = "echo" ~> stringArgs ^^ { args => Echo(args) }

  def wc: Parser[Wc] = "wc" ~> stringArgs ^^ { files => Wc(files) }

  def cat: Parser[Cat] = "cat" ~> stringArgs ^^ { files => Cat(files) }

  def pwd: Parser[Pwd.type] = ("pwd" <~ emptyInput) ^^ { _ => Pwd }

  def exit: Parser[Exit.type] = "exit" ^^ { _ => Exit }

  def assignment: Parser[Assignment] = (variableName <~ "=") ~ token <~ emptyInput ^^ {
    case variable ~ str => Assignment(variable, str)
  }

  private def externalCommand: Parser[ExternalCommand] =
    "Process" ~> stringArgs1 <~ emptyInput ^^ { args => ExternalCommand(args) }
}
