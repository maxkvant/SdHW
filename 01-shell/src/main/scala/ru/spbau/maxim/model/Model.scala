package ru.spbau.maxim.model

import scala.collection.mutable

/** Environment trait
  */
trait Model {
  private var finished = false

  /** sets value of variable, named variableName
    */
  def putEnv(variableName: String, value: String): Unit

  /** returns value fo variable, if variable exists, otherwise returns empty string
    */
  def getEnvValue(string: String): String

  /** returns true if execution should be finished
    */
  def isFinished: Boolean = finished

  /** sets flag, which means execution should be finished
    */
  def finish(): Unit = {
    finished = true
  }
}


