package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

import org.lwjgl.input.Keyboard;

// Change 'melonclient' to your client name :D
import melonclient.Client;
import melonclient.SessionChanger;

public class GuiLogin extends GuiScreen
{
	private GuiMainMenu guimainmenu;
	private GuiTextField loginNameField;
	private GuiTextField loginPassField;
	private String loginName;
	private String loginPass;

    public GuiLogin(GuiMainMenu guiMainMenu) {
		this.guimainmenu = guiMainMenu;
	}

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.loginNameField.updateCursorCounter();
        this.loginPassField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	Client.getInstance().getDiscordRP().update("Signing In", "Login Menu");
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, "Cancel"));
        this.loginNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.loginNameField.setMaxStringLength(128);
        this.loginNameField.setFocused(true);
        this.loginNameField.setText("username");
        this.loginPassField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
        this.loginPassField.setMaxStringLength(128);
        this.loginPassField.setText("password");
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
            	this.mc.displayGuiScreen(new GuiMainMenu());
            }
            else if (button.id == 0)
            {
                this.loginName = this.loginNameField.getText();
                this.loginPass = this.loginPassField.getText();
                SessionChanger.getInstance().setUser(this.loginName, this.loginPass);
                this.mc.displayGuiScreen(new GuiMainMenu());
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 who toggle full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.loginNameField.textboxKeyTyped(typedChar, keyCode);
        this.loginPassField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15)
        {
            this.loginNameField.setFocused(!this.loginNameField.isFocused());
            this.loginPassField.setFocused(!this.loginPassField.isFocused());
        }

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        ((GuiButton)this.buttonList.get(0)).enabled = this.loginPassField.getText().length() > 0 && this.loginPassField.getText().split(":").length > 0 && this.loginNameField.getText().length() > 0;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginPassField.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginNameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Login", this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, "Username", this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 94, 10526880);
        this.loginNameField.drawTextBox();
        this.loginPassField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
