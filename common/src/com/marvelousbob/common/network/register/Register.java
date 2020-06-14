package com.marvelousbob.common.network.register;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.EndPoint;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.dto.Dto;
import com.marvelousbob.common.utils.MarvelousBobProperties;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;

import java.util.ArrayList;
import java.util.List;


/**
 * Using reflexion, we scan all classes that extends DTO in the package defined by the property
 * bob.network.dtopackage and register them to the EndPoint instance passed in.
 */
@Slf4j
public final class Register {

    // Add custom classes that are not part of BASE_DTO_PACKAGE
    private static final List<Class<?>> EXTRA_CLASSES_TO_REGISTER = List.of(
            Array.class,
            ArrayList.class
    );

    private EndPoint registrar;

    public Register(EndPoint registrar) {
        this.registrar = registrar;
    }

    public void registerClasses() {
        String key = "kryo.network.dtopackage";
        String dtoPackage = MarvelousBobProperties.getProps().getProperty(key);
        if (dtoPackage == null) {
            throw new MarvelousBobException("Cannot find property %s".formatted(key));
        }
        var reflex = new Reflections(dtoPackage);
        try {
            reflex.getSubTypesOf(Dto.class).forEach(this::register);
        } catch (ReflectionsException re) {
            throw new MarvelousBobException("Could not load classes from package %s".formatted(dtoPackage), re);
        }
        EXTRA_CLASSES_TO_REGISTER.forEach(this::register);
    }

    private void register(Class<?> clz) {
        log.debug("Registering class %s for Kryo network.".formatted(clz.getName()));
        registrar.getKryo().register(clz);
    }
}
