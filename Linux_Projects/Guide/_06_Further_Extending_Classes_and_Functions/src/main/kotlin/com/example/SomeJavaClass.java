package com.example;

import org.jetbrains.annotations.NotNull;

public class SomeJavaClass {

    public static void addJavaCustomerEventListenerViaCompanionObject() {

        // Without adding the `@JvmStatic` notifier to our Kotlin-side `CustomerService.addListener` function then
        // inside Java we cannot access our Kotlin `CustomerService.addListener` method..
        //CustomerService.addListener

        // ..but we CAN access the CustomerService's companion object - and then access the
        // `addListener` method through that!
        CustomerService.Companion.addListener(new CustomerEventListener() {
            @Override
            public void customerSaved(@NotNull Customer customer) {
                System.out.println("[Companion] Saved customer via anonymous Java CustomerEventListener");
            }

            @Override
            public void customerDeleted(@NotNull Customer customer) {
                System.out.println("[Companion] Deleted customer via anonymous Java CustomerEventListener");
            }
        });
    }

    public static void addJavaCustomerEventListenerDirectly() {

        // However, after adding the `@JvmStatic` notifier to our Kotlin-side `CustomerService.addListener` function
        // then we CAN access it directly without having to go through the companion object!
        CustomerService.addListener(new CustomerEventListener() {
            @Override
            public void customerSaved(@NotNull Customer customer) {
                System.out.println("[Direct] Saved customer via anonymous Java CustomerEventListener");
            }

            @Override
            public void customerDeleted(@NotNull Customer customer) {
                System.out.println("[Direct] Deleted customer via anonymous Java CustomerEventListener");
            }
        });
    }


}
