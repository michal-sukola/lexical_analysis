/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.lexicalAnalysis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author User
 */
public class ApplicationForm {
    
    public static final String APP_LABEL = "Lexical Analysis";

    public ApplicationForm() {
        JFrame frame = new JFrame(APP_LABEL);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.add(panel);
        
        // MENU
        
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Program");
        menu.setMnemonic(KeyEvent.VK_P);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Uložiť",
                                 KeyEvent.VK_U);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Koniec",
                                 KeyEvent.VK_K);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);


        menuItem = new JMenuItem("Both text and icon",
                                 new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

        menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
        menuItem.setMnemonic(KeyEvent.VK_D);
        menu.add(menuItem);

        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

        //a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

        //Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuItem = new JMenuItem("Another item in 2nd menu");
        menu.add(menuItem);
        //menuBar.add(menu);
        
        // END MENU
        
        // Taby
        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("images/middle.gif");

        JComponent panel1 = makeTablePanel();        
        
        tabbedPane.addTab("Reťazce", icon, panel1,
                          "Práca z reťazcami a priraďovanie tagov");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = new JPanel(); //makeTextPanel("Panel #2");
        panel2.setToolTipText("Panel 2 tooltip text");
        panel2.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Panel 2 group"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));
        JMenuBar mb = new JMenuBar();
        mb.add(menu);
        panel2.setLayout(new BorderLayout());
        panel2.add(mb, BorderLayout.NORTH);
        tabbedPane.addTab("Tagy", icon, panel2,
                          "Definícia tagov");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Sumár reťazcov", icon, panel3,
                          "Zobrazuje reťazce podľa roznych tagov");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        JComponent panel4 = makeTextPanel("Panel #4");
        tabbedPane.addTab("Dokumenty", icon, panel4 ,
                          "Zobrazuje dokumenty, umožňuje nahrať nový");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

       
        frame.add(tabbedPane);
        
        frame.setJMenuBar(menuBar);
        
        frame.setSize(1024, 768);
        frame.setVisible(true);
    }
    
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
    
    class ChoiceTableCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object items, boolean isSelected, boolean hasFocus, int row, int column) {
            Vector<String> choices = (Vector<String>) items;
            JComboBox cb = new JComboBox(choices);
            cb.setSelectedItem(choices.get(0));
            
            JLabel label = new JLabel("labelLabel");
            
//            return cb;
            return label;
        }
        
    }
    
    class WordStringTableModel extends AbstractTableModel {
        private Vector<String> columns = new Vector<String>();
        private Vector<Vector<Object> > data = new Vector<Vector <Object>>();
            
        {
            /*columns.add("Reťazec");
            columns.add("Kontext");*/
            Vector<Object> row = new Vector<>();
            /*row.add("Bla");
            row.add("toto je Bla a toto je Bli");*/
            data.add(row);
        }
            
        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return columns.size();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex).get(columnIndex);
        }

        @Override
        public String getColumnName(int column) {
            return columns.get(column);
        }
        
        public void addColumnBefore(String colName, int colIndex) {
            columns.add(colIndex, colName);
            for(int i=0; i<data.size(); i++) {
                Vector<Object> row = data.get(i);
                if(colName == "Tag: Slovny druh" ) {
                    Vector<String> choices = new Vector<>();
                    choices.add("Prislovka");
                    choices.add("Sloveso");
                    choices.add("Podstatne meno");
                    
                    System.out.println(row.getClass());
                    
                    row.add(colIndex, choices);    
                } else {
                    row.add(colIndex, "Test " + colIndex);    
                }                
            }
            
            fireTableStructureChanged();
        }
            
        public void addColumn(String colName) {
            addColumnBefore(colName, columns.size());
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columns.get(columnIndex).getClass();
        }
            
        
            
        
    }
    
    protected JComponent makeTablePanel() {
        JPanel panel = new JPanel(false);
        
        JPanel filterPanel = new JPanel(false);
        
        //filterPanel.setBorder(BorderFactory.createCompoundBorder(
        //                BorderFactory.createTitledBorder("Documents to display"),
        //               BorderFactory.createEmptyBorder(5,5,5,5)));
        JLabel docsFilLabel = new JLabel("Dokumenty: ");
        filterPanel.add(docsFilLabel);
        
        String[] choices = { "Document1", "Document2", "Document3" };
        for (String choice : choices) {
            JCheckBox  chB = new JCheckBox(choice);
            filterPanel.add(chB);
        }
        
        
        
        JLabel filler = new JLabel("Tabuľka");
        filler.setHorizontalAlignment(JLabel.CENTER);
        
        
        //panel.setLayout(new GridLayout(3, 1));
        panel.setLayout(new BorderLayout());
        //panel.add(filler, BorderLayout.PAGE_START);
        panel.add(filterPanel, BorderLayout.PAGE_START);
        
        WordStringTableModel tableModel = new WordStringTableModel();        
        
        JTable table = new JTable(tableModel);
                       
        table.setDefaultRenderer(Vector.class, new ChoiceTableCellRenderer());
        System.out.println(table.getColumnModel());   
        
        JPanel tablePanel = new JPanel(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        tablePanel.add(table, BorderLayout.CENTER);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        
        tableModel.addColumnBefore("Reťazec", 0);
        tableModel.addColumnBefore("Číslo riadka", 1);
        tableModel.addColumnBefore("Tag: Slovny druh", 2);
        tableModel.addColumnBefore("Kontext", 3);
        return panel;
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ApplicationForm.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    
    public static void main(String[] args) {
        new ApplicationForm();
    }
    
    
    
}
