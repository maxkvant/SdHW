package ru.spbau.maxim

import scala.collection.mutable

/** Stores environment
  */
object Model {
  private val env: mutable.Map[String, String] = mutable.Map().withDefaultValue("")

  /** sets value of variable, named variableName
    */
  def putEnv(variableName: String, value: String): Unit = {
    env += (variableName -> value)
  }

  /** returns value fo variable, if variable exists, otherwise returns empty string
    */
  def getEnvValue: String => String = env.apply
}
