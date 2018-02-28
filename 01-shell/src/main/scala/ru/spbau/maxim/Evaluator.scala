package ru.spbau.maxim

import java.io.File

import ru.spbau.maxim.Parser._

class Evaluator {
  private var continue = true

  def evaluate(command: Command, stdIn: String): String = {
    def inputTexts(input: CommandInput): Seq[String] = {
      input match {
        case StdIn => List(stdIn)
        case StringsInput(strs) =>
          strs.map {file => scala.io.Source.fromFile(file).getLines().mkString("\n") }
      }
    }

    command match {
      case echo: Echo => echo.input match {
        case StringsInput(strs) => strs.mkString(" ")
      }

      case Pwd => new File(".").getCanonicalPath

      case Wc(input) => inputTexts(input).map { text =>
        val lines = text.split("\n").length
        val words = text.split("\\s").length
        s"$lines $words ${text.length}"
      }.mkString("\n")

      case Cat(input) => inputTexts(input).mkString("\n")

      case Exit =>
        continue = false
        "Exit"

      case Assignment(name, str) =>
        Model.env += (name -> str)
        ""

      case ExternalCommand(cmd) =>
        import scala.sys.process._
        cmd.strs.lineStream.mkString("\n")
    }
  }

  def evaluate(commands: Seq[Command]): String = {
    var lastOutput: String = ""
    for (command <- commands) {
      if (continue) {
        lastOutput = evaluate(command, lastOutput)
      }
    }
    lastOutput
  }

  def loop(): Unit = {
    while (continue) {
      val commandStr: String = scala.io.StdIn.readLine()
      val commands: Seq[Command] = CommandParser.apply(commandStr)
      println(commands)
      val output: String = evaluate(commands)
      println(output)
    }
  }
}
