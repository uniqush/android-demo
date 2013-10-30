package me.monnand.uniqush.demo;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import android.content.Context;

import org.uniqush.android.ConnectionInfo;
import org.uniqush.android.MessageHandler;

public class UserInfoProvider implements org.uniqush.android.UserInfoProvider {

	private RSAPublicKey pubKey;
	private ConnectionInfo cinfo;
	private Context context;

	public UserInfoProvider(Context context) {
		this.context = context;
		BigInteger modulus = new BigInteger(
				"17388413383649711290254825310339272211319767525363227404074762677568514182143042061437868796167591657550511002079944736281939887225873458139996723826487428401781326377898963252196351565707602724215593767581359596805150972910866410145077679809190513515791700412131297152963158161216863933118912237764396054712362877219981113432778260759907285938711897115187091296093563327334234754294283390985106557355671509013541665508032935881514071942658154269549566084778139622274561728679270315969220599430415442568612476273211334694395646067337896362196452349792104972786584396268761019495750809374141828098785614861562903854521");
		BigInteger exp = new BigInteger("65537");
		KeySpec keyspec = new RSAPublicKeySpec(modulus, exp);
		KeyFactory kf = null;
		try {
			kf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pubKey = null;
		try {
			pubKey = (RSAPublicKey) kf.generatePublic(keyspec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.cinfo = new ConnectionInfo("10.0.2.2", 8964, "service", "monnand",
				true);
	}

	@Override
	public String getToken(String service, String username) {
		return "token/password";
	}

	@Override
	public RSAPublicKey getPublicKey(String addr, int port) {
		return this.pubKey;
	}

	@Override
	public String[] getSenderIds() {
		String[] senderIds = new String[1];
		senderIds[0] = "584369913796";
		return senderIds;
	}

	@Override
	public MessageHandler getMessageHandler(String service, String username) {
		return new MessageEcho(this.context);
	}

	@Override
	public ConnectionInfo getConnectionInfo() {
		return cinfo;
	}

}
