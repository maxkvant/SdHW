package ru.spbau.maxim.Parser
import ru.spbau.maxim.Model

import scala.util.matching.Regex
import scala.util.parsing.combinator._

class CommandParser extends JavaTokenParsers {
  override def skipWhitespace: Boolean = true

  def echo: Parser[Echo] = "echo" ~> stringsInput ^^ { input => Echo(input) }

  def wc: Parser[Wc] = "wc" ~> (stringsInput1 | stdInput) ^^ { input => Wc(input) }

  def cat: Parser[Cat] = "cat" ~> (stringsInput1 | stdInput) ^^ { input => Cat(input) }

  def pwd: Parser[Pwd.type] = "pwd" ^^ { _ => Pwd }

  def exit: Parser[Exit.type] = "exit" ^^ { _ => Exit }

  def assignment: Parser[Assignment] = (variableName <~ "=") ~ token <~ stdInput ^^ {
    case variable ~ str => Assignment(variable, str)
  }

  def command: Parser[Command] = assignment | echo | wc | cat | pwd | exit | externalCommand

  def externalCommand: Parser[ExternalCommand] = "Process" ~> stringsInput1 <~ stdInput ^^ { str => ExternalCommand(str)}

  private def variableName: Parser[String] = Preprocessor.variableRegex

  private def token: Parser[String] = "(\"[^\"]*\"|'[^']*')".r ^^ { str => str.substring(1, str.length - 1) } | "\\S+".r

  private def stdInput: Parser[StdIn.type ] = "\\s*\\z".r ^^ { _ => StdIn }

  private def stringsInput1: Parser[StringsInput] = rep1(token) <~ stdInput ^^ { strs => StringsInput(strs) }

  private def stringsInput: Parser[StringsInput] = rep(token) <~ stdInput ^^ { strs => StringsInput(strs) }
}

object CommandParser extends CommandParser {
  def parse(string: String): Command = {
    parse(command, string) match {
      case Success(command: Command, _) => command
      case error => throw new IllegalArgumentException(error.toString)
    }
  }

  def apply: String => Seq[Command] = Preprocessor.apply(_).map(parse)
}