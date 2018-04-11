package calculator;

import export.ToExcel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MyPrinter extends JFrame {
    
    public static int printNumber;
    
    public JTextField toExcelTempFieldJustForFocus;

    final int MIN = 0;
    final int MAX = 100;
    final int FACTORF_INIT = 49;
    final int FACTORE_INIT = 51;
    
    public String tempSumForExcel = "";
    public Cal tempCalForExcel;
    
    ArrayList<Cal> arCal = new ArrayList<>();
    public ArrayList<String> arInputs = new ArrayList<>();
    ArrayList<JFormattedTextField> arField = new ArrayList<>();
    int x = 1;
    String rounded;
    String raw;
    String sumContent;
    String filePath;
    int counter = 2;
    boolean sw;
    int tempKey = 1;
    Format format = NumberFormat.getInstance();
    
    JSlider hekmatSlider = new JSlider(JSlider.HORIZONTAL,
            MIN, MAX, FACTORF_INIT);
    final JTextField hekmatPercentageField = new JTextField(String.valueOf("   " + FACTORF_INIT) + "%");
    JSlider viraSlider = new JSlider(JSlider.HORIZONTAL,
            MIN, MAX, FACTORE_INIT);
    final JTextField viraPercentageField = new JTextField(String.valueOf("   " + FACTORE_INIT) + "%");
    
    final JTextField factorAPercentageField = new JTextField(String.valueOf(Cal.factorAPercentage) + "%");
    final JTextField factorCPercentageField = new JTextField(String.valueOf(Cal.factorCPercentage) + "%");
    final JTextField factorDPercentageField = new JTextField(String.valueOf(Cal.factorDPercentage) + "%");
    final JTextField factorBPercentageField = new JTextField(String.valueOf(Cal.factorBPercentage) + "%");
    
    final JTextField printNumberField = new JTextField(String.valueOf(printNumber));
    
    JFormattedTextField mainField = new JFormattedTextField(format);
    JTextArea demoField = new JTextArea(15, 58);
    JButton printButton = new JButton("Print  ");
 
    JButton demoButton = new JButton("Show");
    JButton clearButton = new JButton("Clear");
    JButton addField = new JButton("+");
    JButton toExcelButton = new JButton("To Excel");
    JButton printExcelBtn = new JButton("Print Failed?");
    JTextField toExcelField = new JTextField(13);
    JPanel printPanel = new JPanel(new GridBagLayout());
    JPanel demoPanel = new JPanel();
    JPanel mainPanel = new JPanel(new BorderLayout());
    GridBagConstraints gb = new GridBagConstraints();
    DecimalFormat myFormatter = new DecimalFormat("#,###");
    boolean sw1 = true;
    boolean sw2 = false;

    public void setUI(final MyPrinter myPrinter) {
        
        factorCPercentageField.addKeyListener(new ColonyPercentKeyHandler());
        factorDPercentageField.addKeyListener(new BimePercentKeyHandler());
        factorBPercentageField.addKeyListener(new CreditPercentKeyHandler());
        factorAPercentageField.addKeyListener(new brandPercentKeyHandler());
        
        
        printNumberField.addKeyListener(new printFieldKeyHandler());
        
        printNumberField.setColumns(2);
       
        
        hekmatSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                JSlider source = (JSlider) e.getSource();
                int t = source.getValue();
                hekmatPercentageField.setText(String.valueOf(t) + "%");
                viraSlider.setValue(100 - t);
                Cal.factorFPercentage = t;
            }
        });
        
        viraSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                JSlider source = (JSlider) e.getSource();
                int t = source.getValue();
                viraPercentageField.setText(String.valueOf(t) + "%");            
                hekmatSlider.setValue(100 - t);
                Cal.factorEPercentage = t;
            }
        });
        
        mainField.setColumns(15);
        gb.gridx = 0;
        gb.gridy = 0;
        printPanel.add(new JLabel("Factor F"), gb);
        ++gb.gridx;
        printPanel.add(hekmatSlider, gb);
        ++gb.gridx;
        printPanel.add(hekmatPercentageField, gb);
        ++gb.gridx;
        printPanel.add(factorAPercentageField, gb);
        ++gb.gridx;
        printPanel.add(new JLabel("Factor A"), gb);
        --gb.gridx;
        --gb.gridx;
        ++gb.gridy;
        gb.gridx = 0;
        printPanel.add(new JLabel("Factor E"), gb);
        ++gb.gridx;
        printPanel.add(viraSlider, gb);
        ++gb.gridx;
        printPanel.add(viraPercentageField, gb);
        ++gb.gridx;
        printPanel.add(factorCPercentageField, gb);
        ++gb.gridx;
        printPanel.add(new JLabel("Factor C"), gb);
        ++gb.gridx;
        printPanel.add(factorDPercentageField, gb);
        ++gb.gridx;
        printPanel.add(new JLabel("Factor D"), gb);
        --gb.gridx;
        --gb.gridx;
        gb.gridy = 2;
        gb.gridx = 0;
        printPanel.add(new JLabel("1)   "), gb);
        ++gb.gridx;
        printPanel.add(mainField, gb);
        ++gb.gridx;
        printPanel.add(printButton, gb); 
        ++gb.gridx;
        printPanel.add(factorBPercentageField, gb); 
        ++gb.gridx;
        printPanel.add(new JLabel("Factor B"), gb);
        ++gb.gridy;
        printPanel.add(new JLabel("تعداد پرینت"), gb);
        --gb.gridx;
        printPanel.add(printNumberField, gb);
        --gb.gridx;      
        printPanel.add(demoButton, gb);
        ++gb.gridy;
        printPanel.add(clearButton, gb);
        ++gb.gridy;
        printPanel.add(addField, gb);
        ++gb.gridy;
        printPanel.add(toExcelButton, gb);
        ++gb.gridy;
        printPanel.add(toExcelField, gb);
        ++gb.gridy;
        printPanel.add(printExcelBtn, gb);
        gb.gridy = 2;
        demoPanel.add(demoField);
        demoField.setEditable(false);
        mainPanel.add(printPanel, "North");
        mainPanel.add(demoPanel, "South");
        add(mainPanel);
        setSize(670, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        printExcelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(filePath != null) {
                    printFile(new File(filePath));
                }else{
                    JOptionPane.showMessageDialog(null, "آدرس وارد نشده یا اکسل ساخته نشده");
                }
            }
        });
        
        demoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                arCal.clear();
                arInputs.clear();

                String temp;
                temp = mainField.getText();
                if (temp.equals("")) {
                    JOptionPane.showMessageDialog(null, "لطفا جای مربوطه را پر کنید");
                    return;
                }

                arInputs.add(temp);
                for (JFormattedTextField f : arField) {
                    temp = f.getText();
                    if (!temp.equals("")) {

                        arInputs.add(temp);
                    }
                }
                Cal cal = new Cal();
                
                StringBuilder sb = new StringBuilder();
                double tempSum = 0;
                int i = 1;
                for (String db : arInputs) {
                    double tempDb = Double.valueOf(db.replace(",", ""));
                    
                    sb.append(i++ + ") " + db + "     " + cal.singleFactorCalculate(tempDb) + "\n");
                    tempSum += tempDb;
                }

                cal.calculate(tempSum);
                cal.writeRaw();
                cal.writeRounded();
                sb.insert(0, "Total in Rial: "
                        + String.valueOf((int) tempSum * 10)
                                .replaceAll("(\\d)(?=(\\d{3})+$)", "$1,")
                        + "\nFactor A: " + Cal.factorAPercentage + "%           Factor B: "+Cal.factorBPercentage+"%         Factor C: "+Cal.factorCPercentage+"%        Factor D: " + Cal.factorDPercentage + "%         Factor E: " + Cal.factorEPercentage + "%           Factor F: " + Cal.factorFPercentage + "%"
                        + "\n-----------------------------------\n"
                        + cal.roundedContent + "\n" + cal.rawContent + "\n\n");
                

                demoField.setText(sb.toString());
            }
        });
        FieldAction fa = new FieldAction(myPrinter);
        mainField.addActionListener(fa);
        mainField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String ks = mainField.getText().replace(",", "");
                try {
                    if (ks.length() > 0) {
                        String output = myFormatter.format(Long.parseLong(ks));
                        mainField.setText(output);
                    }
                } catch (Exception ex) {/*nothing happens*/
                }
                printNumber = counter-1;
                printNumberField.setText("" + printNumber);
            }
        });
        mainField.requestFocusInWindow();
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                myPrinter.dispose();
                MyPrinter myPrinter = new MyPrinter();
                myPrinter.setUI(myPrinter);
                Cal.factorEPercentage = FACTORE_INIT;
                Cal.factorFPercentage = FACTORF_INIT;
                
            }
        });

        addField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ++gb.gridy;
                gb.gridx = 1;
                final JFormattedTextField tempField = new JFormattedTextField(format);
                tempField.setColumns(15);
                tempField.addActionListener(new FieldAction(myPrinter));
                tempField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        String ks = tempField.getText().replace(",", "");
                        if (ks.length() > 0) {
                            String output = myFormatter.format(Long.parseLong(ks));
                            tempField.setText(output);
                        }
                        printNumber = counter;
                        printNumberField.setText(""+printNumber);
                    }
                });
                arField.add(tempField);
                printPanel.add(tempField, gb);
                gb.gridx = 0;
                --gb.gridy;
                if (sw2) {
                    printPanel.add(new JLabel(String.valueOf(counter++) + ")   "), gb);
                }
                ++gb.gridy;
                sw2 = true;
                gb.gridx = 1;
                mainPanel.updateUI();
                mainPanel.validate();
                mainPanel.repaint();

                toExcelField.requestFocusInWindow();
            }
        });

        toExcelButton.addActionListener(new ToExcelClass(myPrinter));
        toExcelField.addActionListener(new ToExcelClass(myPrinter));
        toExcelField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                toExcelField.setToolTipText(toExcelField.getText());
                if("Space".equals(KeyEvent.getKeyText(e.getKeyCode()))) {
                    toExcelTempFieldJustForFocus.requestFocusInWindow();
                }
            }
        });
        toExcelButton.setToolTipText("نمونه tooltip");
    }


    /*--------------------------------------------innter class for excel export------------------------------------------------*/
    
    class ToExcelClass implements ActionListener {

        MyPrinter myPrinter;
        
        ToExcelClass(MyPrinter myPrinter) {
            this.myPrinter = myPrinter;
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser c = new JFileChooser("D:\\Dispatcher Report Excels Backup");
            
            ToExcel toExcel = new ToExcel();
            
            String fileName = "DID" + toExcelField.getText().replaceAll(" ", " DID");
            

            int counter = 2;
            int i = 0;
            while (i< c.getCurrentDirectory().listFiles().length) {
                if(fileName.equals(c.getCurrentDirectory().listFiles()[i].getName().replace(".xlsx", ""))) {
                    fileName = fileName.replaceAll("-.*", "");
                    fileName += "-" + counter++;
                    i = 0;
                }else {i++;}
            }
            
//            for (int i = 0; i < c.getCurrentDirectory().listFiles().length; i++) {
//                if (fileName.equals(c.getCurrentDirectory().listFiles()[i].getName().replace(".xlsx", ""))) {
//                    if (fileName.contains("-")) {
//                        int cn = Integer.valueOf(fileName.substring(fileName.lastIndexOf("-") + 1, fileName.length()));
//                        cn++;
//                        fileName = fileName.replaceAll("-.*", "");
//                        fileName += "-" + cn;
//                        i = 0;
//                    } else {
//                        fileName = fileName.replaceAll("-.*", "");
//                        fileName += "-2";
//                        i = 0;
//                    }
//                }
//            }
            
            File excelFile = new File(fileName + ".xlsx");
            c.setSelectedFile(excelFile);
            String filePath = "";
            int rVal = c.showSaveDialog(null);
            if (rVal == JFileChooser.APPROVE_OPTION) {
             
                filePath = c.getCurrentDirectory().getPath() + "\\" + c.getSelectedFile().getName();
                MyPrinter.this.filePath = filePath;
            }
            if (rVal == JFileChooser.CANCEL_OPTION) {
                return;
            }

            arCal.clear();
            arInputs.clear();

            String temp;
            temp = mainField.getText();
            if (temp.equals("")) {
                JOptionPane.showMessageDialog(null, "فیلدها خالی است");
                return;
            }

            arInputs.add(temp);
            for (JFormattedTextField f : arField) {
                temp = f.getText();
                if (!temp.equals("")) {

                    arInputs.add(temp);
                }
            }
            Cal cal = new Cal();

            double tempSum = 0;
            for (String db : arInputs) {
                double tempDb = Double.valueOf(db.replace(",", ""));

                tempSum += tempDb;
            }

            cal.calculate(tempSum);
            cal.writeRaw();
            cal.writeRounded();

            tempSumForExcel = String.valueOf((int) tempSum * 10).replaceAll("(\\d)(?=(\\d{3})+$)", "$1,");

            tempCalForExcel = cal;

            try {
                toExcel.init(myPrinter, filePath).close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /* ----------------------------this methid uses vb to read and print an excel file in windows environment-------------------------------------*/
    
    public void printFile(File file) {
        try {
            String vbs = "Dim AppExcel\r\n"
                    + "Set AppExcel = CreateObject(\"Excel.application\")\r\n"
                    + "AppExcel.Workbooks.Open(\"" + file.getPath() + "\")\r\n"
                    + "appExcel.ActiveWindow.SelectedSheets.PrintOut\r\n"
                    + "Appexcel.Quit\r\n"
                    + "Set appExcel = Nothing";
            File vbScript = File.createTempFile("vbScript", ".vbs");
            vbScript.deleteOnExit();
            FileWriter fw = new FileWriter(vbScript);
            fw.write(vbs);
            fw.close();
            for (int i = 0; i < printNumber; i++) {
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + vbScript.getPath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            Class.forName("export.ToExcel").newInstance();
            MyPrinter myPrinter = new MyPrinter();
            myPrinter.setUI(myPrinter);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "خطا در اجرای متد main ممکن است ورژن جاوا شما اشتباه باشد");
        } catch (Error er) {
            JOptionPane.showMessageDialog(null, "poi library missing!\ncreate a lib folder beside Cal.jar and put poi jar files inside it"
                    + " ye pushe besaz kenar file Cal be name lib va jar file haye poi ro add kon tush");
        }

    }
    
    class ColonyPercentKeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                Cal.factorCPercentage = Integer.valueOf(factorCPercentageField.getText().replace("%", ""));
            } catch (Exception ex) {/*nothing happens*/}
            mainPanel.updateUI();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }
    class BimePercentKeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                Cal.factorDPercentage = Integer.valueOf(factorDPercentageField.getText().replace("%", ""));
            } catch (Exception ex) {/*nothing happens*/

            }
            mainPanel.updateUI();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }
    class brandPercentKeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                Cal.factorAPercentage = Integer.valueOf(factorAPercentageField.getText().replace("%", ""));
            } catch (Exception ex) {/*nothing happens*/

            }
            mainPanel.updateUI();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }
    class CreditPercentKeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                Cal.factorBPercentage = Integer.valueOf(factorBPercentageField.getText().replace("%", ""));
            } catch (Exception ex) {/*nothing happens*/

            }
            mainPanel.updateUI();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }
    
    class printFieldKeyHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            printNumber = Integer.valueOf(printNumberField.getText());
            mainPanel.updateUI();
            mainPanel.validate();
            mainPanel.repaint();
        }
    }
}

