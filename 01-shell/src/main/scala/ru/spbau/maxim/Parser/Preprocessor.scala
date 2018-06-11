package ru.spbau.maxim.Parser

import scala.util.matching.Regex

trait Preprocessor {
  def process(command: String): Seq[String]
}

object Preprocessor {
  val variableRegex: Regex = "[_A-Za-z0-9]*".r
}