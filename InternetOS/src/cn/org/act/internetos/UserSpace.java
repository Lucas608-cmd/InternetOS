package cn.org.act.internetos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.org.act.internetos.persist.Application;
import cn.org.act.internetos.persist.Pair;
import cn.org.act.internetos.signal.HttpSignalListener;
import cn.org.act.internetos.signal.MatchRule;
import cn.org.act.internetos.signal.Signal;
import cn.org.act.internetos.signal.SignalListener;

public class UserSpace {
	private static HashMap<String, UserSpace> toeknSpaceMap = new HashMap<String, UserSpace>();

	public static UserSpace getUserSpace(String token) {
		if (!toeknSpaceMap.containsKey(token))
			toeknSpaceMap.put(token, new UserSpace(token));
		return toeknSpaceMap.get(token);
	}

	private String usertoken;

	private BlockingQueue<String> messageQueue = new LinkedBlockingQueue<String>();

	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

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
	}

	public List<SignalListener> getMatchedSignalListener(Signal signal) {
		List<SignalListener> ans = new ArrayList<SignalListener>();

		ModuleConstructor.getAppDAO().getApps(usertoken);
		for (Application app : ModuleConstructor.getAppDAO().getApps(usertoken)) {
			for (SignalListener listener : app
					.getListeners()) {
				if (listener.match(signal))
					ans.add(listener);
			}
		}
		return ans;
	}
}