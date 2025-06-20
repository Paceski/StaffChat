package org.insurgencedev.staffchat.utils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static final String CRAFTBUKKIT = "org.bukkit.craftbukkit";


    /**
     * Find a class in org.bukkit.craftbukkit package, adding the version
     * automatically
     *
     * @param name
     * @return
     */
    public static Class<?> getOBCClass(final String name) {
        String version = MinecraftVersion.getServerVersion();

        if (!version.isEmpty())
            version += ".";

        return ReflectionUtil.lookupClass(CRAFTBUKKIT + "." + version + name);
    }


    /**
     * Lookup a class by its name
     *
     * @param name
     * @return
     */
    public static Class<?> lookupClass(final String name) {
        try {
            return Class.forName(name);

        } catch (final ClassNotFoundException ex) {
            throw new RuntimeException("Cannot find class " + name, ex);
        }

    }

    public static <T> T getFieldContent(final Object instance, final String field) {
        return getFieldContent(instance.getClass(), field, instance);
    }

    public static <T> T getFieldContent(Class<?> clazz, final String field, final Object instance) {
        final String originalClassName = new String(clazz.getName());

        do
            // note: getDeclaredFields() fails if any of the fields are classes that cannot be loaded
            for (final Field f : clazz.getDeclaredFields())
                if (f.getName().equals(field))
                    return (T) getFieldContent(f, instance);
        while (!(clazz = clazz.getSuperclass()).isAssignableFrom(Object.class));

        throw new RuntimeException("Cannot find field " + field + " in class " + originalClassName);
    }

    public static Object getFieldContent(final Field field, final Object instance) {
        try {
            field.setAccessible(true);

            return field.get(instance);

        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException("Cannot get field content", e);
        }
    }

}
