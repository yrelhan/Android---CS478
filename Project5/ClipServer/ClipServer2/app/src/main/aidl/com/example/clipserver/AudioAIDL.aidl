// AudioAIDL.aidl
package com.example.clipserver;

// Declare any non-default types here with import statements
interface AudioAIDL {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void play(String songName);
     void pause();
     void stop();
     void resume();
     void stopService();
     }
