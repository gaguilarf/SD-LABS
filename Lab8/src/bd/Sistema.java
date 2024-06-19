package bd;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Sistema extends JFrame {

    private EmpresaDAO empresaDAO;

    private JPanel mainPanel;
    private JComboBox<String> tableSelector;
    private JPanel formPanel;
    private CardLayout cardLayout;

    private DefaultTableModel tableModel;
    private JTable dataTable;

    private JComboBox<String> departamentoSelector;
    private JComboBox<String> proyectoSelector;

    public Sistema() {
        try {
            empresaDAO = new EmpresaDAO();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setTitle("Sistema de Gestión de Empresa");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());

        String[] tables = {"Departamento", "Proyecto", "Ingeniero", "Proyecto_Ingeniero"};
        tableSelector = new JComboBox<>(tables);
        tableSelector.addActionListener(new TableSelectorActionListener());

        mainPanel.add(tableSelector, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        formPanel = new JPanel(cardLayout);

        formPanel.add(createDepartamentoPanel(), "Departamento");
        formPanel.add(createProyectoPanel(), "Proyecto");
        formPanel.add(createIngenieroPanel(), "Ingeniero");
        formPanel.add(createProyectoIngenieroPanel(), "Proyecto_Ingeniero");

        mainPanel.add(formPanel, BorderLayout.WEST);

        tableModel = new DefaultTableModel();
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel queryPanel = new JPanel(new GridLayout(2, 3));
        departamentoSelector = new JComboBox<>();
        proyectoSelector = new JComboBox<>();
        JButton viewProyectosButton = new JButton("Ver Proyectos");
        JButton viewIngenierosButton = new JButton("Ver Ingenieros");

        viewProyectosButton.addActionListener(e -> viewProyectos());
        viewIngenierosButton.addActionListener(e -> viewIngenieros());

        queryPanel.add(new JLabel("Departamento:"));
        queryPanel.add(departamentoSelector);
        queryPanel.add(viewProyectosButton);
        queryPanel.add(new JLabel("Proyecto:"));
        queryPanel.add(proyectoSelector);
        queryPanel.add(viewIngenierosButton);

        mainPanel.add(queryPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(e -> deleteSelectedRow());
        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> editSelectedRow());

        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        loadComboBoxData();
    }

    private void loadComboBoxData() {
        try {
            departamentoSelector.removeAllItems();
            proyectoSelector.removeAllItems();
            List<Object[]> departamentos = empresaDAO.obtenerTodosLosDepartamentos();
            for (Object[] departamento : departamentos) {
                departamentoSelector.addItem(departamento[0] + " - " + departamento[1]);
            }
            List<Object[]> proyectos = empresaDAO.obtenerTodosLosProyectos();
            for (Object[] proyecto : proyectos) {
                proyectoSelector.addItem(proyecto[0] + " - " + proyecto[1]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewProyectos() {
        if (departamentoSelector.getSelectedIndex() != -1) {
            String selectedItem = (String) departamentoSelector.getSelectedItem();
            int idDpto = Integer.parseInt(selectedItem.split(" - ")[0]);
            try {
                List<Object[]> data = empresaDAO.obtenerProyectosPorDepartamento(idDpto);
                tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID Proyecto", "Nombre Proyecto", "Fecha Inicio", "Fecha Término", "ID Departamento", "Nombre Departamento"});
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void viewIngenieros() {
        if (proyectoSelector.getSelectedIndex() != -1) {
            String selectedItem = (String) proyectoSelector.getSelectedItem();
            int idProy = Integer.parseInt(selectedItem.split(" - ")[0]);
            try {
                List<Object[]> data = empresaDAO.obtenerIngenierosPorProyecto(idProy);
                tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID Ingeniero", "Nombre Ingeniero", "Especialidad", "Cargo", "ID Proyecto", "Nombre Proyecto"});
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel createDepartamentoPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("ID Departamento:");
        JTextField idField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel telefonoLabel = new JLabel("Teléfono:");
        JTextField telefonoField = new JTextField();
        JLabel faxLabel = new JLabel("Fax:");
        JTextField faxField = new JTextField();

        JButton insertButton = new JButton("Insertar");
        insertButton.addActionListener(e -> {
            try {
                empresaDAO.insertarDepartamento(
                        Integer.parseInt(idField.getText()),
                        nombreField.getText(),
                        telefonoField.getText(),
                        faxField.getText()
                );
                JOptionPane.showMessageDialog(this, "Departamento insertado correctamente.");
                updateTableData("Departamento");
                loadComboBoxData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(telefonoLabel);
        panel.add(telefonoField);
        panel.add(faxLabel);
        panel.add(faxField);
        panel.add(new JLabel());
        panel.add(insertButton);

        return panel;
    }

    private JPanel createProyectoPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        JLabel idLabel = new JLabel("ID Proyecto:");
        JTextField idField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel fechaInicioLabel = new JLabel("Fecha Inicio:");
        JTextField fechaInicioField = new JTextField();
        JLabel fechaTerminoLabel = new JLabel("Fecha Término:");
        JTextField fechaTerminoField = new JTextField();
        JLabel idDptoLabel = new JLabel("ID Departamento:");
        JTextField idDptoField = new JTextField();

        JButton insertButton = new JButton("Insertar");
        insertButton.addActionListener(e -> {
            try {
                empresaDAO.insertarProyecto(
                        Integer.parseInt(idField.getText()),
                        nombreField.getText(),
                        Date.valueOf(fechaInicioField.getText()),
                        Date.valueOf(fechaTerminoField.getText()),
                        Integer.parseInt(idDptoField.getText())
                );
                JOptionPane.showMessageDialog(this, "Proyecto insertado correctamente.");
                updateTableData("Proyecto");
                loadComboBoxData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(fechaInicioLabel);
        panel.add(fechaInicioField);
        panel.add(fechaTerminoLabel);
        panel.add(fechaTerminoField);
        panel.add(idDptoLabel);
        panel.add(idDptoField);
        panel.add(new JLabel());
        panel.add(insertButton);

        return panel;
    }

    private JPanel createIngenieroPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("ID Ingeniero:");
        JTextField idField = new JTextField();
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreField = new JTextField();
        JLabel especialidadLabel = new JLabel("Especialidad:");
        JTextField especialidadField = new JTextField();
        JLabel cargoLabel = new JLabel("Cargo:");
        JTextField cargoField = new JTextField();

        JButton insertButton = new JButton("Insertar");
        insertButton.addActionListener(e -> {
            try {
                empresaDAO.insertarIngeniero(
                        Integer.parseInt(idField.getText()),
                        nombreField.getText(),
                        especialidadField.getText(),
                        cargoField.getText()
                );
                JOptionPane.showMessageDialog(this, "Ingeniero insertado correctamente.");
                updateTableData("Ingeniero");
                loadComboBoxData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(especialidadLabel);
        panel.add(especialidadField);
        panel.add(cargoLabel);
        panel.add(cargoField);
        panel.add(new JLabel());
        panel.add(insertButton);

        return panel;
    }

    private JPanel createProyectoIngenieroPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel idProyLabel = new JLabel("ID Proyecto:");
        JTextField idProyField = new JTextField();
        JLabel idIngLabel = new JLabel("ID Ingeniero:");
        JTextField idIngField = new JTextField();

        JButton insertButton = new JButton("Insertar");
        insertButton.addActionListener(e -> {
            try {
                empresaDAO.insertarIngenieroEnProyecto(
                        Integer.parseInt(idProyField.getText()),
                        Integer.parseInt(idIngField.getText())
                );
                JOptionPane.showMessageDialog(this, "Ingeniero insertado en Proyecto correctamente.");
                updateTableData("Proyecto_Ingeniero");
                loadComboBoxData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(idProyLabel);
        panel.add(idProyField);
        panel.add(idIngLabel);
        panel.add(idIngField);
        panel.add(new JLabel());
        panel.add(insertButton);

        return panel;
    }

    private void updateTableData(String table) {
        try {
            List<Object[]> data;
            switch (table) {
                case "Departamento":
                    data = empresaDAO.obtenerTodosLosDepartamentos();
                    tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID", "Nombre", "Teléfono", "Fax"});
                    break;
                case "Proyecto":
                    data = empresaDAO.obtenerTodosLosProyectos();
                    tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID", "Nombre", "Fecha Inicio", "Fecha Término", "ID Departamento"});
                    break;
                case "Ingeniero":
                    data = empresaDAO.obtenerTodosLosIngenieros();
                    tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID", "Nombre", "Especialidad", "Cargo"});
                    break;
                case "Proyecto_Ingeniero":
                    data = empresaDAO.obtenerTodosLosProyectosIngenieros();
                    tableModel.setDataVector(data.toArray(new Object[0][]), new Object[]{"ID Proyecto", "ID Ingeniero"});
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String selectedTable = (String) tableSelector.getSelectedItem();
                switch (selectedTable) {
                    case "Departamento":
                        int idDpto = (int) tableModel.getValueAt(selectedRow, 0);
                        empresaDAO.eliminarDepartamento(idDpto);
                        break;
                    case "Proyecto":
                        int idProy = (int) tableModel.getValueAt(selectedRow, 0);
                        empresaDAO.eliminarProyecto(idProy);
                        break;
                    case "Ingeniero":
                        int idIng = (int) tableModel.getValueAt(selectedRow, 0);
                        empresaDAO.eliminarIngeniero(idIng);
                        break;
                    case "Proyecto_Ingeniero":
                        int idProyIng = (int) tableModel.getValueAt(selectedRow, 0);
                        int idIngProy = (int) tableModel.getValueAt(selectedRow, 1);
                        empresaDAO.eliminarIngenieroDeProyecto(idProyIng, idIngProy);
                        break;
                }
                updateTableData(selectedTable);
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                loadComboBoxData();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.");
        }
    }

    private void editSelectedRow() {
        int selectedRow = dataTable.getSelectedRow();
        if (selectedRow != -1) {
            String selectedTable = (String) tableSelector.getSelectedItem();
            switch (selectedTable) {
                case "Departamento":
                    editDepartamento(selectedRow);
                    break;
                case "Proyecto":
                    editProyecto(selectedRow);
                    break;
                case "Ingeniero":
                    editIngeniero(selectedRow);
                    break;
                case "Proyecto_Ingeniero":
                    // No hay edición para Proyecto_Ingeniero
                    JOptionPane.showMessageDialog(this, "La edición no está disponible para esta tabla.");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para editar.");
        }
    }

    private void editDepartamento(int selectedRow) {
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        String telefono = (String) tableModel.getValueAt(selectedRow, 2);
        String fax = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField nombreField = new JTextField(nombre);
        JTextField telefonoField = new JTextField(telefono);
        JTextField faxField = new JTextField(fax);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Fax:"));
        panel.add(faxField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Departamento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                empresaDAO.actualizarDepartamento(id, nombreField.getText(), telefonoField.getText(), faxField.getText());
                updateTableData("Departamento");
                JOptionPane.showMessageDialog(this, "Departamento actualizado correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editProyecto(int selectedRow) {
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        Date fechaInicio = (Date) tableModel.getValueAt(selectedRow, 2);
        Date fechaTermino = (Date) tableModel.getValueAt(selectedRow, 3);
        int idDpto = (int) tableModel.getValueAt(selectedRow, 4);

        JTextField nombreField = new JTextField(nombre);
        JTextField fechaInicioField = new JTextField(fechaInicio.toString());
        JTextField fechaTerminoField = new JTextField(fechaTermino.toString());
        JTextField idDptoField = new JTextField(String.valueOf(idDpto));

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Fecha Inicio:"));
        panel.add(fechaInicioField);
        panel.add(new JLabel("Fecha Término:"));
        panel.add(fechaTerminoField);
        panel.add(new JLabel("ID Departamento:"));
        panel.add(idDptoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Proyecto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                empresaDAO.actualizarProyecto(id, nombreField.getText(), Date.valueOf(fechaInicioField.getText()), Date.valueOf(fechaTerminoField.getText()), Integer.parseInt(idDptoField.getText()));
                updateTableData("Proyecto");
                JOptionPane.showMessageDialog(this, "Proyecto actualizado correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editIngeniero(int selectedRow) {
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String nombre = (String) tableModel.getValueAt(selectedRow, 1);
        String especialidad = (String) tableModel.getValueAt(selectedRow, 2);
        String cargo = (String) tableModel.getValueAt(selectedRow, 3);

        JTextField nombreField = new JTextField(nombre);
        JTextField especialidadField = new JTextField(especialidad);
        JTextField cargoField = new JTextField(cargo);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Especialidad:"));
        panel.add(especialidadField);
        panel.add(new JLabel("Cargo:"));
        panel.add(cargoField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Ingeniero", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                empresaDAO.actualizarIngeniero(id, nombreField.getText(), especialidadField.getText(), cargoField.getText());
                updateTableData("Ingeniero");
                JOptionPane.showMessageDialog(this, "Ingeniero actualizado correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private class TableSelectorActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedTable = (String) tableSelector.getSelectedItem();
            cardLayout.show(formPanel, selectedTable);
            updateTableData(selectedTable);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            sistema.setVisible(true);
        });
    }
}
