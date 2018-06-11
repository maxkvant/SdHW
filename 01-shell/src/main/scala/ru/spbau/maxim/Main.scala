package ru.spbau.maxim

import ru.spbau.maxim.Parser.CommandParser
import ru.spbau.maxim.model._

object Main {
  def main(args: Array[String]): Unit = {
    val model = new ModelImpl()
    val evaluator = new Evaluator(CommandParser, model)

    while (!evaluator.finished) {
      val commandStr: String = scala.io.StdIn.readLine()
      val output = evaluator.evaluatePipeline(commandStr)
      println(output)
    }
  }
}
