package co.marcin.novaguilds.command;

import co.marcin.novaguilds.NovaGuilds;
import co.marcin.novaguilds.basic.NovaGuild;
import co.marcin.novaguilds.util.LoggerUtils;
import co.marcin.novaguilds.util.NumberUtils;
import co.marcin.novaguilds.util.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAdminGuildSetLiveRegenerationTime implements CommandExecutor {
	private final NovaGuilds plugin;
	private final NovaGuild guild;

	public CommandAdminGuildSetLiveRegenerationTime(NovaGuilds pl, NovaGuild guild) {
		plugin = pl;
		this.guild = guild;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("novaguilds.admin.guild.liveregenerationtime")) {
			plugin.getMessageManager().sendNoPermissionsMessage(sender);
			return true;
		}

		String timeString = "";
		if(args.length > 1) {
			timeString = StringUtils.join(args," ");
		}

		int iseconds = StringUtils.StringToSeconds(timeString);
		long seconds = Long.parseLong(iseconds+"");

		long newregentime = NumberUtils.systemSeconds() + (seconds - plugin.getConfigManager().getGuildLiveRegenerationTime());
		LoggerUtils.debug("newregentime: " + newregentime);

		guild.setLostLiveTime(newregentime);
		plugin.getMessageManager().sendMessagesMsg(sender,"chat.admin.guild.timerest.set");

		return true;
	}
}
