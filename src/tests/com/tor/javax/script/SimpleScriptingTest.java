package com.tor.javax.script;

import junit.framework.TestCase;
import obuchenie.data.FIO;
import obuchenie.data.Person;
import org.intellij.lang.annotations.Language;

import javax.script.*;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 05.11.12
 * Time: 13:23
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 * http://www.ibm.com/developerworks/ru/library/j-5things9/index.html?ca=drs-
 * http://docs.oracle.com/javase/6/docs/technotes/guides/scripting/programmer_guide/index.html
 */
public class SimpleScriptingTest extends TestCase {
    @Language("JavaScript")
    String demoScript = "println(\"Start script \\r\\n\");\n" +
            "\n" +
            "// Output the type of an object\n" +
            "function printType(obj) {\n" +
            "    if (obj.getClass)\n" +
            "        println(\"    Java object: \" + obj.getClass().name);\n" +
            "    else\n" +
            "        println(\"    JS object: \" + obj.toSource());\n" +
            "    println(\"\");\n" +
            "}\n" +
            "\n" +
            "// Print variable\n" +
            "println(\"[JS] demoVar: \" + demoVar);\n" +
            "printType(demoVar);\n" +
            "\n" +
            "// Call method of Java object\n" +
            "strBuf.append(\" used in DemoScript.js\");\n" +
            "println(\"[JS] strBuf: \" + strBuf);\n" +
            "printType(strBuf);\n" +
            "\n" +
            "// Modify variable\n" +
            "demoVar = \"value set in DemoScript.js\";\n" +
            "println(\"[JS] demoVar: \" + demoVar);\n" +
            "printType(demoVar);\n" +
            "\n" +
            "// Set a new variable\n" +
            "var newVar = { x: 1, y: { u: 2, v: 3 } }\n" +
            "println(\"[JS] newVar: \" + newVar);\n" +
            "printType(newVar);\n" +
            "\n" +
            "println(\"End script \\r\\n\");";

    @Language("JavaScript")
    String invokeFunction = "println(\"Start script \\r\\n\");\n" +
            "\n" +
            "function printType(obj) {\n" +
            "    if (obj.getClass)\n" +
            "        println(\"    Java object: \" + obj.getClass().name);\n" +
            "    else\n" +
            "        println(\"    JS object: \" + obj.toSource());\n" +
            "    println(\"\");\n" +
            "}\n" +
            "\n" +
            "function demoFunction(a, b) {\n" +
            "    println(\"[JS] a: \" + a);\n" +
            "    printType(a);\n" +
            "    println(\"[JS] b: \" + b);\n" +
            "    printType(b);\n" +
            "    var c = a + b;\n" +
            "    println(\"[JS] c: \" + c);\n" +
            "    printType(c);\n" +
            "    return c;\n" +
            "}\n" +
            "\n" +
            "println(\"End script \\r\\n\");";

    /**
     * Вызов сценариев из кода Java
     */
    public void testJavaScript() {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            engine.eval("\"println(\"Hello from inside scripting!\")");
        } catch (ScriptException scrEx) {
            scrEx.printStackTrace();
            fail();
        }
    }

    /**
     * Связывание объектов для сценариев
     */
    public void testJavaScript_binding() {
        try {
            ScriptEngine engine =
                    new ScriptEngineManager().getEngineByName("javascript");
            Bindings bindings = new SimpleBindings();
            bindings.put("author", new Person(new FIO("Ted", "Neward", ""), 39, false));
            bindings.put("title", "5 Things You Didn't Know");
            //свойства в стиле JavaBeans сводятся к прямому доступу к имени, как если бы это были поля.
            engine.eval("println(\"Hello from inside scripting!\")\n" +
                    "println(\"author.firstName = \" + author.fio.lastName)", bindings);

        } catch (ScriptException scrEx) {
            scrEx.printStackTrace();
            fail();
        }
    }

    /**
     * Компиляция часто используемых сценариев
     */

    public void testJavaScript_compil() {
        String s = "println(\"Hello from inside scripting!\")\n" +
                "println(\"author.firstName = \" + author.fio.lastName)";
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
            Bindings bindings = new SimpleBindings();
            bindings.put("author", new Person(new FIO("Ted", "Neward", ""), 39, false));
            bindings.put("title", "5 Things You Didn't Know");
            //
            if (engine instanceof Compilable) {
                System.out.println("Compiling....");
                Compilable compEngine = (Compilable) engine;
                CompiledScript cs = compEngine.compile(s);
                cs.eval(bindings);
            } else {
                engine.eval(s, bindings);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testDemoScript_variable() {
// Get the JavaScript engine
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Set JavaScript variables
        Bindings vars = new SimpleBindings();
        vars.put("demoVar", "value set in ScriptDemo.java");
        vars.put("strBuf", new StringBuffer("string buffer"));


        try {
            engine.eval(demoScript, vars);
        } catch (ScriptException e) {
            fail(e.toString());
        }
        // Get JavaScript variables
        Object demoVar = vars.get("demoVar");
        System.out.println("[Java] demoVar: " + demoVar);
        System.out.println("    Java object: " + demoVar.getClass().getName());
        System.out.println();
        Object strBuf = vars.get("strBuf");
        System.out.println("[Java] strBuf: " + strBuf);
        System.out.println("    Java object: " + strBuf.getClass().getName());
        System.out.println();
        Object newVar = vars.get("newVar");
        System.out.println("[Java] newVar: " + newVar);
        System.out.println("    Java object: " + newVar.getClass().getName());
        System.out.println();
    }

    public void testInvokeFunction() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        try {
            engine.eval(invokeFunction);
            if (engine instanceof Invocable) {
                Invocable invEngine = (Invocable) engine;
                Object result = invEngine.invokeFunction("demoFunction", 1, 2.3);
                System.out.println("[Java] result: " + result);
                System.out.println("    Java object: " + result.getClass().getName());
                System.out.println();
            } else
                System.out.println("NOT Invocable");
        } catch (ScriptException e) {
            e.printStackTrace();
            fail();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            fail();
        }
    }

}
