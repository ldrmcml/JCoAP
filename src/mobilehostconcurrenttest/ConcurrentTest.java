package mobilehostconcurrenttest;
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
public class ConcurrentTest { 
	private static int thread_num = 1000; 
	private static int client_num = 100; 
	private static Map keywordMap = new HashMap(); 
	private static AtomicInteger atomic = new AtomicInteger();
	static int count = 0;
	
	static Lock lock = new ReentrantLock();;
	

	public static void main(String[] args) throws InterruptedException { 
		int size = keywordMap.size(); 
		ExecutorService exec = Executors.newCachedThreadPool(); 
		// 50个线程可以同时访�? 
//		final Semaphore semp = new Semaphore(thread_num); 
		// 模拟2000个客户端访问 
		for (int index = 0; index < client_num; index++) {
			final int NO = index; 
			Runnable run = new Runnable() { 
				public void run() { 
					try { 
						String uri = "coap://10.183.7.156:5683/helloWorld";
//						String uri = "coap://192.168.191.2:5683/helloWorld";
//						String uri = "coap://192.168.1.122:5683/helloWorld";
						Request request = new GETRequest() {
		                    @Override
		                    protected void handleResponse(Response response) {
		                    	atomic.incrementAndGet();
		                    	System.out.println(atomic.get());
		                        //System.out.println(response.getPayloadString());
//		                    	lock.lock();
//		                    	try{
//		                    	    //处理任务
//		                    		count++;
//		                    	}catch(Exception ex){
//		                    	     
//		                    	}finally{
//		                    	    lock.unlock();   //释放�?
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
			}; 
			exec.execute(run); 
		} 
		//Thread.sleep(10000);
		// �?出线程池 
		//exec.shutdown(); 
		while(!exec.awaitTermination(1, TimeUnit.SECONDS));
		System.out.println("============"+atomic.get());
		
//		Thread.sleep(10000);
//		System.out.println(atomic.get());
//		
//		Thread.sleep(30000);
//		System.out.println(atomic.get());
	} 
}