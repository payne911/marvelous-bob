package com.marvelousbob.common.network.register;

import com.esotericsoftware.kryonet.EndPoint;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.utils.MarvelousBobProperties;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.scanners.MemberUsageScanner;
import org.reflections.scanners.SubTypesScanner;


/**
 * Using reflexion, we scan all classes that extends a desired class (or interface)
 * in the package defined by the property {@code kryo.network.dtopackage} (in the
 * {@code config.properties} file) and register them to the {@link EndPoint} instance passed in.
 * <p>
 * Fields from registered class will be recursively registered, unless the field is declared as a
 * Parameterized type (Generic) or is an Interface. This will have to be manually registered.
 */
@Slf4j
public final class Register {

    public static final String KRYO_PACKAGE_KEY = "kryo.network.dtopackage";

    @Getter
    private SortedSet<Class<?>> registeredDtos;
    @Getter
    private SortedSet<Class<? extends Object>> otherClassesToRegister;

    private EndPoint registrar;
    private String dtoPackage;


    /**
     * Will use a package declared in a property named {@value Register#KRYO_PACKAGE_KEY}
     *
     * @param registrar the {@link EndPoint} on which to register all classes
     */
    public Register(EndPoint registrar) {
        this.registrar = Objects.requireNonNull(registrar);
        this.dtoPackage = MarvelousBobProperties.getProps().getProperty(KRYO_PACKAGE_KEY);
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


    private void addClassToregister(Class<?> clz) {
        otherClassesToRegister.add(clz);
    }


    public void registerClasses() {
        registerClasses(Object.class);
    }

    @SuppressWarnings("unchecked")
    public void registerClasses(Class<?> filter) {
        registeredDtos = new TreeSet<>(Comparator.comparing(Class::getName, String.CASE_INSENSITIVE_ORDER));
        otherClassesToRegister = new TreeSet<>(Comparator.comparing(Class::getName, String.CASE_INSENSITIVE_ORDER));
        if (dtoPackage == null) {
            throw new MarvelousBobException("Cannot find property %s".formatted(KRYO_PACKAGE_KEY));
        }
        var reflex = new Reflections(dtoPackage, new SubTypesScanner(false),
                new MemberUsageScanner());
        try {
            registeredDtos.addAll(reflex.getSubTypesOf(filter == null ? Object.class : (Class<Object>) filter));
            registeredDtos.forEach(this::register);
            log.debug("REGISTERED {} DTOs", registeredDtos.size());

            for (Class<?> dto : registeredDtos) {
                addMissingFields(dto);
            }

            otherClassesToRegister.forEach(this::register);

        } catch (ReflectionsException re) {
            throw new MarvelousBobException("Could not load classes from package %s".formatted(dtoPackage), re);
        }
    }


    private void addMissingFields(Class<?> clz) {
        for (Field field : clz.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            if (!fieldType.isPrimitive()
                    && !fieldType.isInterface() // see issue https://github.com/payne911/marvelous-bob/issues/1
                    && !Modifier.isStatic(field.getModifiers())
                    && !registeredDtos.contains(fieldType)
                    && !otherClassesToRegister.contains(fieldType)) {
                try {
                    registrar.getKryo().getRegistration(fieldType);
                } catch (IllegalArgumentException iae) {
                    log.debug("{} used in {} is not registered by Kryo", fieldType.getName(), clz.getName());
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
