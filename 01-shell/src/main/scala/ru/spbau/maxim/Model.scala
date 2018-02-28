package ru.spbau.maxim

import scala.collection.mutable

object Model {
  private val env: mutable.Map[String, String] = mutable.Map().withDefaultValue("")

  def putEnv(name: String, value: String): Unit = {
    env += (name -> value)
  }

  def getEnvValue: String => String = env.apply
}
