package com.future.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import scala.Option;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.JavaConverters;
import scala.reflect.internal.util.BatchSourceFile;
import scala.reflect.internal.util.Position;
import scala.reflect.internal.util.SourceFile;
import scala.tools.nsc.Global;
import scala.tools.nsc.Settings;
import scala.tools.nsc.ast.parser.SyntaxAnalyzer;
import scala.tools.nsc.reporters.ConsoleReporter;

/**
 * @Description 使用Scala的scala-parser-combinators库。
 * @Author 
 * @Date 2023/7/28 20:10
 */
public class ScalaFileReader {

    private Global global;
    private List<String> lines;

    public ScalaFileReader(String filePath) throws IOException {
        lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        Settings settings = new Settings();
        settings.usejavacp().value_$eq(true);
        ConsoleReporter reporter = new ConsoleReporter(settings);
        global = new Global(settings, reporter);
    }

    public String getClassNameAndPackageName() {
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(global);
        SourceFile sourceFile = new BatchSourceFile("scalaFile", String.join("\n", lines));
        Global.Run run = new Global.Run(global);
        Global.CompilationUnit unit = global.newCompilationUnit(sourceFile, null);
        run.parser(unit);

        Option<String> packageName = unit.packge().name().toStringOpt();
        Option<String> className = unit.body().collectFirst({
        case global.ClassDef(_, name, _, _, _) => name.toString()
        }, global);

        return "Package: " + packageName.getOrElse("") + ", Class: " + className.getOrElse("");
    }

    public List<String> getClassAttributesAndComments() {
        List<String> attributes = new ArrayList<>();
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(global);
        SourceFile sourceFile = new BatchSourceFile("scalaFile", String.join("\n", lines));
        Global.Run run = new Global.Run(global);
        Global.CompilationUnit unit = global.newCompilationUnit(sourceFile, null);
        run.parser(unit);

        unit.body().foreach({
        case global.ValDef(_, name, tpt, _) =>
        val comment = global.comment(name.pos).map(_.raw).getOrElse("")
        attributes.add(comment + "val " + name + ": " + tpt)
        case _ =>
        }, global);

        return attributes;
    }

    public List<String> getClassMethodsAndComments() {
        List<String> methods = new ArrayList<>();
        SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(global);
        SourceFile sourceFile = new BatchSourceFile("scalaFile", String.join("\n", lines));
        Global.Run run = new Global.Run(global);
        Global.CompilationUnit unit = global.newCompilationUnit(sourceFile, null);
        run.parser(unit);

        unit.body().foreach({
        case global.DefDef(_, name, _, _, tpt, _) =>
        val comment = global.comment(name.pos).map(_.raw).getOrElse("")
        methods.add(comment + "def " + name + ": " + tpt)
        case _ =>
        }, global);

        return methods;
    }

    public static void main(String[] args) {
        try {
            ScalaFileReader reader = new ScalaFileReader("path/to/your/scala/file.scala");

            System.out.println("Class Name and Package Name:");
            System.out.println(reader.getClassNameAndPackageName());

            System.out.println("\nClass Attributes and Comments:");
            for (String attribute : reader.getClassAttributesAndComments()) {
                System.out.println(attribute);
            }

            System.out.println("\nClass Methods and Comments:");
            for (String method : reader.getClassMethodsAndComments()) {
                System.out.println(method);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
