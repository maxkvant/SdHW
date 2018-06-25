package ru.spbau.maxim

import ru.spbau.maxim.commands._

/** Can evaluate command, pipeline
  */
trait Evaluator {
  /** Evaluates command
    */
  def evaluate(command: Command, stdIn: String): String

  /** Parses and evaluates pipeline
    * returns result of latest command or error message
    */
  def evaluatePipeline(commandStr: String): String

  /** is execution finished?
    */
  def finished: Boolean

  /** Evaluates pipeline
    * returns result of latest command
    */
  def evaluatePipeline(commands: Seq[Command]): String = {
    var lastOutput: String = ""
    for (command <- commands) {
      if (!finished) {
        lastOutput = evaluate(command, lastOutput)
      }
    }
    lastOutput
  }
}
