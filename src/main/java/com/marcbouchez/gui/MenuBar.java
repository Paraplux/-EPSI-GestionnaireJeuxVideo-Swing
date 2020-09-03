package com.marcbouchez.gui;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuBar {

    private static JMenuBar menuBar;

    static {
        //Menu bar init
        menuBar = new JMenuBar();

        //Menu "Fichier"
        JMenu menuFichier = new JMenu( "Fichier" );
        menuFichier.setMnemonic( 'F' );

        JMenuItem menuQuitter = new JMenuItem( "Quitter" );
        menuQuitter.setMnemonic( 'Q' );
        menuQuitter.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK) );
        menuFichier.add(menuQuitter);

        menuBar.add(menuFichier);

        //Menu "Aller à"
        JMenu menuAller = new JMenu( "Aller à" );
        menuAller.setMnemonic( 'A' );

        JMenuItem menuJeux = new JMenuItem( "Jeux" );
        menuJeux.setMnemonic( 'J' );
        menuJeux.addActionListener(e -> SidePane.goToTab("Jeux"));
        menuAller.add(menuJeux);

        JMenuItem menuPlateformes = new JMenuItem( "Plateformes" );
        menuPlateformes.setMnemonic( 'P' );
        menuPlateformes.addActionListener(e -> SidePane.goToTab("Plateformes"));
        menuAller.add(menuPlateformes);

        JMenuItem menuCategories = new JMenuItem( "Catégories" );
        menuCategories.setMnemonic( 'C' );
        menuCategories.addActionListener(e -> SidePane.goToTab("Catégories"));
        menuAller.add(menuCategories);

        //Menu "Aide"
        JMenu menuAide = new JMenu( "Aide" );
        menuAide.setMnemonic( 'A' );

        JMenuItem menuVersion = new JMenuItem( "Version" );
        menuVersion.setMnemonic( 'V' );
        menuAide.add(menuVersion);


        menuBar.add(menuFichier);
        menuBar.add(menuAller);
        menuBar.add(menuAide);
    }

    public static JMenuBar getMenuBar() {
        return menuBar;
    }
}
