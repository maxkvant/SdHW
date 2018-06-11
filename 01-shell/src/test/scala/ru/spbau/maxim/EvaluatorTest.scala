package ru.spbau.maxim

import org.scalatest.{FunSuite, Matchers}
import ru.spbau.maxim.parser.{CommandParser, PreprocessorImpl}
import ru.spbau.maxim.model.ModelImpl

import scala.reflect.io.Path

class EvaluatorTest extends FunSuite with Matchers {
  def genEvalutor: Evaluator = {
    val model = new ModelImpl()
    val preprocessor = new PreprocessorImpl(model)
    new Evaluator(preprocessor, CommandParser, model)
  }

  test("echoWcTest") {
    val evaluator = genEvalutor
    evaluator.evaluatePipeline("echo '1  23 45' | wc") should be ("1 3 8")
  }

  test("exitTest") {
    val evaluator = genEvalutor
    evaluator.evaluatePipeline("exit | echo 1 | wc") should be ("Exit")
    evaluator.evaluatePipeline("echo '1  23 45' | wc") should be ("")
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
    val evaluator = genEvalutor
    evaluator.evaluatePipeline(s"cat ${tmpFile.path}") should be (fileText)

    evaluator.evaluatePipeline(s"cat ${tmpFile.path} ${tmpFile.path}") should be (fileText + "\n" + fileText)

    evaluator.evaluatePipeline(s"wc ${tmpFile.path}") should be ("2 7 61")
  }

  test("assignTest") {
    val model = new ModelImpl()
    val preprocessor = new PreprocessorImpl(model)
    val evaluator = new Evaluator(preprocessor, CommandParser, model)

    evaluator.evaluatePipeline("a='1 '")
    model.getEnvValue("a") should be ("1 ")

    evaluator.evaluatePipeline("a = exit ")
    model.getEnvValue("a") should be ("exit")

    evaluator.evaluatePipeline("echo $a") should be ("exit")
  }
}
