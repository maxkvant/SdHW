package ru.spbau.maxim.Parser
import ru.spbau.maxim.Parser.Command.StringArgs

import scala.util.parsing.combinator._

/** Contains parsers, which can parse commands
  */
class CommandParser extends JavaTokenParsers {
  override def skipWhitespace: Boolean = true

  /** Parser for all commands
    */
  def command: Parser[Command] = assignment | echo | wc | cat | pwd | exit | externalCommand

  private def echo: Parser[Echo] = "echo" ~> stringArgs ^^ { args => Echo(args) }

  private def wc: Parser[Wc] = "wc" ~> stringArgs ^^ { files => Wc(files) }

  private def cat: Parser[Cat] = "cat" ~> stringArgs ^^ { files => Cat(files) }

  private def pwd: Parser[Pwd.type] = ("pwd" <~ emptyInput) ^^ { _ => Pwd }

  private def exit: Parser[Exit.type] = "exit" ^^ { _ => Exit }

  private def assignment: Parser[Assignment] = (variableName <~ "=") ~ token <~ emptyInput ^^ {
    case variable ~ str => Assignment(variable, str)
  }

  private def externalCommand: Parser[ExternalCommand] =
    "Process" ~> stringArgs1 <~ emptyInput ^^ { args => ExternalCommand(args) }

  private def variableName: Parser[String] = Preprocessor.variableRegex

  private def token: Parser[String] = "(\"[^\"]*\"|'[^']*')".r ^^ { str => str.substring(1, str.length - 1) } | "\\S+".r

  private def emptyInput: Parser[StdIn.type ] = "\\s*\\z".r ^^ { _ => StdIn }

  private def stringArgs1: Parser[StringArgs] = rep1(token) <~ emptyInput

  private def stringArgs: Parser[StringArgs] = rep(token) <~ emptyInput
}

object CommandParser extends CommandParser {
  /** Parses single command
    */
  def parseCommand(string: String): Command = {
    parse(command, string) match {
      case Success(command: Command, _) => command
      case error => throw new IllegalArgumentException(error.toString)
    }
  }

  /** Parses pipeline (command1 | command2 | command3 ...)
    */
  def parse: String => Seq[Command] = Preprocessor.apply(_).map(parseCommand)
}