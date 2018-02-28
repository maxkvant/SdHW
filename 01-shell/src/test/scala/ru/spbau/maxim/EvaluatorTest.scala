package ru.spbau.maxim

import org.scalatest.{FunSuite, Matchers}

import scala.reflect.io.Path

class EvaluatorTest extends FunSuite with Matchers {
  test("echoWcTest") {
    val evaluator = new Evaluator
    evaluator.evaluate("echo '1  23 45' | wc") should be ("1 3 8")
  }

  test("exitTest") {
    val evaluator = new Evaluator
    evaluator.evaluate("exit | echo 1 | wc") should be ("Exit")
    evaluator.evaluate("echo '1  23 45' | wc") should be ("")
  }

  test("catWcTest") {
    val fileText =
      """|Корабли лавировали, лавировали, лавировали
         |да не вылавировали""".stripMargin

    val tmpDir = Path(System.getProperty("java.io.tmpdir"))
    val fileName = "cat_tmp.txt"
    val tmpFile = tmpDir.resolve(fileName)
    println(tmpFile.toAbsolute)
    if (!tmpFile.exists) {
      tmpFile.createFile(failIfExists = true)
      tmpFile.toFile.printlnAll(fileText)
    }
    val evaluator = new Evaluator
    evaluator.evaluate(s"cat ${tmpFile.path}") should be (fileText)

    evaluator.evaluate(s"cat ${tmpFile.path} ${tmpFile.path}") should be (fileText + "\n" + fileText)

    evaluator.evaluate(s"wc ${tmpFile.path}") should be ("2 7 61")
  }

  test("assignTest") {
    val evaluator = new Evaluator

    evaluator.evaluate("a='1 '")
    Model.getEnvValue("a") should be ("1 ")

    evaluator.evaluate("a = exit ")
    Model.getEnvValue("a") should be ("exit")

    evaluator.evaluate("echo $a") should be ("exit")
  }
}
