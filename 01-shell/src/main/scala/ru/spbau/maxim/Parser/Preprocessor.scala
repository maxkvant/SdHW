package ru.spbau.maxim.Parser

import ru.spbau.maxim.Model

import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

object Preprocessor extends JavaTokenParsers {
  val variableRegex: Regex = "[_A-Za-z0-9]*".r
  override val skipWhitespace = false

  private def replacerEnv: Parser[String] = {
    rep("'.*'".r | ("$" ~> variableRegex ^^ Model.getEnvValue) | "[^$]".r) ^^ {
      _.mkString
    }
  }

  private def replaceEnv(string: String): String = {
    println(string)
    parse(replacerEnv, string) match {
      case Success(str, _) => str
      case error => throw new IllegalArgumentException(error.toString)
    }
  }

  def apply(command: String): Seq[String] = command.split("\\|").map(replaceEnv)
}