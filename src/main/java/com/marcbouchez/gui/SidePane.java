package com.marcbouchez.gui;

import com.marcbouchez.guicomponents.ButtonTabComponent;
import com.marcbouchez.tables.CategorieTable;
import com.marcbouchez.tables.JeuTable;
import com.marcbouchez.tables.PlateformeTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SidePane {

    private static JPanel sidePane;

    private static JLabel sidePaneTitle;
    private static JLabel jeuxTabButton;
    private static JLabel plateformesabButton;
    private static JLabel categoriesTabButton;

    //Colors
    private static final Color MAIN = UIManager.getColor("Panel.background");
    private static final Color MAINBRIGHTER = UIManager.getColor("Panel.background").brighter();


    static {
        //Panel init with default size
        sidePane = new JPanel();
        sidePane.setPreferredSize(new Dimension(200, 600));
        sidePane.setBackground(MAIN);

        //Creating title
        sidePaneTitle = new JLabel("Navigation");
        sidePaneTitle.setBorder(new EmptyBorder(10, 10, 10, 10));
        sidePaneTitle.setBackground(MAIN);
        sidePaneTitle.setOpaque(true);
        sidePaneTitle.setHorizontalAlignment(JLabel.CENTER);

        //Creating entries
        List<JLabel> sidePaneEntries = new ArrayList<>();
        jeuxTabButton = new JLabel("Jeux");
        plateformesabButton = new JLabel("Plateformes");
        categoriesTabButton = new JLabel("Catégories");
        sidePaneEntries.add(jeuxTabButton);
        sidePaneEntries.add(plateformesabButton);
        sidePaneEntries.add(categoriesTabButton);

        //Styling entries
        for (JLabel entry : sidePaneEntries) {
            entry.setBackground(MAIN);
            entry.setOpaque(true);
            entry.setBorder(new EmptyBorder(10, 10, 10, 50));
            entry.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    entry.setBackground(MAINBRIGHTER);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    entry.setBackground(MAIN);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseExited(e);
                    goToTab(entry.getText());
                }
            });
        }

        //Styling side pane
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;// your code goes here

        sidePane.setLayout(new GridBagLayout());

        //Adding title to the side pane
        sidePane.add(sidePaneTitle, gbc);
        //Adding entries to the side pane
        sidePane.add(jeuxTabButton, gbc);
        sidePane.add(plateformesabButton, gbc);
        sidePane.add(categoriesTabButton, gbc);


        gbc.weightx = 1;
        gbc.weighty = 1;
        sidePane.add(new JLabel(" "), gbc);
    }

    public static JPanel getSidePane() {
        return sidePane;
    }

    public static void goToTab (String entryName) {
        JTabbedPane mainPane = MainPane.getMainPane();
        int tabsCount = MainPane.getMainPane().getTabCount();

        //-1 if it doesn't exist
        int indexOfCurrentTab = getTabIndexFrom(entryName);


        if (indexOfCurrentTab == -1) {
            mainPane.addTab(entryName, null, getPanel(entryName), "Listing " + entryName);
            mainPane.setTabComponentAt(tabsCount, new ButtonTabComponent(mainPane));
            mainPane.setSelectedIndex(tabsCount);

            System.out.println("TabCount = " + tabsCount + ", index = " + indexOfCurrentTab + ". Ouverture d'un nouvel onglet");
        } else {
            System.out.println("TabCount = " + tabsCount + ", index = " + indexOfCurrentTab + ". Onglet déjà ouvert");
            mainPane.setSelectedIndex(indexOfCurrentTab);
        }
    }

    private static JPanel getPanel(String panelName) {
        switch (panelName) {
            case "Jeux":
                JeuTable jeuTable = new JeuTable();
                return jeuTable.getTablePane();
            case "Plateformes" :
                PlateformeTable plateformeTable = new PlateformeTable();
                return plateformeTable.getTablePane();
            case "Catégories" :
                CategorieTable categorieTable = new CategorieTable();
                return categorieTable.getTablePane();
            default:
                return new JPanel();
        }
    }

    private static int getTabIndexFrom(String panelName) {
        JTabbedPane mainPane = MainPane.getMainPane();
        for (int i = 0; i < mainPane.getTabCount(); i++) {
            if (mainPane.getTitleAt(i).equals(panelName)) {
                return i;
            }
        }
        return -1;
    }
}
