package com.marcbouchez.gui;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    public App() {
        super();
        this.setJMenuBar(MenuBar.getMenuBar());

        JSplitPane containerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                SidePane.getSidePane(),
                MainPane.getMainPane());

        containerPane.setResizeWeight(0.1);
        containerPane.setDividerSize(5);
        JPanel rootPane = (JPanel) this.getContentPane();
        rootPane.add(containerPane);
    }

    public static void load() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            System.out.println(UIManager.getDefaults().get("TabbedPane.font"));
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println("Bug look and feel");
            e.getStackTrace();
        }

        App app = new App();
        app.setTitle("Gestionnaire de jeux vidéos");
        app.setSize(new Dimension(1200, 600));
        app.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //app.pack();
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }


}
