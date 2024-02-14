package com.ak.rnd.excel.excelcsv.utils;

import com.ak.rnd.excel.excelcsv.dao.DataProvider;
import com.ak.rnd.excel.excelcsv.model.Employee;
import reactor.core.publisher.Flux;

import java.lang.reflect.Field;

public class ReflectionTest {

    public static void main(String[] args) {
        Flux<Employee> employees = new DataProvider().getEmployees(5);

        invokeReflection(employees);
    }

    private static void invokeReflection(Flux<Employee> items) {

//        items.subscribe(item -> {
//            Method[] methods = item.getClass().getDeclaredMethods();
//            for(Method m : methods) {
//                try {
//                    System.out.println(m.getName() + " = " + m.invoke(item));
//                } catch (IllegalAccessException | InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

        items.subscribe(item -> {
            Field[] fields = item.getClass().getDeclaredFields();
            for (Field field: fields) {
                field.setAccessible(true);
                try {
                    System.out.println(field.getName() + " = " + field.get(item));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
