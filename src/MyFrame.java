
import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class TextEditor extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLable;
    JSpinner fontSizeSpiner;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Text something");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN,20));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLable = new JLabel("Font");

        fontSizeSpiner = new JSpinner();
        fontSizeSpiner.setPreferredSize(new Dimension(50,25));
        fontSizeSpiner.setValue(20);
        fontSizeSpiner.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpiner.getValue()));

            }
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        menuBar = new JMenuBar();
        fileMenu = new JMenu("file");
        openItem = new JMenuItem("open");
        saveItem = new JMenuItem("save");
        exitItem = new JMenuItem("exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);


        this.setJMenuBar(menuBar);
        this.add(fontLable);
        this.add(fontSizeSpiner);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }





    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==fontColorButton){
            JColorChooser colorChooser = new JColorChooser();

            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);

        }
        if (e.getSource()==fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if (e.getSource()==openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("text file", "txt");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION);
            {

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner filein = null;

                try {
                    filein = new Scanner(file);
                    if (file.isFile()) {
                        while (filein.hasNextLine()) {
                            String line = filein.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    filein.close();
                }
            }

        }
        if (e.getSource()==saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());

                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                finally {
                    fileOut.close();
                }
            }

        }
        if (e.getSource()==exitItem){
            System.exit(0);

        }

    }
}

