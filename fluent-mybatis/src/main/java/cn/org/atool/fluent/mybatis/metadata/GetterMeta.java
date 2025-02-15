package cn.org.atool.fluent.mybatis.metadata;

import cn.org.atool.fluent.mybatis.base.model.KeyMap;
import cn.org.atool.fluent.mybatis.utility.LockKit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static cn.org.atool.fluent.mybatis.mapper.StrConstant.PRE_GET;
import static cn.org.atool.fluent.mybatis.mapper.StrConstant.PRE_IS;

/**
 * getter方法元数据
 *
 * @author wudarui
 */
@SuppressWarnings("rawtypes")
public class GetterMeta {
    public final String fieldName;

    private final Method method;

    public final Type fType;

    private GetterMeta(Method method) {
        this.method = method;
        String name = method.getName();
        if (name.startsWith(PRE_IS)) {
            this.fieldName = name.substring(2, 3).toLowerCase() + name.substring(3);
        } else {
            this.fieldName = name.substring(3, 4).toLowerCase() + name.substring(4);
        }
        this.fType = method.getGenericReturnType();
    }

    public Object getValue(Object target) throws InvocationTargetException, IllegalAccessException {
        return target == null ? null : this.method.invoke(target);
    }

    private static final KeyMap<KeyMap<GetterMeta>> methodMetas = new KeyMap<>();

    /**
     * 返回类klass属性setter方法
     *
     * @param klass     指定类
     * @param fieldName 属性名称
     * @return SetterMethodMeta
     */
    public static GetterMeta get(Class klass, String fieldName) {
        if (!methodMetas.containsKey(klass)) {
            buildMetas(klass);
        }
        return methodMetas.get(klass).get(fieldName);
    }

    public static KeyMap<GetterMeta> get(Class klass) {
        if (!methodMetas.containsKey(klass)) {
            buildMetas(klass);
        }
        return methodMetas.get(klass);
    }

    /**
     * 按class类进行加锁
     */
    private final static LockKit<Class> ClassLock = new LockKit<>(16);

    private static void buildMetas(Class klass) {
        ClassLock.lockDoing(methodMetas::containsKey, klass, () -> {
            Method[] methods = klass.getDeclaredMethods();
            KeyMap<GetterMeta> classMethods = new KeyMap<>();
            for (Method m : methods) {
                String name = m.getName();
                if (!name.startsWith(PRE_GET) && !name.startsWith(PRE_IS) || m.getParameterCount() != 0) {
                    continue;
                }
                m.setAccessible(true);
                GetterMeta meta = new GetterMeta(m);
                classMethods.put(meta.fieldName, meta);
            }
            methodMetas.put(klass, classMethods);
        });
    }
}