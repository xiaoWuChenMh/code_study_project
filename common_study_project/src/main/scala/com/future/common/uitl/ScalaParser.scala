package com.future.common.uitl

import scala.meta._

/**
  * @Description
  * @Author hma
  * @Date 2023/8/24 20:41
  */
object ScalaParser {
  def main(args: Array[String]): Unit = {
    val code = scala.io.Source.fromFile("E:\\workspace\\IntelliJIDEA\\yuanma\\spark-source_code_read\\core\\src\\main\\scala\\org\\apache\\spark\\memory\\StaticMemoryManager.scala").mkString
    val parsedCode  = code.parse[Source]

    parsedCode match {
      case Parsed.Success(tree) =>
        tree.collect {
          case c: Defn.Class =>
            println(s"Class: ${c.name.value}")
            c.templ.stats.foreach {
              case d: Defn.Def => {
                val dstr = d.toString.replaceAll("\\s+", " ")
                println(s"  Method: ${dstr}")
              }
              case v: Defn.Val => {
                val dstr = v.toString.replaceAll("\\s+", " ")
                println(s"  Val: ${dstr}")
              }
              case v: Defn.Var => {
                val dstr = v.toString.replaceAll("\\s+", " ")
                println(s"  Var: ${dstr}")
              }
              case _ =>
            }
            c.mods.foreach {
              case mod"@${annot}" => println(s"  Annotation: $annot")
              case _ =>
            }

          case o: Defn.Object =>
            println(s"Object: ${o.name.value}")
            o.templ.stats.foreach {
              case d: Defn.Def => {
               val dstr = d.toString.replaceAll("\\s+", " ")
                println(s"  Method: ${dstr}")
              }
              case v: Defn.Val => {
                val dstr = v.toString.replaceAll("\\s+", " ")
                println(s"  Val: ${dstr}")
              }
              case v: Defn.Var => {
                val dstr = v.toString.replaceAll("\\s+", " ")
                println(s"  Var: ${dstr}")
              }
              case _ =>
            }
            o.mods.foreach {
              case mod"@${annot}" => println(s"  Annotation: $annot")
              case _ =>
            }
        }

      case Parsed.Error(pos, msg, _) =>
        println(s"Failed to parse code at ${pos.startLine}:${pos.startColumn}, error: $msg")
    }
  }
}
