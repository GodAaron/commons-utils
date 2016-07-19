package com.wf2311.commons.reflect;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;

/**
 * @author wf2311
 * @date 2016/01/08 16:02
 */
public class ReflectUtils {
    private static ReflectUtils instance = null;

    public static ReflectUtils getInstance() {
        if (instance == null)
            instance = new ReflectUtils();
        return instance;
    }

    /**
     * 将java.lang.Class数组转为javassist.CtClass数组
     *
     * @param pool ClassPool.getDefault()
     * @param cls  java.lang.Class数组
     * @return javassist.CtClass数组
     */
    private CtClass[] getCtClasses(ClassPool pool, Class[] cls) {
        if (cls.length > 0) {

            CtClass[] ctClasses = new CtClass[cls.length];
            try {
                for (int i = 0; i < cls.length; i++) {

                    ctClasses[i] = pool.getCtClass(cls[i].getName());
                }
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            return ctClasses;
        }
        return null;
    }


    /**
     * 使用javassist获取指定类的指定方法的参数列表
     *
     * @param methodName 方法名
     * @param clazz      类
     * @param cls        该方法的参数(按顺序)类型数组
     * @return 参数列表数组
     * @throws NotFoundException 未找到该方法
     */
    public String[] getMethodParameterNames(String methodName, Class clazz, Class[] cls) throws NotFoundException {
        String[] paramNames;
        CtMethod ctMethod;
        ClassPool pool = ClassPool.getDefault();
        //将本类所在的目录名作为类搜索路径，解决在tomcat等web容器中找不到class文件
        pool.insertClassPath(new ClassClassPath(getClass()));
        CtClass ctClass = pool.getCtClass(clazz.getName());
        if (cls == null) {
            ctMethod = ctClass.getDeclaredMethod(methodName);
        } else {
            ctMethod = ctClass.getDeclaredMethod(methodName, getCtClasses(pool, cls));
        }
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

        paramNames = new String[ctMethod.getParameterTypes().length];
        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        for (int i = 0; i < attr.tableLength(); i++) {
            if (attr.index(i) >= pos && attr.index(i) < paramNames.length + pos) {
                paramNames[attr.index(i) - pos] = attr.variableName(i);
            }
        }
        return paramNames;
    }

    /**
     * 获取指定类的指定方法的参数列表
     *
     * @param method 方法(必须明确指明)
     * @param clazz  类
     * @return 参数列表数组
     * @throws NotFoundException 未找到该方法
     */
    public String[] getMethodParameterNames(Method method, Class clazz) throws NotFoundException {
        Class[] cls = method.getParameterTypes();
        return getMethodParameterNames(method.getName(), clazz, cls);
    }

    /**
     * 获取指定类的指定方法的参数列表(若该方法存在重写,请调用{@link ReflectUtils#getMethodParameterNames(String, Class, Class[])}方法)
     *
     * @param methodName 方法名
     * @param clazz      类
     * @return 参数列表数组
     * @throws NotFoundException 未找到该方法
     */
    public String[] getMethodParameterNames(String methodName, Class clazz) throws NotFoundException {
        return getMethodParameterNames(methodName, clazz, null);
    }
}