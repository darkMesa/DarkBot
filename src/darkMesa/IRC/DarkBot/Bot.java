package darkMesa.IRC.DarkBot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

public class Bot extends PircBot {
	DarkBot main = new DarkBot();

	public Bot() {
		log("Initalizing bot...");
		this.setName(main.name);

		log("Joining server...");
		this.joinServer();
		log("Joining channel...");
		this.joinChannel();
	}
	
	@Override
	protected void onConnect() {
		load();

		super.onConnect();
	}

	@Override
	protected void onDisconnect() {
		saveAll();

		super.onDisconnect();
	}

	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		if(main.ops.contains(sender)) {
			this.op(channel, sender);
		}else if(main.voices.contains(sender)) {
			this.voice(channel, sender);
		}else if(main.users.contains(sender)){
			this.sendNotice(sender, "Welcome back " + sender);
		}else{
			this.sendNotice(sender, "If you have a question, don't hesitiate to ask but remember, we may be busy or unavalible.");
			addUser(sender);
		}
		super.onJoin(channel, sender, login, hostname);
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		Boolean perm = false;
		User[] users = this.getUsers(channel);

		for(int i = 0; i < users.length; i++) {
			if(users[i].isOp() && users[i].getNick().equals(sender)) {
				perm = true;
			}
		}

		String[] args = message.split(" ");

		if(perm) {
			if(args[0].equalsIgnoreCase(main.prefix + "save")) {
				if(args.length < 2) {
					saveAll();
					this.sendNotice(sender, "Saved files!");
				} else {
					if(args[1].equalsIgnoreCase("ops")) {
						saveOps();
						this.sendNotice(sender, "Saved OPs file!");
					} else if(args[1].equalsIgnoreCase("voices")) {
						saveVoices();
						this.sendNotice(sender, "Saved Voices file!");
					}
				}
			}

			if(args[0].equalsIgnoreCase(main.prefix + "op")) {
				addOp(args[1]);
				this.sendNotice(sender, "OP'd \"" + args[1] + "\"!");
			}

			if(args[0].equalsIgnoreCase(main.prefix + "deop")) {
				delOp(args[1]);
				this.sendNotice(sender, "DeOP'd \"" + args[1] + "\"!");
			}

			if(args[0].equalsIgnoreCase(main.prefix + "voice")) {
				addVoice(args[1]);
				this.sendNotice(sender, "Voice'd \"" + args[1] + "\"!");
			}

			if(args[0].equalsIgnoreCase(main.prefix + "devoice")) {
				delVoice(args[1]);
				this.sendNotice(sender, "DeVoice'd \"" + args[1] + "\"!");
			}
		}

		if(args[0].equalsIgnoreCase(main.prefix + "help")) {
			this.sendNotice(sender, "Commands Avaiable: !help, !git");
			this.sendNotice(sender, "OP Only Commands: !op, !deop, !voice, !devoice, !save");
		}
		
		if(args[0].equalsIgnoreCase(main.prefix + "git")) {
			this.sendNotice(sender, "Our GitHub is located at: https://github.com/darkMesa");
		}

		super.onMessage(channel, sender, login, hostname, message);
	}

	public void addOp(String s) {
		this.op(main.channel, s);
		log("OP'd \"" + s + "\"!");

		main.ops.add(s);
		log("\"" + s + "\" added to OPs file!");

		saveOps();
	}

	public void delOp(String s) {
		this.deOp(main.channel, s);
		log("DeOP'd \"" + s + "\"!");

		main.ops.remove(s);
		log("\"" + s + "\" removed from OPs file!");

		saveOps();
	}

	public void addVoice(String s) {
		this.voice(main.channel, s);
		log("Voice'd \"" + s + "\"!");

		main.voices.add(s);
		log("\"" + s + "\" added to Voices file!");

		saveVoices();
	}

	public void delVoice(String s) {
		this.deVoice(main.channel, s);
		log("DeVoice'd \"" + s + "\"!");

		main.voices.remove(s);
		log("\"" + s + "\" removed from Voices file!");

		saveVoices();
	}

	public void addUser(String s) {
		main.users.add(s);
		log(s + "has been added to the userslist");

		saveUsers();
	}

	private void joinServer() {
		try {
			log("Connecting...");
			this.connect(main.server);
			this.sendMessage("nickserv", "identify " + main.password);
			log("Connected!");
		} catch(NickAlreadyInUseException e1) {
			log("Nick in use, GHOSTing...");
			this.setAutoNickChange(true);
			joinServer();
			this.sendMessage("nickserv", "ghost " + main.name + " " + main.password);
			this.changeNick(main.name);
			log("Nick recovered!");
		} catch(IOException | IrcException e2) {
			e2.printStackTrace();
		}
	}

	private void joinChannel() {
		this.joinChannel(main.channel);
		log("Joined channel \"" + main.channel + "\"!");

		User[] users = this.getUsers(main.channel);
		for(int i = 0; i < users.length; i++) {
			String user = users[i].getNick();

			if(main.ops.contains(user)) {
				this.op(main.channel, user);
				log("OP'd \"" + user + "\"!");
			}
		}

	}

	private void saveOps() {
		try {
			SLAPI.save(main.ops, "ops.bin");
			log("Saved \"ops.bin\"!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void saveVoices() {
		try {
			SLAPI.save(main.voices, "voices.bin");
			log("Saved \"voices.bin\"!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void saveUsers() {
		try {
			SLAPI.save(main.users, "users.bin");
			log("Saved \"users.bin\"!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void saveAll() {
		try {
			SLAPI.save(main.ops, "ops.bin");
			log("Saved \"ops.bin\"!");
			SLAPI.save(main.voices, "voices.bin");
			log("Saved \"voices.bin\"!");
			SLAPI.save(main.users, "users.bin");
			log("Saved \"users.bin\"!");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try {
			main.ops = SLAPI.load("ops.bin");
			log("Loaded \"ops.bin\"!");
			main.voices = SLAPI.load("voices.bin");
			log("Loaded \"voices.bin\"!");
		} catch(FileNotFoundException e) {
			log("\"ops.bin\" and/or \"voices.bin\" not found!");
			log("Generating new files...");
			saveAll();
			log("New files generated!");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String s) {
		String[] time = Calendar.getInstance().getTime().toString().split(" ");
		System.out.println(time[3] + " [DB] " + s);
	}
}