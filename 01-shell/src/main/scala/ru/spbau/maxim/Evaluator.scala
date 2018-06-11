package ru.spbau.maxim

import ru.spbau.maxim.parser._
import ru.spbau.maxim.commands._
import ru.spbau.maxim.model.Model

/** Can evaluate command, pipeline
  */
class Evaluator(
  private val preprocessor: Preprocessor,
  private val parser: CommandParser,
  private val curModel: Model
) {
  private implicit val model: Model = curModel


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
    var res = ""
    val commands: Seq[Command] = try {
      val commandsStrs: Seq[String] = preprocessor.process(commandStr)
      commandsStrs.map(parser.parseCommand)
    } catch {
      case e: Exception =>
        res = "parse error"
        Seq[Command]()
    }
    if (res == "") {
      res = try {
        evaluatePipeline(commands)
      } catch {
        case e: CommandExecutionException => e.getMessage
      }
    }
    res
  }

  def finished: Boolean = model.isFinished
}
