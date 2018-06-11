package ru.spbau.maxim.Parser

import ru.spbau.maxim.model.Model

import scala.util.matching.Regex
import scala.util.parsing.combinator.JavaTokenParsers

class Preprocessor(model: Model) extends JavaTokenParsers {
  override val skipWhitespace = false

  private def replacerEnv: Parser[String] = {
    rep("'.*'".r | ("$" ~> Preprocessor.variableRegex ^^ model.getEnvValue) | "[^$]".r) ^^ {
      _.mkString
    }
  }

  private def replaceEnv(string: String): String = {
    parse(replacerEnv, string) match {
      case Success(str, _) => str
      case error => throw new IllegalArgumentException(error.toString)
    }
  }

  /** Does preprocessing:
    * 1. splits by pipes
    * 2. substitutes $variable
    */
  def process(command: String): Seq[String] = command.split("\\|").map(replaceEnv)
}

object Preprocessor {
  val variableRegex: Regex = "[_A-Za-z0-9]*".r
}