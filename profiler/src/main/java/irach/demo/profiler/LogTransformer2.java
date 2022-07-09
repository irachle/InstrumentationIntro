package irach.demo.profiler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * Learning Note:
 * To transform a class files an agent should provide an implementation of the ClassFileTransformer interface.
 * This is where you implement addition of new code.
 */

public class LogTransformer2 implements ClassFileTransformer
{
    private static final String TRANSFORM_METHOD = "log";

    private String targetClassName;

    public LogTransformer2(String className)
    {
        targetClassName = className;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer)
    {
        byte[] byteCode = classfileBuffer;
        String finalTargetClassName = this.targetClassName.replaceAll("\\.", "/");

        if (!className.equals(finalTargetClassName))
        {
            return byteCode;
        }

        try
        {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(targetClassName);
            CtMethod method = cc.getDeclaredMethod(TRANSFORM_METHOD);

            if (method == null)
            {
                // todo - this should be checked and logged properly
                System.out.println("Failed to instrumnet. method is null");
            }

            // Add code to the beginning of a method
//            method.addLocalVariable("startTime", CtClass.longType);
//            method.insertBefore( "startTime = System.currentTimeMillis();");
            method.insertAfter("irach.demo.profiler.LogProfiler.$_INSTANCE.logMethodCalled();");

            // Add code to the end of a method
//            StringBuilder endBlock = new StringBuilder();
//            method.addLocalVariable("endTime", CtClass.longType);
//            method.addLocalVariable("opTime", CtClass.longType);
//            endBlock.append("endTime = System.currentTimeMillis();");
//            endBlock.append("opTime = endTime - startTime;");
//            endBlock.append("System.out.println(\"[Added by Agent] operation time: \" + opTime + \" miliseconds!\");");
//            method.insertAfter(endBlock.toString());

            byteCode = cc.toBytecode();
            cc.detach();
        }
        catch (Exception e)
        {
            System.out.println("Exception during retransform: " + e.getMessage());
//            logger.log(Level.SEVERE, "Exception during retransform:", e);
        }

//        logger.info("[Agent] Finished transforming class " + className);
        return byteCode;
    }
}