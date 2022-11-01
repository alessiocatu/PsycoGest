package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.ui.GraphicConst;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {
    private static final int SCROLL_BAR_ALPHA_ROLLOVER = 150;
    private static final int SCROLL_BAR_ALPHA = 100;
    private static final int THUMB_BORDER_SIZE = 2;
    private static final int THUMB_SIZE = 8;
    private static final Color THUMB_COLOR = GraphicConst.BACKGROUND_DARK;

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setPreferredSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setMaximumSize(new Dimension(0, 0));
        return jbutton;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        c.setBackground(GraphicConst.BACKGROUND_LIGHT);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        int alpha = isThumbRollover() ? SCROLL_BAR_ALPHA_ROLLOVER : SCROLL_BAR_ALPHA;
        int orientation = scrollbar.getOrientation();
        int arc = THUMB_SIZE;
        int x = thumbBounds.x + THUMB_BORDER_SIZE;
        int y = thumbBounds.y + THUMB_BORDER_SIZE;

        int width = orientation == JScrollBar.VERTICAL ?
                THUMB_SIZE : thumbBounds.width - (THUMB_BORDER_SIZE * 2);
        width = Math.max(width, THUMB_SIZE);

        int height = orientation == JScrollBar.VERTICAL ?
                thumbBounds.height - (THUMB_BORDER_SIZE * 2) : THUMB_SIZE;
        height = Math.max(height, THUMB_SIZE);

        Graphics2D graphics2D = (Graphics2D) g.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(new Color(THUMB_COLOR.getRed(),
                THUMB_COLOR.getGreen(), THUMB_COLOR.getBlue(), alpha));
        graphics2D.fillRoundRect(x, y, width, height, arc, arc);
        graphics2D.dispose();
    }
}
