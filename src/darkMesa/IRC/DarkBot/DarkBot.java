package darkMesa.IRC.DarkBot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DarkBot {
	
	public String name = "BotName";
	public String password = "Password"; //:D
	public String server = "Server";
	public String channel = "#Channel";
	public Set<String> ops = new HashSet<String>();
	public Set<String> voices = new HashSet<String>();
	public Set<String> users = new HashSet<String>();
	public String prefix = "!";
	
	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.main.loadInfo();
		bot.t.start();
	}
	public void loadInfo() {
		String s = "";
		try {
			s = SLAPI.load("Darkbot.conf");
			System.out.println(s);
		} catch (FileNotFoundException e) {
			try {
				SLAPI.save(name + "," + password + "," + server + "," + channel + "," + prefix,"Darkbot.conf");
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
		String[] l  = s.split(",");
		name = l[0];
		System.out.print(l[0]);
		password = l[1];
		System.out.print(l[1]);
		server = l[2];
		System.out.print(l[2]);
		channel = l[3];
		System.out.print(l[3]);
		prefix = l[4];
		System.out.print(l[4]);
	}
}