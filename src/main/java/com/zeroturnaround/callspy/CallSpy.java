package com.zeroturnaround.callspy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class CallSpy implements ClassFileTransformer {
    @Override
    public byte[] transform(//region other parameters
                            ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            //endregion
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        ClassPool cp = ClassPool.getDefault();
        cp.importPackage("com.zeroturnaround.callspy");

        //region filter agent classes
        if (!className.startsWith("com/zeroturnaround/callspy/Main")) {
            return null;
        }
        CtClass ct = null;
        try {
            ct = cp.makeClass(new ByteArrayInputStream(classfileBuffer));

            CtMethod[] declaredMethods = ct.getDeclaredMethods();
            for (CtMethod method : declaredMethods) {
                //region instrument method
                method.insertBefore(
                        " { " + "Stack.push();" + "Stack.log(\"" + className + "." + method.getName() + "\"); " + "}");
                method.insertAfter("{ Stack.pop(); }", true);
                //endregion
            }
            ct.writeFile();
            return ct.toBytecode();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (ct != null) {
                ct.detach();
            }
        }

        return classfileBuffer;
    }
}
