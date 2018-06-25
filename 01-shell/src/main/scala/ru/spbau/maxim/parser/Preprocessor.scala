package ru.spbau.maxim.parser

import scala.util.matching.Regex

trait Preprocessor {
  /** Does preprocessing
    */
  def process(command: String): Seq[String]
}

object Preprocessor {
  val variableRegex: Regex = "[_A-Za-z0-9]*".r
}