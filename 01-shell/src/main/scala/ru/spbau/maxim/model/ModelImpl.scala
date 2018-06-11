package ru.spbau.maxim.model

import scala.collection.mutable

/** Environment implementation, wraps mutable map
  */
class ModelImpl extends Model {
  private val env: mutable.Map[String, String] = mutable.Map().withDefaultValue("")

  def putEnv(variableName: String, value: String): Unit = {
    require(!isFinished)
    env += (variableName -> value)
  }

  override def getEnvValue(variableName: String) = env(variableName)
}
