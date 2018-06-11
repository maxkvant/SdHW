package ru.spbau.maxim.comands

case class CommandExecutionException(
  private val message: String = "",
  private val cause: Throwable = None.orNull
) extends Exception(message, cause)
