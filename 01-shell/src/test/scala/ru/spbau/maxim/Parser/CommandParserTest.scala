package ru.spbau.maxim.Parser

import org.scalatest.{FunSuite, Matchers}

class CommandParserTest extends FunSuite with Matchers {
  import CommandParser.parseCommand

  test("echoParsing") {
    parseCommand("echo 1  2 3") should be (Echo(StringsInput("1" :: "2" :: "3" :: Nil)))

    parseCommand(" echo ") should be (Echo(StringsInput(Nil)))

    parseCommand(" echo ' a ' \"b \" c ") should be (Echo(StringsInput(" a " :: "b " :: "c" :: Nil)))
  }

  test("wcParsing") {
    parseCommand("wc build.sbt") should be (Wc(StringsInput("build.sbt" :: Nil)))

    parseCommand(" wc ") should be (Wc(StdIn))

    parseCommand(" wc 'буква а.txt' readme.md ") should be (Wc(StringsInput("буква а.txt" :: "readme.md" :: Nil)))

    parseCommand(" wc \"'записки охотника'\" 'прив '") should be (Wc(StringsInput("'записки охотника'" :: "прив " :: Nil)))
  }

  test("catParsing") {
    parseCommand("cat  build.sbt") should be (Cat(StringsInput("build.sbt" :: Nil)))

    parseCommand(" cat  ") should be (Cat(StdIn))

    parseCommand(" cat  'буква а.txt'  readme.md ") should be (Cat(StringsInput("буква а.txt" :: "readme.md" :: Nil)))
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
    parseCommand("Process wc") should be (ExternalCommand(StringsInput("wc" :: Nil)))

    parseCommand("Process echo 1 2 3") should be (ExternalCommand(StringsInput("echo" :: "1" :: "2" :: "3" :: Nil)))
  }

  test("pipelineParsing") {
    CommandParser.parse("cat build.sbt | wc | echo") should be (
      Cat(StringsInput("build.sbt" :: Nil)) :: Wc(StdIn) :: Echo(StringsInput(Nil)) :: Nil
    )
  }
}
