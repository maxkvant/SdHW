package ru.spbau.maxim

import ru.spbau.maxim.model._

object Main {
  def main(args: Array[String]): Unit = {
    new Evaluator(new ModelImpl()).loop()
  }
}
