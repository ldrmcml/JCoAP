package test;
import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException;
import java.io.InputStreamReader; 
import java.io.PrintWriter; 
import java.net.HttpURLConnection; 
import java.net.URL; 
import java.util.HashMap; 
import java.util.Map; 
import java.util.concurrent.ExecutorService; 
import java.util.concurrent.Executors; 
import java.util.concurrent.Semaphore; 
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import coap.GETRequest;
import coap.Request;
import coap.Response;
public class CopyOfConcurrentTest { 
	private static int thread_num = 1000; 
	private static int client_num = 50; 
	private static Map keywordMap = new HashMap(); 
	private static AtomicInteger atomic = new AtomicInteger();
	static int count = 0;
	
	static Lock lock = new ReentrantLock();;
	

	public static void main(String[] args) throws InterruptedException { 
		int size = keywordMap.size(); 
		// TODO Auto-generated method stub 
		ExecutorService exec = Executors.newCachedThreadPool(); 
		// 50个线程可以同时访问 
//		final Semaphore semp = new Semaphore(thread_num); 
		// 模拟2000个客户端访问 
		for (int index = 0; index < client_num; index++) {
			final int NO = index; 
					try { 
						String uri = "coap://10.183.4.237:5683/helloWorld";
//						String uri = "coap://10.183.10.42:5683/helloWorld";
//						String uri = "coap://192.168.191.2:5683/helloWorld";
						Request request = new GETRequest() {
		                    @Override
		                    protected void handleResponse(Response response) {
		                    	atomic.incrementAndGet();
		                        System.out.println(response.getPayloadString());
//		                    	lock.lock();
//		                    	try{
//		                    	    //处理任务
//		                    		count++;
//		                    	}catch(Exception ex){
//		                    	     
//		                    	}finally{
//		                    	    lock.unlock();   //释放锁
//		                    	}
		                    }
		                };
		                request.setURI(uri);
		                try {
		                    request.execute();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
					} catch (Exception e) { 
						e.printStackTrace(); 
					} 
		} 
		Thread.sleep(5000);
		System.out.println(atomic.get());
		
//		Thread.sleep(2000);
//		System.out.println(atomic.get());
//		
//		Thread.sleep(30000);
//		System.out.println(atomic.get());
	} 
}