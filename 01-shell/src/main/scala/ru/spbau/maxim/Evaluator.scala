package ru.spbau.maxim

import java.io.{File, PrintWriter}

import ru.spbau.maxim.Parser.Command.StringArgs
import ru.spbau.maxim.Parser._

import scala.io.Source

/** Can evaluate command, pipeline
  * Can start REPL
 */
class Evaluator {
  private var continue = true

  /** Evaluates command
    */
  def evaluate(command: Command, stdIn: String): String = {
    def inputTexts(files: StringArgs): Seq[String] = {
      files match {
        case Nil => stdIn :: Nil
        case _ => files.map { file => Source.fromFile(file).getLines().mkString("\n") }
      }
    }

    command match {
      case echo: Echo => echo.args.mkString(" ")

      case Pwd => new File(".").getCanonicalPath

      case Wc(args) => inputTexts(args).map { text =>
        val lines = text.split("\n").length
        val words = text.split("\\s").count(!_.isEmpty)
        s"$lines $words ${text.length}"
      }.mkString("\n")

      case Cat(files) => inputTexts(files).mkString("\n")

      case Exit =>
        continue = false
        "Exit"

      case Assignment(name, str) =>
        Model.putEnv(name, str)
        ""

      case ExternalCommand(tokens) =>
        import scala.sys.process._
        var output = ""
        stringSeqToProcess(tokens).run(new ProcessIO(
          in => {
            val writer = new PrintWriter(in)
            writer.print(stdIn)
            writer.close()
          },
          out => {
            val src = Source.fromInputStream(out)
            output = src.getLines().mkString("\n")
            src.close()
          },
          _.close()
        ))
        output
    }
  }

  /** Evaluates pipeline
    * returns result of latest command
    */
  def evaluatePipeline(commands: Seq[Command]): String = {
    var lastOutput: String = ""
    for (command <- commands) {
      if (continue) {
        lastOutput = evaluate(command, lastOutput)
      }
    }
    lastOutput
  }

  /** Parses and evaluates pipeline
    * returns result of latest command or error message
    */
  def evaluatePipeline(commandStr: String): String = {
    try {
      val commands: Seq[Command] = CommandParser.parse(commandStr)
      try {
        evaluatePipeline(commands)
      } catch {
        case e: Exception => s"Error executing command:\n${e.getMessage}"
      }
    } catch {
      case e: Exception => "parse error"
    }
  }

  /** runs REPL
    */
  def loop(): Unit = {
    while (continue) {
      val commandStr: String = scala.io.StdIn.readLine()
      val output = evaluatePipeline(commandStr)
      println(output)
    }
  }
}
