package com.bladeDemo;


import lombok.Builder;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Testing {

    public static void main(String[] args) {
//        tryThis(session -> method(session,6,'F'));

        try {
            TestTry();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TestTry() throws Exception {

        try (NewType myType = new NewType()) {
            myType.setName("name");
            System.err.println(myType.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void method(NewType name, int Age, char gender){
        name.setName("Nema");

        System.out.println("In method this is a name "+name.getName());
        System.out.println(Age);
        System.out.println(gender);
    }


//
    @FunctionalInterface
        interface test{
            void run(NewType name);
    }

    public static void tryThis(test test){
        System.out.println("this is a session hook");
        NewType name = new NewType();
            try{
                test.run(name);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("Test complete");
            }
    }

    static class NewType implements AutoCloseable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if(name.contains("ex")){

                throw new RuntimeException("ex is not allowed");
            }
            this.name = name;
        }


        @Override
        public void close() throws Exception {
            System.err.println("Removed everything");
        }
    }

}
