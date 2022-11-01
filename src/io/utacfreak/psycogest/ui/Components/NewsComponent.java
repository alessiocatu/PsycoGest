package io.utacfreak.psycogest.ui.Components;

import io.utacfreak.psycogest.ui.GraphicConst;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

public class NewsComponent extends JPanel implements Observer {
    static private NewsComponent n;
    private JTextPane news;
    private static final int SIZE = 6;
    private static Queue<String> lines;

    private NewsComponent(){
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        news = new JTextPane();
        StyledDocument documentStyle = news.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        news.setForeground(GraphicConst.TEXT_LIGHT);
        news.setFont(new Font(Font.DIALOG,  Font.BOLD, 15));
        news.setOpaque(false);
        news.setEditable(false);
        lines = new LinkedList<>();
        this.add(news);
    }

    public static NewsComponent getNewsComponent(){
        if(n != null)
            return n;
        return n = new NewsComponent();
    }

    private static void addLines(String line){
        if(lines.size() == SIZE) {
            lines.poll();
        }
        lines.offer(line);
    }

    private static String printQueue(){
        StringBuilder s = new StringBuilder();
        Object line[] = lines.toArray();
        for(int i=0; i < line.length; i++){
            s.append(line[i].toString() + "\n");
        }

        return s.toString();
    }

    @Override
    public void update(Observable o, Object arg) {
        String txt = (String)arg;
        addLines(txt);
        news.setText(printQueue());
    }
}
