package ru.spbau.maxim.parser

import ru.spbau.maxim.commands.Command.StringArgs
import scala.util.parsing.combinator.JavaTokenParsers

/** Contains parsers, which can be used in command parsing
  */
class CommandParsingPrimitives extends JavaTokenParsers {
    override def skipWhitespace: Boolean = true

    def variableName: Parser[String] = Preprocessor.variableRegex

    def token: Parser[String] = "(\"[^\"]*\"|'[^']*')".r ^^ { str => str.substring(1, str.length - 1) } | "\\S+".r

    def emptyInput: Parser[Unit] = "\\s*\\z".r ^^ { _ => Unit }

    def stringArgs1: Parser[StringArgs] = rep1(token) <~ emptyInput

    def stringArgs: Parser[StringArgs] = rep(token) <~ emptyInput
  }