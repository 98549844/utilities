package util;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotUtil {

    public static void clearConsole() throws AWTException {
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ALT);
        r.keyPress(KeyEvent.VK_BACK_SPACE);
        r.keyPress(KeyEvent.VK_BACK_SPACE);
        r.keyPress(KeyEvent.VK_ALT);
    }



}
