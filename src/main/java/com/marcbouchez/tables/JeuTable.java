package com.marcbouchez.tables;

import com.marcbouchez.entities.Jeu;
import com.marcbouchez.entities.Plateforme;
import com.marcbouchez.interfaces.PaginationDataProvider;
import com.marcbouchez.pagination.PaginatedTableDecorator;
import com.marcbouchez.services.GestionnaireJeuxService;
import com.marcbouchez.utils.ObjectTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JeuTable extends JPanel {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    TableModel tableModel;
    private JTable table;
    private JPanel rootPane = new JPanel();
    private JPanel tablePane = new JPanel();
    private JPanel fieldsPane = new JPanel();
    private JPanel menuPane = new JPanel();

    private PaginationDataProvider<Jeu> dataProvider;
    private PaginatedTableDecorator<Jeu> paginatedDecorator;

    //Fields and buttons
    private JTextField idTF = new JTextField("AUTO");
    private JTextField nameTF = new JTextField("Zelda");
    private JTextField dateTF = new JTextField("12-12-2020");

    private JComboBox plateformeCB = new JComboBox();

    private JButton categorieBtn = new JButton("Catégorie(s)");
    private JButton addBtn = new JButton("Ajouter");

    public JeuTable() {
        super();

        tableModel = createObjectDataModel();
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        dataProvider = createDataProvider();
        paginatedDecorator = PaginatedTableDecorator.decorate(table, dataProvider, new int[]{15, 30, 50, 75, 100}, 15);

        //FIELDS
        int inputHeight = 25;
        idTF.setEnabled(false);
        idTF.setPreferredSize(new Dimension(50, inputHeight));
        nameTF.setPreferredSize(new Dimension(250, inputHeight));
        dateTF.setPreferredSize(new Dimension(100, inputHeight));
        plateformeCB.setPreferredSize(new Dimension(150, inputHeight));
        categorieBtn.setPreferredSize(new Dimension(100, inputHeight));
        addBtn.setPreferredSize(new Dimension(75, inputHeight));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(new Color(60, 125, 200));

        for (Plateforme p : GestionnaireJeuxService.getToutesLesPlateformes()) {
            plateformeCB.addItem(p);
        }

        //TABLE DESIGN {Alignment = center, AutoSize off, resize}
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(225);
        table.getColumnModel().getColumn(5).setPreferredWidth(75);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //LISTENER
        table.getSelectionModel().addListSelectionListener(e -> selectionListener(e));

        addBtn.addActionListener(e -> addBtnListener(e));


        tablePane.add(paginatedDecorator.getContentPanel());
        tablePane.setLayout(new GridLayout(1, 1));

        menuPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPane.add(idTF);
        menuPane.add(nameTF);
        menuPane.add(dateTF);
        menuPane.add(plateformeCB);
        menuPane.add(categorieBtn);
        menuPane.add(Box.createHorizontalStrut(20));
        menuPane.add(addBtn);

        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.PAGE_AXIS));
        rootPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        rootPane.add(tablePane);
        rootPane.add(menuPane);


        JPopupMenu popupMenu = this.createPopupMenu();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });
    }

    private static TableModel createObjectDataModel() {
        return new ObjectTableModel<Jeu>() {
            @Override
            public Object getValueAt(Jeu j, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return j.getId();
                    case 1:
                        return j.getName();
                    case 2:
                        return j.getDate().format(dateFormat);
                    case 3:
                        return j.getPlateforme().getName();
                    case 4:
                        return "Catégorie";
                    case 5:
                        return j.getPrix();
                }
                return null;
            }

            @Override
            public int getColumnCount() {
                return 6;
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
                    case 3:
                        return "Plateforme";
                    case 4:
                        return "Catégorie(s)";
                    case 5:
                        return "Prix";
                }
                return null;
            }
        };
    }

    public JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem mnuUndo = new JMenuItem("Edit...");
        mnuUndo.setMnemonic('U');
        mnuUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK));
        mnuUndo.addActionListener(this::menuEditListener);
        popupMenu.add(mnuUndo);

        return popupMenu;
    }

    public void menuEditListener(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Edit!");
    }

    public JPanel getTablePane() {
        return rootPane;
    }

    private PaginationDataProvider<Jeu> createDataProvider() {

        final java.util.List<Jeu> jeux = GestionnaireJeuxService.getTousLesJeux();

        return new PaginationDataProvider<Jeu>() {
            @Override
            public int getTotalRowCount() {
                return jeux.size();
            }

            @Override
            public List<Jeu> getRows(int startIndex, int endIndex) {
                return jeux.subList(startIndex, endIndex);
            }
        };
    }

    private void selectionListener(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {

        }
    }

    private void addBtnListener(ActionEvent e) {
        System.out.println("Add btn fired");
        String nom = nameTF.getText();
        String date = dateTF.getText();
        Plateforme plateforme = (Plateforme) plateformeCB.getSelectedItem();

        if (!nom.equals("") && !date.equals("")) {
            String regex = "([0-3][0-9])-(0[1-9]|1[0-2])-([0-2][0-9][0-9][0-9])";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches()) {
                String dateParts[] = date.split("-");
                int year = Integer.parseInt(dateParts[2]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[0]);

                Jeu j = new Jeu(nom, LocalDate.of(year, month, day), plateforme, 0.0F);
                GestionnaireJeuxService.createJeu(j);

                String successMessage = nom + " " + date + " " + plateforme + "\n Jeu correctement ajouté !";
                JOptionPane.showMessageDialog(JeuTable.this, successMessage);
                reload(createDataProvider());
            } else {
                JOptionPane.showMessageDialog(JeuTable.this, "Veuillez entrer une date valide (JJ-MM-AAAA)");
            }
        } else {
            JOptionPane.showMessageDialog(JeuTable.this, "Veuillez remplir correctement les champs");
        }
    }

    private void reload(PaginationDataProvider<Jeu> dataProvider) {
        paginatedDecorator = PaginatedTableDecorator.decorate(table, dataProvider, new int[]{15, 30, 50, 75, 100}, 15);
        tablePane.removeAll();
        tablePane.add(paginatedDecorator.getContentPanel());
        tablePane.updateUI();
    }
}
