package darkMesa.IRC.DarkBot;

import java.util.HashSet;
import java.util.Set;

public class DarkBot {
	
	public String name = "DarkBot";
	public String password = "robert312";
	public String server = "irc.esper.net";
	public String channel = "#darkmesa";
	public Set<String> ops = new HashSet<String>();
	public Set<String> voices = new HashSet<String>();
	public String prefix = "!";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Bot bot = new Bot();
	}
}