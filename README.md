callspy
=======

A simple tracing agent

Build:
gradle jar

Run:
java  -javaagent:build/libs/callspy-0.1.jar com.zeroturnaround.callspy.Main

Find the new bytecode in com/zeroturnaround/callspy/Main.class

Decompiled classfile after Instumentating Main.java
```java
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zeroturnaround.callspy;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        try {
            Stack.push(); // added after instrumentation
            Stack.log("com/zeroturnaround/callspy/Main.main");  // added after instrumentation
            System.out.println("Hello from CallSpy!");
            System.out.println("Usage: ");
            System.out.println("   java -javaagent:/path/to/callspy.jar your.main.Clazz");
            System.out.println("\nEnjoy! :-)");
        } finally {  // added after instrumentation
            Object var2 = null;
            Stack.pop();
        }

    }
}


```
