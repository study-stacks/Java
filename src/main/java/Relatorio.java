import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class Relatorio extends JPanel {

    JTable tabela;
    DefaultTableModel modelo;
    JComboBox<String> boxFiltro;
    JButton btnGerar, btnExportar, btnImprimir;

    private Vector<Vector<Object>> dadosExemplo = new Vector<>();
    {
        Vector<Object> row1 = new Vector<>();
        row1.add(1); row1.add("Projeto A"); row1.add("João"); row1.add("Pendente"); row1.add("2023-10-01");
        dadosExemplo.add(row1);

        Vector<Object> row2 = new Vector<>();
        row2.add(2); row2.add("Projeto B"); row2.add("Maria"); row2.add("Em execução"); row2.add("2023-10-05");
        dadosExemplo.add(row2);

        Vector<Object> row3 = new Vector<>();
        row3.add(3); row3.add("Projeto C"); row3.add("Pedro"); row3.add("Concluída"); row3.add("2023-10-10");
        dadosExemplo.add(row3);

        Vector<Object> row4 = new Vector<>();
        row4.add(4); row4.add("Projeto D"); row4.add("Ana"); row4.add("Pendente"); row4.add("2023-10-15");
        dadosExemplo.add(row4);
    }

    public Relatorio() {
        setLayout(null);

        String[] colunas = {"ID", "Nome Projeto", "Responsável", "Status", "Data"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(10, 10, 500, 360);
        add(rolagem);

        JLabel l1 = new JLabel("Filtrar por Status:");
        l1.setBounds(520, 10, 120, 25);
        add(l1);

        String[] filtros = {"Todos", "Pendente", "Em execução", "Concluída"};
        boxFiltro = new JComboBox<>(filtros);
        boxFiltro.setBounds(640, 10, 120, 25);
        add(boxFiltro);

        boxFiltro.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    aplicarFiltro();
                }
            }
        });
        btnGerar = new JButton("Gerar Relatório");
        btnGerar.setBounds(520, 50, 120, 25);
        add(btnGerar);

        btnExportar = new JButton("Exportar");
        btnExportar.setBounds(650, 50, 110, 25);
        add(btnExportar);

        btnImprimir = new JButton("Imprimir");
        btnImprimir.setBounds(520, 80, 120, 25);
        add(btnImprimir);

        btnGerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio();
            }
        });
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaCSV();
            }
        });

        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imprimirRelatorio();
            }
        });
    }
    private void gerarRelatorio() {
        modelo.setRowCount(0);
        for (Vector<Object> row : dadosExemplo) {
            modelo.addRow(row);
        }
        aplicarFiltro();
        JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso!");
    }
    private void aplicarFiltro() {
        String filtroSelecionado = (String) boxFiltro.getSelectedItem();
        modelo.setRowCount(0);

        for (Vector<Object> row : dadosExemplo) {
            String status = (String) row.get(3);
            if (filtroSelecionado.equals("Todos") || filtroSelecionado.equals(status)) {
                modelo.addRow(row);
            }
        }
    }
    private void exportarParaCSV() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum dado para exportar. Gere o relatório primeiro!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar relatório como CSV");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".csv")) {
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    writer.append(modelo.getColumnName(i));
                    if (i < modelo.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    for (int j = 0; j < modelo.getColumnCount(); j++) {
                        writer.append(modelo.getValueAt(i, j).toString());
                        if (j < modelo.getColumnCount() - 1) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }
                JOptionPane.showMessageDialog(this, "Relatório exportado com sucesso para CSV!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
            }
        }
    }
    private void imprimirRelatorio() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Nenhum dado para imprimir. Gere o relatório primeiro!");
            return;
        }

        try {
            boolean complete = tabela.print(JTable.PrintMode.FIT_WIDTH, null, null);
            if (complete) {
                JOptionPane.showMessageDialog(this, "Impressão concluída com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Impressão cancelada pelo usuário.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage() +
                    "\nVerifique se a impressora está configurada corretamente.");
        }
    }
}