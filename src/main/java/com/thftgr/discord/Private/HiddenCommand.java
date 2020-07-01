package com.thftgr.discord.Private;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HiddenCommand {
    public void event(MessageReceivedEvent e) {
        String cmd = e.getMessage().getContentRaw().substring(1);
        String[] array = cmd.split(" ");

        switch (array[0]){
            case "s":
            case "status":
                if (!e.getAuthor().getId().equals("368620104365244418")) break;

                if (array.length > 2) {
                    if (array.length > 3) {
                        for (int i = 3; i < array.length; i++) {
                            array[2] += " " + array[i];
                        }
                    }
                    new Thread(new PrivateThread.setBotStatus(array)).start();
                } else {
                    e.getChannel().sendMessage("!status, s[P, L, W] [String Status use \"\" ]").queue();
                    //new Message().sayMsg(e.getChannel(), "!status, s[P, L, W] [String Status use \"\" ]", null);
                }
                break;
        }
    }
}
