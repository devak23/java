package com.ak.learning.patterns.adapter;

public interface SocketAdapter {
    Volt get120Volts();
    Volt get12Volts();
    Volt get3Volts();
}
