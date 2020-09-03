package com.marcbouchez.tables;

import com.marcbouchez.entities.Plateforme;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlateformeTable extends JPanel {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private JTable table;
    private JPanel rootPane = new JPanel();
    private JPanel tablePane = new JPanel();
    private JPanel menuPane = new JPanel();

    private PaginationDataProvider<Plateforme> dataProvider;
    private PaginatedTableDecorator<Plateforme> paginatedDecorator;
    //BUTTONS
    private JButton addBtn = new JButton("Ajouter");
    private JButton backBtn = new JButton("Retour");

    public PlateformeTable() {
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
        return new ObjectTableModel<Plateforme>() {
            @Override
            public Object getValueAt(Plateforme p, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return p.getId();
                    case 1:
                        return p.getName();
                    case 2:
                        return p.getDate().format(dateFormat);
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
                    case 2:
                        return "Sortie";
                }
                return null;
            }
        };
    }

    public JPanel getTablePane() {
        return rootPane;
    }

    private PaginationDataProvider<Plateforme> createDataProvider() {

        final List<Plateforme> plateformes = GestionnaireJeuxService.getToutesLesPlateformes();

        return new PaginationDataProvider<Plateforme>() {
            @Override
            public int getTotalRowCount() {
                return plateformes.size();
            }

            @Override
            public List<Plateforme> getRows(int startIndex, int endIndex) {
                return plateformes.subList(startIndex, endIndex);
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
