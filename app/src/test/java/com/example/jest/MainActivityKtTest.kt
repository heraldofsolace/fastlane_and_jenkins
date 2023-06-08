package com.example.jest

import org.junit.Assert.*

import org.junit.Test

class MainActivityKtTest {

    @Test
    fun incrementCounter() {
        val count=0
        val result=com.example.jest.incrementCounter(count)
        assertEquals(count+1,result)
    }
}