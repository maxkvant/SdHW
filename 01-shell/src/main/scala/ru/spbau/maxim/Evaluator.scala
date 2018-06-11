package ru.spbau.maxim

import ru.spbau.maxim.Parser._
import ru.spbau.maxim.comands._
import ru.spbau.maxim.model.Model

/** Can evaluate command, pipeline
  * Can start REPL
 */
class Evaluator(private val curModel: Model) {
  private val preprocessor: Preprocessor = new PreprocessorImpl(curModel)
  private val parser: CommandParser = CommandParser
  implicit val model: Model = curModel

  /** Evaluates command
    */
  def evaluate(command: Command, stdIn: String): String = command.execute(stdIn)

  /** Evaluates pipeline
    * returns result of latest command
    */
  def evaluatePipeline(commands: Seq[Command]): String = {
    var lastOutput: String = ""
    for (command <- commands) {
      if (!model.isFinished) {
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
      val commandsStrs: Seq[String] = preprocessor.process(commandStr)
      val commands: Seq[Command] = commandsStrs.map(parser.parseCommand)
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
    while (!model.isFinished) {
      val commandStr: String = scala.io.StdIn.readLine()
      val output = evaluatePipeline(commandStr)
      println(output)
    }
  }
}
