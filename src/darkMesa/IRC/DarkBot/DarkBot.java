package darkMesa.IRC.DarkBot;

import java.util.HashSet;
import java.util.Set;

public class DarkBot {
	
	public String name = "Bot";
	public String password = "Password"; //:D
	public String server = "Server";
	public String channel = "#Channel";
	public Set<String> ops = new HashSet<String>();
	public Set<String> voices = new HashSet<String>();
	public Set<String> users = new HashSet<String>();
	public String prefix = "!";
	
	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.t.start();
	}
}