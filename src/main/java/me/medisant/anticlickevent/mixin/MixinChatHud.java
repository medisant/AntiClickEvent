package me.medisant.anticlickevent.mixin;

import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = ChatHud.class, priority = 600)
public class MixinChatHud {

    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;I)V", at = @At("HEAD"), argsOnly = true)
    private Text injected(Text message) {
        MutableText newMessage = (MutableText) message;
        List<String> commands = new ArrayList<>();

        if (message.getStyle().getClickEvent() != null && message.getStyle().getClickEvent().getAction() == ClickEvent.Action.RUN_COMMAND) {
            message.getStyle().getClickEvent().getValue();
            commands.add(message.getStyle().getClickEvent().getValue());
        }

        for (Text sibling : message.getSiblings()) {
            if (sibling.getStyle().getClickEvent() != null && sibling.getStyle().getClickEvent().getAction() == ClickEvent.Action.RUN_COMMAND)
                if (!commands.contains(sibling.getStyle().getClickEvent().getValue()))
                    commands.add(sibling.getStyle().getClickEvent().getValue());
        }

        if (commands.size() == 0) return message;

        MutableText warning = (MutableText) Text.of(" §8[§c!§8]");
        StringBuilder commandWarning = new StringBuilder();
        for (int i = 0; i < commands.size(); i++) {
            String command = commands.get(i);
            commandWarning.append(command);
            if (i != commands.size() - 1) commandWarning.append("\n");
        }

        return newMessage.append(warning.setStyle(warning.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.of(commandWarning.toString())))));

    }

}
