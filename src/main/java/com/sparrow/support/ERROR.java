package com.sparrow.support;

import com.sparrow.protocol.ErrorSupport;
import com.sparrow.protocol.ModuleSupport;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ERROR implements ErrorSupport {
    private static volatile Map<Integer, String> container = new ConcurrentHashMap<Integer, String>();

    private ModuleSupport module;
    private boolean system;
    private int code;
    private String message;


    @Override
    public boolean system() {
        return system;
    }

    @Override
    public ModuleSupport module() {
        return module;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ERROR business(ModuleSupport moduleSupport, String code, String message) {
        return new ERROR(false, moduleSupport, code, message);
    }

    public static ERROR system(ModuleSupport moduleSupport, String code, String message) {
        return new ERROR(true, moduleSupport, code, message);
    }

    private ERROR(boolean system, ModuleSupport moduleSupport, String code, String message) {
        this.system = system;
        this.message = message;
        this.module = moduleSupport;
        this.code = Integer.valueOf((system ? 1 : 2) + moduleSupport.code() + code);
    }

    @Override
    public String name() {
        if (container != null && container.size() > 0 && container.get(this.code) != null) {
            return container.get(this.code);
        }
        synchronized (ERROR.class) {
            if (container != null && container.size() > 0 && container.get(this.code) != null) {
                return container.get(this.code);
            }
            for (Field field : this.getClass().getDeclaredFields()) {
                try {
                    Object object = field.get(ErrorSupport.class);
                    if (object != null) {
                        ErrorSupport sparrowError = (ErrorSupport) object;
                        container.put(sparrowError.getCode(), field.getName().toLowerCase());
                    }
                } catch (Throwable ignore) {
                }
            }
            return container.get(this.code);
        }
    }
}
