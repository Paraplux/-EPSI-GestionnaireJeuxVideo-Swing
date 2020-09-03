package com.marcbouchez.main;

import com.marcbouchez.gui.App;
import com.marcbouchez.services.GestionnaireJeuxService;

import javax.swing.*;

public class Main extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        GestionnaireJeuxService.loadData();
        App.load();

    }

}
