package com.mbvreddy.test.java.util.concurrent;

import java.util.concurrent.*;

public class ThreadPoolExecutorTest {
	public static void main(String[] args) throws Exception {
		//test Executors.newFixedThreadPool
		ExecutorService fixedTPE = Executors.newFixedThreadPool(5);

		//Check worker threads on empty task queue
		System.out.println("["+Thread.currentThread().getName()+"] Just created TPE");
		com.ibm.jvm.Dump.JavaDump();

		//Execute 1 task - only one worker thread is created
		System.out.println("["+Thread.currentThread().getName()+"] Added 1 task and executed");
		fixedTPE.execute(new TaskThread());
		com.ibm.jvm.Dump.JavaDump();

		//Execute 10 tasks in a loop - forcing 5 worker threads to be created.
		System.out.println("["+Thread.currentThread().getName()+"] Added 10 task and executed");
		for(int i=0; i<10; i++) { fixedTPE.execute(new TaskThread()); }
		com.ibm.jvm.Dump.JavaDump();

		//Sleep for 5 seconds to give time for worker threads to complete.
		Thread.sleep(10000);
		com.ibm.jvm.Dump.JavaDump();

		System.out.println();
		System.out.println();
		System.out.println();

		//test Executors.newSingleThreadExecutor
		ExecutorService singleThreadedTPE = Executors.newSingleThreadExecutor();

		//Execute 10 tasks in a loop - executes sequentially as there is only one worker thread.
		System.out.println("["+Thread.currentThread().getName()+"] Added 10 task and executed");
		for(int i=0; i<10; i++) { singleThreadedTPE.execute(new TaskThread()); }
		com.ibm.jvm.Dump.JavaDump();

		//This program doesn't terminate as worker threads are still alive. To force this to quit, call shutdown and awaitTermination api.
		fixedTPE.shutdown();
		fixedTPE.awaitTermination(30, TimeUnit.SECONDS);
		singleThreadedTPE.shutdown();
		singleThreadedTPE.awaitTermination(30, TimeUnit.SECONDS);

		//main thread is exiting
		System.out.println("["+Thread.currentThread().getName()+"] Added 10 task and executed");
	}
}

class TaskThread implements Runnable {
	public void run() {
		System.out.println("["+Thread.currentThread().getName()+"] Task execution started");
		try { Thread.sleep(1000); } catch (Exception e) {}
		System.out.println("["+Thread.currentThread().getName()+"] Task execution ended");
	}
}