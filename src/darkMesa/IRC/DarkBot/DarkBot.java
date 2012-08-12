package darkMesa.IRC.DarkBot;

import java.util.HashSet;
import java.util.Set;

public class DarkBot {
	
	public String name = "DarkBot";
	public String password = "password"; //:D
	public String server = "irc.esper.net";
	public String channel = "#darkmesa";
	public Set<String> ops = new HashSet<String>();
	public Set<String> voices = new HashSet<String>();
	public Set<String> users = new HashSet<String>();
	public String prefix = "!";
	
	public static void main(String[] args) {
		Bot bot = new Bot();
		bot.t.start();
	}
}