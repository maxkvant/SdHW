package ru.spbau.maxim.parser

import org.scalatest.{FunSuite, Matchers}
import ru.spbau.maxim.commands._
import ru.spbau.maxim.model.{Model, ModelImpl}

class CommandParserTest extends FunSuite with Matchers {
  import CommandParser.parseCommand

  test("echoParsing") {
    parseCommand("echo 1  2 3") should be (Echo("1" :: "2" :: "3" :: Nil))

    parseCommand(" echo ") should be (Echo(Nil))

    parseCommand(" echo ' a ' \"b \" c ") should be (Echo(" a " :: "b " :: "c" :: Nil))
  }

  test("wcParsing") {
    parseCommand("wc build.sbt") should be (Wc("build.sbt" :: Nil))

    parseCommand(" wc ") should be (Wc(Nil))

    parseCommand(" wc 'буква а.txt' readme.md ") should be (Wc("буква а.txt" :: "readme.md" :: Nil))

    parseCommand(" wc \"'записки охотника'\" 'прив '") should be (Wc("'записки охотника'" :: "прив " :: Nil))
  }

  test("catParsing") {
    parseCommand("cat  build.sbt") should be (Cat("build.sbt" :: Nil))

    parseCommand(" cat  ") should be (Cat(Nil))

    parseCommand(" cat  'буква а.txt'  readme.md ") should be (Cat("буква а.txt" :: "readme.md" :: Nil))
  }

  test("pwdParsing") {
    parseCommand("pwd") should be (Pwd)

    parseCommand("   pwd ") should be (Pwd)

    intercept[IllegalArgumentException] {
      parseCommand("pwd 1 ")
    }
  }

  test("exitParsing") {
    parseCommand("exit") should be (Exit)
  }

  test("assignmentParsing") {
    parseCommand("a= 'hello \"world\"'") should be (Assignment("a", "hello \"world\""))

    intercept[IllegalArgumentException] {
      parseCommand("a=1 2 ")
    }
  }

  test("Process parsing") {
    parseCommand("Process wc") should be (ExternalCommand("wc" :: Nil))

    parseCommand("Process echo 1 2 3") should be (ExternalCommand("echo" :: "1" :: "2" :: "3" :: Nil))
  }

  test("pipelineParsing") {
    val model = new ModelImpl()
    val preprocessor = new PreprocessorImpl(model)

    preprocessor.process("cat build.sbt | wc | echo").map(CommandParser.parseCommand) should be (
      Cat("build.sbt" :: Nil) :: Wc(Nil) :: Echo(Nil) :: Nil
    )
  }
}
