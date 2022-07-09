package irach.demo.profiler;

import java.lang.instrument.Instrumentation;

public class Profiler
{
  // todo - to allow logging. maybe use here the java.util.logger and in the app a logs... or log4j?
  private static final String CLASS_TO_INSTRUMENT = "java.util.logging.Logger";

  public static void premain(String agentArgs, Instrumentation inst)
  {
    transformClass(CLASS_TO_INSTRUMENT, inst);
  }

  public static void agentmain(String agentArgs, Instrumentation inst)
  {
    transformClass(CLASS_TO_INSTRUMENT, inst);
  }

  private static void transformClass(String className, Instrumentation instrumentation)
  {
    LogTransformer2 logTransformer = new LogTransformer2(className);
    instrumentation.addTransformer(logTransformer, true);

//    try
//    {
//      instrumentation.retransformClasses(clazz);
//    }
//    catch (Exception ex)
//    {
////      logger.log(Level.SEVERE, "Transform failed for: [" + clazz.getName() + "]", ex);
//    }

//    todo - the following was a working part uncomment if the new try fails
//    Class<?> targetCls = null;
//    ClassLoader targetClassLoader = null;
//
//    // see if we can get the class using forName
//    // todo - in a way this is less good, it's defenetly better to wait until the class is loaded.
//    // todo - how this can be implemented here? seems to me that it only needs to be added to the instrumentation
//    //  (but before the class we wish to transform is loaded)
//
//    try
//    {
//      targetCls = Class.forName(className);
//      targetClassLoader = targetCls.getClassLoader();
//      transform(targetCls, targetClassLoader, instrumentation);
//      return;
//    }
//    catch (Exception ex)
//    {
////            logger.warning("Class [{}] not found with Class.forName");
//    }
//    // otherwise iterate all loaded classes and find what we want
//
//    for (Class<?> clazz : instrumentation.getAllLoadedClasses())
//    {
//      if (clazz.getName().equals(className))
//      {
//        targetCls = clazz;
//        targetClassLoader = targetCls.getClassLoader();
//        transform(targetCls, targetClassLoader, instrumentation);
//        return;
//      }
//    }
////        logger.log(Level.SEVERE, "Failed to find class [" + className + "]");
  }

//  private static void transform(Class<?> clazz, ClassLoader classLoader, Instrumentation instrumentation)
//  {
//    /* learning note:
//    We now add a transformer (implements java.lang.instrument.ClassFileTransformer) to the instrumentation
//    Once a transformer has been registered with addTransformer, the transformer will be called for every
//    new class definition and every class redefinition.
//    */
//
//    // todo - why do i check the class loader i what the class to be transformed for all classloaders?
//    //  potentially I can remove the class loader check
//    //  I indeed can - but now the problem remains what if the class i want to instrument is called before i have added my transformer.
//    //  Not a problem - I can descide not to dicusss this right now. when this wiil be a problem for whick classess? this is an important question to know the answear to but no need for the retransform which in my opinion is confusing
//
//    LogTransformer logTransformer = new LogTransformer(clazz.getName(), classLoader);
//    instrumentation.addTransformer(logTransformer, true);
//
//    try
//    {
//      instrumentation.retransformClasses(clazz);
//    }
//    catch (Exception ex)
//    {
////      logger.log(Level.SEVERE, "Transform failed for: [" + clazz.getName() + "]", ex);
//    }
//  }
}
