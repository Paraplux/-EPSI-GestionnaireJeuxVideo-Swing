package com.marcbouchez.gui;

import com.marcbouchez.guicomponents.ButtonTabComponent;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

public class MainPane {

    private static JTabbedPane mainPane;
    private static JPanel welcomePane;
    private static JLabel welcomeLabel;

    static {
        //Panel init
        mainPane = new JTabbedPane();

        //Welcome tab
        welcomePane = new JPanel();
        welcomeLabel = new JLabel("<html><body style='text-align:center'>" +
                                        "Bienvenue dans votre gestionnaire de jeux vidéos ! " +
                                        "<br> Vous pouvez ouvrir un nouvel onglet via le menu latéral</body></html>");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(welcomeLabel.getForeground().darker());
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(16F));

        welcomePane.setLayout(new GridLayout(0, 1));
        welcomePane.add(welcomeLabel);
        mainPane.addTab("Bienvenue", null, welcomePane, "Bienvenue ");
        mainPane.setTabComponentAt(0, new ButtonTabComponent(mainPane));
    }

    public static JTabbedPane getMainPane() {
        return mainPane;
    }
}
