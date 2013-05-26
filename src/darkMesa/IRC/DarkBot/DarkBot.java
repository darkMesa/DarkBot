package darkMesa.IRC.DarkBot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DarkBot {
	
	public String name = "BotName";
	public String password = "Password"; //:D
	public String server = "irc.mibbit.net";
	public String channel = "#Channel";
	public Set<String> ops = new HashSet<String>();
	public Set<String> voices = new HashSet<String>();
	public Set<String> users = new HashSet<String>();
	public String prefix = "!";
	
	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.t.start();
	}
	public void loadInfo() {
		String [] s = {name,password,server,channel,prefix};
		try {
			s = SLAPI.load("Darkbot.conf");
			System.out.println(s);
		} catch (FileNotFoundException e) {
			try {
				SLAPI.save(s,"Darkbot.conf");
				loadInfo();
				return;
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		name = s[0];
		System.out.println(s[0]);
		password = s[1];
		System.out.println(s[1]);
		server = s[2];
		System.out.println(s[2]);
		channel = s[3];
		System.out.println(s[3]);
		prefix = s[4];
		System.out.println(s[4]);
	}
}