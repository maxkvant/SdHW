package ru.spbau.maxim.comands

import java.io.PrintWriter

import ru.spbau.maxim.comands.Command.StringArgs
import ru.spbau.maxim.model.Model

import scala.io.Source

/** Executes external command
  */
case class ExternalCommand(tokens: StringArgs) extends Command {
  override def execute(stdIn: String)(implicit model: Model): String = {
    import scala.sys.process._

    try {
      var output = ""
      stringSeqToProcess(tokens).run(new ProcessIO(
        in => {
          val writer = new PrintWriter(in)
          writer.print(stdIn)
          writer.close()
        },
        out => {
          val src = Source.fromInputStream(out)
          output = src.getLines().mkString("\n")
          src.close()
        },
        _.close()
      )).exitValue()
      output
    } catch {
      case e: Exception =>
        throw CommandExecutionException("command execution error: " + e.getMessage, e)
    }

  }
}
