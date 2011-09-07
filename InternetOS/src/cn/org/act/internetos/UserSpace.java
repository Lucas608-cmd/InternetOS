package cn.org.act.internetos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.org.act.internetos.activities.Activity;
import cn.org.act.internetos.activities.ActivityManager;
import cn.org.act.internetos.activities.ClientPageListener;
import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.Pair;
import cn.org.act.internetos.signal.ClientSignalListener;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;
import cn.org.act.tools.Observable;

public class UserSpace extends Observable{
	/**
	 * @tag group
	 * name = "˽�ж���"
	 */
	private static HashMap<String, UserSpace> toeknSpaceMap = new HashMap<String, UserSpace>();

	public static UserSpace getUserSpace(String token) {
		if (!toeknSpaceMap.containsKey(token))
			toeknSpaceMap.put(token, new UserSpace(token));
		return toeknSpaceMap.get(token);
	}

	/**
	 * @tag group
	 * name = "˽�ж���"
	 */
	private String usertoken;
	/**
	 * @tag group
	 * name = "˽�ж���"
	 */
	private ActivityManager activityManager;
	/**
	 * @tag group
	 * name = "˽�ж���"
	 */
	private BlockingQueue<String> messageQueue;

	/**
	 * @tag group
	 * name = "�ͻ������"
	 */
	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

	/**
	 * @tag group
	 * name = "�ͻ������"
	 */
	public void notify(String cMessage) throws IOException {
		try {
			messageQueue.put(cMessage);
		} catch (Exception ex) {
			IOException t = new IOException();
			t.initCause(ex);
			throw t;
		}
	}

	public UserSpace(String token) {
		usertoken = token;
		activityManager = new ActivityManager(this);
		messageQueue = new LinkedBlockingQueue<String>();
	}
	/**
	 * @tag group
	 * name = "Ӧ�ù������"
	 */
	public List<Application> getApps(){
		 return ModuleConstructor.getAppDAO().getApps(usertoken);			
	}
	/**
	 * @tag group
	 * name = "Ӧ�ù������"
	 */
	private void tes(){
		
	}
	/**
	 * @tag group
	 * name = "�ά�����"
	 */
	private void t(){
		
	}
	
	//TODO this function did to irrelevant work
	/**
	 * @tag group
	 * name = "�ź�ת�����"
	 */
	public List<SignalListener> getMatchedSignalListener(Signal signal) {
		//TODO  system signal
		List<SignalListener> ans = getSystemSignalListener();

		List<Application> apps = getApps();
		for (Application app : apps) {
			
			if(!app.isInited())
				app.init(this); //TODO  init a app
			
			//TODO activeListeners
			Activity activity = new Activity(app.getName(),"listener",Activity.Stopped);
			this.activityManager.setActivity(activity);
			
			for (SignalListener listener : app
					.getListeners()) {
				if (listener.match(signal))
					ans.add(listener);
			}
		}
		
		
		return ans;
	}
	
	/**
	 * @tag group
	 * name = "�ź�ת�����"
	 */
	private List<SignalListener> getSystemSignalListener(){
		List<SignalListener> ans = new ArrayList<SignalListener>();
		ans.add(new ClientPageListener(this));
		
		return ans;
	}
	

	/**
	 * @tag group
	 * name = "�ά�����"
	 */
	public ActivityManager getActivityManager() {
		return activityManager;		
	}

	/**
	 * @tag group
	 * name = "˽�ж���"
	 */
	private boolean clientAlive = false;
	
	/**
	 * @tag group
	 * name = "�ͻ������"
	 */
	public boolean isClientActived() {
		return clientAlive;
	}

	/**
	 * @tag group
	 * name = "�ͻ������"
	 */
	public void clientTick() {
		clientAlive = true;
		super.onChanged("clientAlive");
	}

	/**
	 * @tag group
	 * name = "�ͻ������"
	 */
	public void waitingClient() {
		clientAlive = true;
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				clientAlive = false;
			}}).run();
	}
	
}
