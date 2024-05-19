package mvc.gui;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Class serving as the container of the GUI configuration values.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class GUIConfig {
    public static final Dimension WINDOW_SIZE = new Dimension(960, 480);

    public static final int SMALL_PADDING = 5;
    public static final int MEDIUM_PADDING = 10;
    public static final int LARGE_PADDING = 20;

    public static final int BORDER_THICKNESS = 1;

    public static final Color PRIMARY_TEXT_COLOR = Color.WHITE;
    public static final Color SECONDARY_TEXT_COLOR = Color.GRAY;
    public static final Color PRIMARY_BACKGROUND_COLOR = Color.DARK_GRAY;
    public static final Color SECONDARY_BACKGROUND_COLOR = Color.BLACK;
    public static final Color PRIMARY_BORDER_COLOR = Color.WHITE;
    public static final Color SECONDARY_BORDER_COLOR = Color.GRAY;
}
