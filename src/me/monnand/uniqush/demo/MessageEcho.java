package me.monnand.uniqush.demo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.uniqush.android.MessageCenter;
import org.uniqush.android.MessageHandler;
import org.uniqush.client.Message;

import android.content.Context;
import android.util.Log;

public class MessageEcho implements MessageHandler {

	private String TAG = "ECHO";
	private Context context;

	public MessageEcho(Context context) {
		Log.i(TAG, "Constructing the handler...");
		this.context = context;
	}

	private void printMap(Map<String, String> header) {
		Iterator<Entry<String, String>> iter = header.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			Log.i(TAG, "[" + entry.getKey() + "=" + entry.getValue() + "]");
		}
	}

	private void printMessage(Message msg) {
		Map<String, String> header = msg.getHeader();
		printMap(header);
	}

	@Override
	public void onCloseStart() {
		Log.i(TAG, "onCloseStart");
	}

	@Override
	public void onClosed() {
		Log.i(TAG, "onClosed");

	}

	@Override
	public void onError(Exception e) {
		Log.i(TAG, "onErrorx " + e.toString());
	}

	@Override
	public void onMissingAccount() {
		// In real world, the app should ask to user to use a google account on
		// the device.
		Log.i(TAG, "Account missing");
	}

	@Override
	public void onServiceDestroyed() {
		Log.i(TAG, "onServiceDestroyed");
	}

	@Override
	public void onResult(int id, Exception e) {
		String reason = "";
		if (e != null) {
			reason = ": " + e.getMessage();
		}
		Log.i(TAG, "onResult: " + id + reason);

		if (id == 1) {
			Message msg = new Message();
			if (e == null) {
				msg.put("callid=" + id, "success");
			} else {
				msg.put("callid=" + id, e.toString());
			}
			MessageCenter.sendMessageToServer(context, 10, msg);
		}
	}

	@Override
	public void onMessageFromServer(String dstService, String dstUser,
			String id, Message msg) {
		Log.i(TAG, "Message Received from server with id " + id);
		/*
		 * if (msg.get("stop") != null) { this.center.stop(context); }
		 */
		if (msg.get("stop") != null) {
			MessageCenter.stop(context, -1);
		}
		// this.center.sendMessageToServer(this.context, 0, msg);
		printMessage(msg);

	}

	@Override
	public void onMessageFromUser(String dstService, String dstUser,
			String srcService, String srcUser, String id, Message msg) {
		Log.i(TAG, "Message Received from user with id " + id + "; service="
				+ srcService + "; username=" + srcUser + ": ");
		printMessage(msg);
		// this.center.sendMessageToUser(context, 1, service, username, msg,
		// 3600);

	}

	@Override
	public void onMessageDigestFromServer(String dstService, String dstUser,
			int size, String id, Map<String, String> parameters) {
		Log.i(TAG, "Message digest Received from server with id " + id + ": ");
		// this.center.requestMessage(context, 30, id);

	}

	@Override
	public void onMessageDigestFromUser(String dstService, String dstUser,
			String srcService, String srcUser, int size, String id,
			Map<String, String> parameters) {
		Log.i(TAG, "Message digest Received from user with message id " + id
				+ "; service=" + srcService + "; username=" + srcUser + ": ");
		// this.center.requestMessage(context, 30, id);
	}
}
