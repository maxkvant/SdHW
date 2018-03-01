package ru.spbau.maxim.Parser
import scala.util.parsing.combinator._

/** Contains parsers, which can parse commands
  */
class CommandParser extends JavaTokenParsers {
  override def skipWhitespace: Boolean = true

  /** Parser for all commands
    */
  def command: Parser[Command] = assignment | echo | wc | cat | pwd | exit | externalCommand

  private def echo: Parser[Echo] = "echo" ~> stringsInput ^^ { input => Echo(input) }

  private def wc: Parser[Wc] = "wc" ~> (stringsInput1 | emptyInput) ^^ { input => Wc(input) }

  private def cat: Parser[Cat] = "cat" ~> (stringsInput1 | emptyInput) ^^ { input => Cat(input) }

  private def pwd: Parser[Pwd.type] = ("pwd" <~ emptyInput) ^^ { _ => Pwd }

  private def exit: Parser[Exit.type] = "exit" ^^ { _ => Exit }

  private def assignment: Parser[Assignment] = (variableName <~ "=") ~ token <~ emptyInput ^^ {
    case variable ~ str => Assignment(variable, str)
  }

  private def externalCommand: Parser[ExternalCommand] = "Process" ~> stringsInput1 <~ emptyInput ^^ { str => ExternalCommand(str)}

  private def variableName: Parser[String] = Preprocessor.variableRegex

  private def token: Parser[String] = "(\"[^\"]*\"|'[^']*')".r ^^ { str => str.substring(1, str.length - 1) } | "\\S+".r

  private def emptyInput: Parser[StdIn.type ] = "\\s*\\z".r ^^ { _ => StdIn }

  private def stringsInput1: Parser[StringsInput] = rep1(token) <~ emptyInput ^^ { strs => StringsInput(strs) }

  private def stringsInput: Parser[StringsInput] = rep(token) <~ emptyInput ^^ { strs => StringsInput(strs) }
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