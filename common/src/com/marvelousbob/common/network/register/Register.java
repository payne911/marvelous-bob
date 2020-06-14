package com.marvelousbob.common.network.register;

import com.esotericsoftware.kryonet.EndPoint;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.dto.Dto;
import com.marvelousbob.common.utils.MarvelousBobProperties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Using reflexion, we scan all classes that extends DTO in the package defined by the property
 * bob.network.dtopackage and register them to the EndPoint instance passed in.
 */
@Slf4j
public final class Register {

    public static final String KYRO_PACKAGE_KEY = "kryo.network.dtopackage";

    @Getter
    private Set<Class<? extends Dto>> registeredDtos;
    @Getter
    private Set<Class<? extends Object>> otherClassesToRegister;

    private EndPoint registrar;
    private String dtoPackage;


    /**
     * Will use a package declared in a property named {@link Register#KYRO_PACKAGE_KEY}
     *
     * @param registrar the {@link EndPoint} on which to register all classes
     */
    public Register(EndPoint registrar) {
        this.registrar = Objects.requireNonNull(registrar);
        this.dtoPackage = MarvelousBobProperties.getProps().getProperty(KYRO_PACKAGE_KEY);
    }


    /**
     * Will use the package passed as a parameter
     *
     * @param registrar  the {@link EndPoint} on which to register all classes
     * @param dtoPackage the package in which to scan classes
     */
    public Register(EndPoint registrar, String dtoPackage) {
        this.registrar = Objects.requireNonNull(registrar);
        this.dtoPackage = Objects.requireNonNull(dtoPackage);
    }

    public void registerClasses() {
        registeredDtos = new HashSet<>();
        otherClassesToRegister = new HashSet<>();
        if (dtoPackage == null) {
            throw new MarvelousBobException("Cannot find property %s".formatted(KYRO_PACKAGE_KEY));
        }
        var reflex = new Reflections(dtoPackage, new SubTypesScanner(), new MemberUsageScanner());
        try {
            registeredDtos = reflex.getSubTypesOf(Dto.class);
            registeredDtos.forEach(this::register);
            log.debug("REGISTERED {} DOTS", registeredDtos.size());

            for (Class<?> dto : registeredDtos) {
                addMissingFields(dto);
            }

            otherClassesToRegister.forEach(this::register);

        } catch (ReflectionsException re) {
            throw new MarvelousBobException("Could not load classes from package %s".formatted(dtoPackage), re);
        }
    }

    public Set<Class<?>> getAllRegisteredClasses() {
        return new HashSet<>() {{
            addAll(registeredDtos);
            addAll(otherClassesToRegister);
        }};
    }

    private void addMissingFields(Class<?> clz) {
        for (Field field : clz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (!fieldType.isPrimitive()
                    && !fieldType.isInterface()
                    && !Modifier.isStatic(field.getModifiers())
                    && !registeredDtos.contains(fieldType)
                    && !otherClassesToRegister.contains(fieldType)) {
                try {
                    registrar.getKryo().getRegistration(fieldType);
                } catch (IllegalArgumentException iae) {
                    log.debug("{} used in {} is not registered by Kyro", fieldType.getName(), clz.getName());
                    otherClassesToRegister.add(fieldType);
                    addMissingFields(fieldType);
                }
            }
        }
    }

    private void register(Class<?> clz) {
        log.debug("Registering class %s for Kryo network.".formatted(clz.getName()));
        registrar.getKryo().register(clz);
    }


}
