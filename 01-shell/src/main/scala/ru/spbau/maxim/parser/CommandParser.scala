package ru.spbau.maxim.parser

import ru.spbau.maxim.commands.Command

trait CommandParser {
  /** Parses single command
    */
  def parseCommand(string: String): Command
}

/** default implementation for CommandParser
  */
object CommandParser extends CommandParser {
  private val parser = new CommandParsers

  def parseCommand(string: String): Command = {
    parser.parse(parser.command, string) match {
      case parser.Success(command: Command, _) => command
      case error => throw new IllegalArgumentException(error.toString)
    }
  }
}