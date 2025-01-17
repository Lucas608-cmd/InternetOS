import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HSuperColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.SuperSliceQuery;

import org.apache.cassandra.thrift.InvalidRequestException;  
import org.apache.cassandra.thrift.NotFoundException;  
import org.apache.cassandra.thrift.TimedOutException;  
import org.apache.cassandra.thrift.UnavailableException;  
import org.apache.thrift.TException;  
public class test {
	
	static void Test(){
		Cluster cluster = HFactory.getOrCreateCluster("TestCluster",
			    new CassandraHostConfigurator("localhost:9160"));
		Keyspace keyspace = HFactory.createKeyspace("Keyspace1", cluster);
//		Mutator<String> mutator = HFactory.createMutator(keyspace,new StringSerializer());
//		mutator.insert("user1","Standard1", HFactory.createStringColumn("name", "value"));
//		
//		ArrayList<HColumn<String,String>> columns = new ArrayList<HColumn<String,String>>();
//		columns.add(HFactory.createStringColumn("name", "demo"));
//		columns.add(HFactory.createStringColumn("config", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//"<Application>"+
//"		<Name>Test</Name>"+
//"		<Listeners>"+
//"			<HttpListener>"+
//"					<URL>http://localhost:8080/DemoApp/listener</URL>"+
//"					<MatchRule type=\"urlregex\">"+
//"						.*/signal/send.*"+
//"					</MatchRule>"+
//"			</HttpListener>"+
//"		</Listeners>"+
//"	</Application>"));
//		mutator.insert("user", "Applications", 
//				HFactory.createSuperColumn("app1", columns, new StringSerializer(), new StringSerializer(), new StringSerializer()));
//		

		SuperSliceQuery<String,String,String,String> query= HFactory.createSuperSliceQuery(keyspace, new StringSerializer(), new StringSerializer(), new StringSerializer(), new StringSerializer());
		String start="";
		String finish="";
		boolean reversed = false;
		int count =1000;
		query.setColumnFamily("Applications").setKey("user")
		.setRange(start,finish, reversed, count);
		for(HSuperColumn<String, String, String> app: query.execute().get().getSuperColumns()){
			for(HColumn<String, String> attr: app.getColumns()){

				if( attr.getName().equals("name") )
					System.out.println("name:"+attr.getValue());
				else if( attr.getName().equals("config") )
					System.out.println("config:"+attr.getValue());
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, InvalidRequestException, UnavailableException, TimedOutException, TException, NotFoundException {
//		//Part One
		
		Test();
//		TTransport trans = new TSocket("192.168.1.216", 9160);
//		TProtocol proto = new TBinaryProtocol(trans);
//		Cassandra.Client client = new Cassandra.Client(proto);
//		trans.open();
//
//		//Part Two
//		String keyspace = "Keyspace1";
//		String cf = "Standard2";
//
//		for (int i=0;i<10000;i++)
//		{
//			String key = "fly3q"+i;
//			long timestamp = new Date().getTime();
//			ColumnPath path = new ColumnPath(cf);
//			path.setColumn("id".getBytes("UTF-8"));
//			client.insert(keyspace, key, path, String.format("520.%d", i).getBytes("UTF-8"), timestamp, ConsistencyLevel.ONE);
//			path.setColumn("action".getBytes("UTF-8"));
//			client.insert(keyspace, key, path, String.format("Hello, Cassandra!.%d", i).getBytes("UTF-8"), timestamp, ConsistencyLevel.ONE);
//
//			//Part Three
//			path.setColumn("id".getBytes("UTF-8"));
//			ColumnOrSuperColumn cc = client.get(keyspace, key, path, ConsistencyLevel.ONE);
//			Column c = cc.getColumn();
//			String value = new String(c.value, "UTF-8");
//			System.out.println(value);
//
//			path.setColumn("action".getBytes("UTF-8"));
//			ColumnOrSuperColumn cc2 = client.get(keyspace, key, path, ConsistencyLevel.ONE);
//			Column c2 = cc2.getColumn();
//			String value2 = new String(c2.value, "UTF-8");
//			System.out.println(value2);
//		}
//
//		//Part four
//		trans.close();

	}
	
	
	
public static void main1(String[] args) throws InterruptedException
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
