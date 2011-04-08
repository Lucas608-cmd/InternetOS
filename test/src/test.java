import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;


public class test {
public static void main(String[] args) throws InterruptedException
{
	final BlockingQueue<String> messageQueue= new LinkedBlockingQueue<String>();
	ExecutorService executorService = Executors.newFixedThreadPool(10);
	final Future<?> task = executorService.submit(new Runnable(){

		@Override
		public void run() {
			String a = null;
			try {
				System.out.println("1");
				a = messageQueue.take();
				
				
			} catch (InterruptedException e) {
				try {
					System.out.println("2");
					Thread.sleep(2000);
					System.out.println("3");
				} catch (InterruptedException e2) {
					System.out.println("4");
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				System.out.println("5");
				if(a == null){
					try {
						System.out.println("6");
						messageQueue.put(a);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						System.out.println("7");
						e1.printStackTrace();
					}
				}
				e.printStackTrace();
			}
		}
		
	});
	Thread.sleep(1);
	task.cancel(true);
}
}
