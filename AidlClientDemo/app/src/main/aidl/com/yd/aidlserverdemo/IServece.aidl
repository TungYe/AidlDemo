// IServece.aidl
package com.yd.aidlserverdemo;

import com.yd.aidlserverdemo.bean.Animal;

// Declare any non-default types here with import statements

interface IServece {

    int getProcessId();
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    Animal getAnimal();

}
