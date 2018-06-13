package ru.spbau.maxim

import org.scalatest.{FunSuite, Matchers}
import ru.spbau.maxim.model.{Model, ModelImpl}
import commands.Grep

import scala.reflect.io.Path

class EvaluatorTest extends FunSuite with Matchers {
  def genEvaluator(): Evaluator = new EvaluatorImpl(new ModelImpl)

  test("echoWcTest") {
    val evaluator = genEvaluator()
    evaluator.evaluatePipeline("echo '1  23 45' | wc") should be ("1 3 8")
  }

  test("exitTest") {
    val evaluator = genEvaluator()

    evaluator.finished should be (false)
    evaluator.evaluatePipeline("exit | echo 1 | wc") should be ("Exit")
    evaluator.finished should be (true)
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
    val evaluator = genEvaluator()
    evaluator.evaluatePipeline(s"cat ${tmpFile.path}") should be (fileText)

    evaluator.evaluatePipeline(s"cat ${tmpFile.path} ${tmpFile.path}") should be (fileText + "\n" + fileText)

    evaluator.evaluatePipeline(s"wc ${tmpFile.path}") should be ("2 7 61")
  }

  test("assignTest") {
    val model = new ModelImpl()
    val evaluator = new EvaluatorImpl(model)

    evaluator.evaluatePipeline("a='1 '")
    model.getEnvValue("a") should be ("1 ")

    evaluator.evaluatePipeline("a = exit ")
    model.getEnvValue("a") should be ("exit")

    evaluator.evaluatePipeline("echo $a") should be ("exit")
  }

  test("grepTest") {
    implicit val model: Model = new ModelImpl()

    Grep("1|2").execute(
      """
        |11
        |2
        |33
        |313
        |4
      """.stripMargin
    ).split("\n") should be (List("11", "2", "313"))

    Grep("1", linesAfterMatch = 2).execute(
      """|11
         |1
         |2
         |5
         |33
         |313
         |4""".stripMargin
    ).split("\n") should be (List("11", "1",  "2", "5", "313", "4"))

    Grep("саша", caseInsensitive = true).execute(
      "шла Саша по шоссе"
    ).split("\n") should be (List("шла Саша по шоссе"))

    Grep("саша").execute(
      "шла Саша по шоссе"
    ).length should be (0)

    Grep("шав", matchOnlyWords = true).execute(
      "вкусная шаверма"
    ).length should be (0)
    
    val evaluator = genEvaluator()

    evaluator.evaluatePipeline(
      "echo вкусная шаверма | grep -w шав"
    ).length should be (0)

    evaluator.evaluatePipeline(
      "echo \"a\n2\n3\na\" | grep -A 1 a"
    ).split("\n") should be (List("a", "2", "a"))

    evaluator.evaluatePipeline(
      "echo шла Саша по шоссе | grep -i саша"
    ).split("\n") should be (List("шла Саша по шоссе"))
  }
}
