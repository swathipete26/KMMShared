package com.ather.kmmshared

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}