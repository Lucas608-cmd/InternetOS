//import org.apache.cassandra.thrift.Cassandra;  
//import org.apache.cassandra.thrift.Column;  
//import org.apache.cassandra.thrift.ColumnPath;  
//import org.apache.cassandra.thrift.ConsistencyLevel;  
//import org.apache.cassandra.thrift.InvalidRequestException;  
//import org.apache.cassandra.thrift.NotFoundException;  
//import org.apache.cassandra.thrift.TimedOutException;  
//import org.apache.cassandra.thrift.UnavailableException;  
//import org.apache.thrift.TException;  
//import org.apache.thrift.protocol.TBinaryProtocol;  
//import org.apache.thrift.transport.TSocket;  
//import org.apache.thrift.transport.TTransport;  
//import org.apache.thrift.transport.TTransportException;  
//public class CassandraTest {    
//	static Cassandra.Client cassandraClient;    
//    static TTransport socket;  
//    
//    public static void main(String[] args) throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException {    
//         /* ��ʼ������ */    
//         init();    
//     
//     
//         /* ѡ����Ҫ������Keyspaces�� �����������ݿ�ı� */    
//         String keyspace= "Keyspace1";    
//         String row = "employee";    
//     
//         /* ����һ��Table Name */    
//         String tableName = "Standard2";  
//           
//         /* ����һ����¼ */  
//         insertOrUpdate(keyspace,tableName,row,"name","happy birthday!",System.currentTimeMillis());  
//         /* ɾ��һ����¼ */  
//         //delete(keyspace,tableName,row,"name",System.currentTimeMillis());  
//         /* ��ȡһ����¼ (���ڲ����ɾ����ͬһ����¼,�п��ܻ��������Ŷ!��������!*/  
//         Column column = getByColumn(keyspace,tableName,row,"name", System.currentTimeMillis()); 
//
//         System.out.println("read row " + row);    
//         System.out.println("column name " + ":" + column.name);    
//         System.out.println("column value" + ":" + column.value);    
//         System.out.println("column timestamp" + ":" + (column.timestamp));    
//           
//         close();  
//     }  
//	public static void init() throws TTransportException{  
//		String server = "localhost";  
//        int port = 9160;    
//		socket = new TSocket(server,port);
//		System.out.println(" connected to " + server + ":" + port + ".");    
//     
//     
//         /* ָ��ͨ��Э��Ϊ��������Э�� */    
//         TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);    
//         cassandraClient = new Cassandra.Client(binaryProtocol);    
//     
//     
//         /* ����ͨ������ */    
//         socket.open();    
//	}/** 
//      * �����¼ 
//      */  
//     public static void insertOrUpdate(String tableSpace,String tableName, String rowParam,String ColumnName,String ColumnValue,long timeStamp)    
//        throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//         /* ѡ����Ҫ������Keyspaces�� ������ݱ����ڵĿռ�λ�� */    
//         String keyspace= tableSpace;  
//         /* �������ڵ��б� */  
//         String row = rowParam;    
//     
//         /* ����һ��column path */    
//         ColumnPath col = new ColumnPath(tableName);    
//         col.setColumn(ColumnName.getBytes());   
//           
//         /* ִ�в��������ָ��keysapce, row, col, ���������ݣ� ������������һ����timestamp�� ����һ����consistency_level  
//          * timestamp������������һ���Ա�֤�ģ� ��consistency_level�������������ݷֲ��Ĳ��ԣ�ǰ�ߵ�����������bigtable, ���ߵ�����������dynamo  
//          */    
//        cassandraClient.insert(keyspace, row, col,"i don't know".getBytes(), System.currentTimeMillis(), ConsistencyLevel.ONE);  
//     }  
//       
//     /** 
//      * ɾ����¼ 
//      */  
//     public static void delete(String tableSpace,String tableName, String rowParam,String ColumnName,long timeStamp)   
//        throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//         /* ѡ����Ҫ������Keyspaces�� ������ݱ����ڵĿռ�λ�� */    
//         String keyspace= tableSpace;  
//         /* �������ڵ��б� */  
//         String row = rowParam;    
//     
//         /* ����һ��column path */    
//         ColumnPath col = new ColumnPath(tableName);    
//         col.setColumn(ColumnName.getBytes());   
//           
//         /* ִ��ɾ��������ָ��keysapce, row, col�� ������������һ����timestamp�� ����һ����consistency_level  
//          * timestamp������������һ���Ա�֤�ģ� ��consistency_level�������������ݷֲ��Ĳ��ԣ�ǰ�ߵ�����������bigtable, ���ߵ�����������dynamo  
//          */    
//        cassandraClient.remove(keyspace, row, col, System.currentTimeMillis(), ConsistencyLevel.ONE);  
//     }  
//       
//     /**  
//      * ��ȡ����  
//      */  
//     public static Column getByColumn(String tableSpace,String tableName, String rowParam,String ColumnName,long timeStamp)   
//    throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException{  
//      /* ѡ����Ҫ������Keyspaces�� ������ݱ����ڵĿռ�λ�� */    
//      String keyspace= tableSpace;   
//      /* �������ڵ��б� */  
//      String row = rowParam;    
//  
//      /* ����һ��column path */    
//      ColumnPath col = new ColumnPath(tableName);    
//      col.setColumn(ColumnName.getBytes());   
//        
//      /* ִ�в�ѯ������ָ��keysapce, row, col�� timestamp  
//       * timestamp������������һ���Ա�֤�ģ� ��consistency_level�������������ݷֲ��Ĳ��ԣ�ǰ�ߵ�����������bigtable, ���ߵ�����������dynamo  
//       */    
//      Column column = cassandraClient.get(keyspace, row, col, ConsistencyLevel.ONE).column;    
//      cassandraClient.get(new ByteBuffer(keyspace.getBytes(), col, ConsistencyLevel.ONE);
//      return column;  
//     }  
//       
//       
//     /** 
//      * �رյ�ǰ��Զ�̷������� 
//      */  
//     public static void close() {  
//         socket.close();  
//    }  
//}
