package ru.spbau.maxim

import ru.spbau.maxim.commands.{Command, CommandExecutionException}
import ru.spbau.maxim.model.{Model, ModelImpl}
import ru.spbau.maxim.parser.{CommandParser, Preprocessor, PreprocessorImpl}

/**
  * Evaluator implementation
  */
class EvaluatorImpl(curModel: Model) extends Evaluator {
  private val parser: CommandParser = CommandParser
  private implicit val model: Model = curModel
  private val preprocessor: Preprocessor = new PreprocessorImpl(model)

  override def evaluate(command: Command, stdIn: String): String = command.execute(stdIn)

  override def evaluatePipeline(commandStr: String): String = {
    var res = ""
    val commands: Seq[Command] = try {
      val commandsStrs: Seq[String] = preprocessor.process(commandStr)
      commandsStrs.map(parser.parseCommand)
    } catch {
      case e: Error =>
        res = "parse error"
        Seq[Command]()
      case e: Exception =>
        res = "parsing exception"
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

  override def finished: Boolean = model.isFinished
}
