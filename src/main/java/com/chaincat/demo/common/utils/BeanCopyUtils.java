package com.chaincat.demo.common.utils;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对象复制工具类
 *
 * @author Chain
 */
public class BeanCopyUtils {

    /**
     * 默认字段工厂
     */
    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * 默认字段实例
     */
    private static final MapperFacade MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();

    /**
     * 默认字段实例集合
     */
    private static final Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap<>();

    /**
     * 复制对象
     *
     * @param source      原对象
     * @param targetClass 目标对象类型
     * @return 目标对象
     */
    public static  <T, E> E covert(T source, Class<E> targetClass) {
        return MAPPER_FACADE.map(source, targetClass);
    }

    /**
     * 复制对象（自定义配置）
     *
     * @param source      原对象
     * @param targetClass 目标对象类型
     * @param fieldMap    原对象与目标对象字段映射关系
     * @return 目标对象
     */
    public static <T, E> E covert(T source, Class<E> targetClass, Map<String, String> fieldMap) {
        MapperFacade mapperFacade = getMapperFacade(source.getClass(), targetClass, fieldMap);
        return mapperFacade.map(source, targetClass);
    }

    /**
     * 复制集合（默认字段）
     *
     * @param source      原对象
     * @param targetClass 目标对象类型
     * @return 目标集合
     */
    public static <T, E> List<E> covertList(Collection<T> source, Class<E> targetClass) {
        return MAPPER_FACADE.mapAsList(source, targetClass);
    }

    /**
     * 复制集合（自定义配置）
     *
     * @param source      原对象
     * @param targetClass 目标对象类型
     * @param fieldMap    原对象与目标对象字段映射关系
     * @return 目标集合
     */
    public static <E, T> List<E> covertList(Collection<T> source, Class<E> targetClass, Map<String, String> fieldMap) {
        Optional<T> first = source.stream().findFirst();
        if (first.isEmpty()) {
            return new ArrayList<>();
        }
        MapperFacade mapperFacade = getMapperFacade(first.get().getClass(), targetClass, fieldMap);
        return mapperFacade.mapAsList(source, targetClass);
    }

    /**
     * 获取自定义映射
     *
     * @param sourceClass 原对象类型
     * @param targetClass 目标对象类型
     * @param fieldMap    原对象与目标对象字段映射关系
     * @return 字段实例
     */
    private static <T, E> MapperFacade getMapperFacade(Class<T> sourceClass, Class<E> targetClass, Map<String, String> fieldMap) {
        String mapKey = sourceClass.getCanonicalName() + "_" + targetClass.getCanonicalName();
        MapperFacade mapperFacade = CACHE_MAPPER_FACADE_MAP.get(mapKey);
        if (Objects.isNull(mapperFacade)) {
            MapperFactory factory = new DefaultMapperFactory.Builder().build();
            ClassMapBuilder<T, E> classMapBuilder = factory.classMap(sourceClass, targetClass);
            fieldMap.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
            mapperFacade = factory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
        }
        return mapperFacade;
    }
}
