package org.cesken.perfbook.chapter.threads;

public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world. I am going to sleep.");
        Thread.sleep(30000);
        System.out.println("I have woken up.");
    }
}
