package ru.spbau.maxim.parser

import ru.spbau.maxim.commands._
import org.rogach.scallop.{ScallopConf, ScallopOption}

/** Contains parsers, which parses commands
  */
class CommandParsers extends CommandParsingPrimitives  {
  override def skipWhitespace: Boolean = true

  /** Parser for all commands
    */
  def command: Parser[Command] = assignment | echo | wc | cat | pwd | exit | externalCommand | grep

  def echo: Parser[Echo] = "echo" ~> stringArgs ^^ { args => Echo(args) }

  def wc: Parser[Wc] = "wc" ~> stringArgs ^^ { files => Wc(files) }

  def cat: Parser[Cat] = "cat" ~> stringArgs ^^ { files => Cat(files) }

  def pwd: Parser[Pwd.type] = ("pwd" <~ emptyInput) ^^ { _ => Pwd }

  def exit: Parser[Exit.type] = "exit" ^^ { _ => Exit }

  def assignment: Parser[Assignment] = (variableName <~ "=") ~ token <~ emptyInput ^^ {
    case variable ~ str => Assignment(variable, str)
  }

  def externalCommand: Parser[ExternalCommand] =
    "Process" ~> stringArgs1 <~ emptyInput ^^ { args => ExternalCommand(args) }

  def grep: Parser[Grep] = "grep" ~> (stringArgs1 ^^ grepArgs)

  private def grepArgs(args: Seq[String]): Grep = {
    val argsParser = new GrepArgs(args)
    Grep(
      pattern = argsParser.regex(),
      linesAfterMatch = argsParser.A(),
      matchOnlyWords = argsParser.w(),
      caseInsensitive = argsParser.i(),
      files = argsParser.files()
    )
  }

  private class GrepArgs(args: Seq[String]) extends ScallopConf(args) {
    val A: ScallopOption[Int] = opt[Int]("A", default = Some(0), required = false, validate = _ >= 0)
    val w: ScallopOption[Boolean] = opt[Boolean]("w", default = Some(false), required = false)
    val i: ScallopOption[Boolean] = opt[Boolean]("i", default = Some(false), required = false)
    val regex: ScallopOption[String] = trailArg[String](required = true)
    val files: ScallopOption[List[String]] = trailArg[List[String]](required = false, default = Some(List()))

    verify()
  }
}
