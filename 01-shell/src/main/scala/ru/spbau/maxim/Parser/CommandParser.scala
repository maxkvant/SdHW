package ru.spbau.maxim.Parser

import ru.spbau.maxim.comands.Command

trait CommandParser {
  def parseCommand(string: String): Command
}

/**
  * default implementation for CommandParser
  */
object CommandParser extends CommandParser {
  private val parser = new CommandParsers

  /** Parses single command
    */
  def parseCommand(string: String): Command = {
    parser.parse(parser.command, string) match {
      case parser.Success(command: Command, _) => command
      case error => throw new IllegalArgumentException(error.toString)
    }
  }
}