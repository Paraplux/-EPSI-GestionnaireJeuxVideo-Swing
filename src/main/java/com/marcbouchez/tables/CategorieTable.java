package com.marcbouchez.tables;

import com.marcbouchez.entities.Categorie;
import com.marcbouchez.interfaces.PaginationDataProvider;
import com.marcbouchez.pagination.PaginatedTableDecorator;
import com.marcbouchez.services.GestionnaireJeuxService;
import com.marcbouchez.utils.ObjectTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CategorieTable extends JPanel {

    private JTable table;

    private JPanel rootPane = new JPanel();
    private JPanel tablePane = new JPanel();
    private JPanel menuPane = new JPanel();

    private PaginationDataProvider<Categorie> dataProvider;
    private PaginatedTableDecorator<Categorie> paginatedDecorator;
    //BUTTONS
    private JButton addBtn = new JButton("Ajouter");
    private JButton backBtn = new JButton("Retour");

    public CategorieTable() {
        super();

        table = new JTable(createObjectDataModel());
        table.setAutoCreateRowSorter(true);
        dataProvider = createDataProvider();
        paginatedDecorator = PaginatedTableDecorator.decorate(table, dataProvider, new int[]{15, 30, 50, 75, 100}, 15);


        //LISTENER
        table.getSelectionModel().addListSelectionListener(e -> selectionListener(e));

        addBtn.addActionListener(e -> addBtnListener(e));

        backBtn.addActionListener(e -> backBtnListener(e));


        tablePane.add(paginatedDecorator.getContentPanel());
        tablePane.setLayout(new GridLayout(1, 1));

        menuPane.setLayout(new FlowLayout());
        menuPane.add(addBtn);
        menuPane.add(backBtn);

        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.PAGE_AXIS));
        rootPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        rootPane.add(tablePane);
        rootPane.add(menuPane);
    }

    private static TableModel createObjectDataModel() {
        return new ObjectTableModel<Categorie>() {
            @Override
            public Object getValueAt(Categorie c, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return c.getId();
                    case 1:
                        return c.getName();
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int column) {
                switch (column) {
                    case 0:
                        return "UID";
                    case 1:
                        return "Nom";
                }
                return null;
            }
        };
    }

    public JPanel getTablePane() {
        return rootPane;
    }

    private PaginationDataProvider<Categorie> createDataProvider() {

        final List<Categorie> categories = GestionnaireJeuxService.getToutesLesCategories();

        return new PaginationDataProvider<Categorie>() {
            @Override
            public int getTotalRowCount() {
                return categories.size();
            }

            @Override
            public List<Categorie> getRows(int startIndex, int endIndex) {
                return categories.subList(startIndex, endIndex);
            }
        };
    }

    private void selectionListener(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
            System.out.println("Cliked on row");
        }
    }

    private void addBtnListener(ActionEvent e) {
        System.out.println("Add button");
    }

    private void backBtnListener(ActionEvent e) {
        System.out.println("Back button");
    }


}
