package calculator;

import static calculator.MyPrinter.printNumber;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class FieldAction implements ActionListener {
    
    MyPrinter mp;
    DecimalFormat myFormatter = new DecimalFormat("#,###");
    FieldAction(MyPrinter mp) {
    
        this.mp = mp;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    
        ++mp.gb.gridy;
        mp.gb.gridx = 1;
        final JFormattedTextField tempField = new JFormattedTextField(mp.format);
        mp.toExcelTempFieldJustForFocus = tempField;
        tempField.setColumns(15);
        tempField.addActionListener(new FieldAction(mp));
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
                try {
                    if (ks.length() > 0) {
                        String output = myFormatter.format(Long.parseLong(ks));
                        tempField.setText(output);
                    }
                } catch (Exception ex) {/*nothing happens*/}
                printNumber = mp.counter;
                mp.printNumberField.setText("" + printNumber);
            }
        });
        mp.arField.add(tempField);
        mp.printPanel.add(tempField, mp.gb);
        mp.gb.gridx = 0;
        --mp.gb.gridy;
        if (mp.sw2) {
            mp.printPanel.add(new JLabel(String.valueOf(mp.counter++) + ")   "), mp.gb);
        }
        ++mp.gb.gridy;
        mp.sw2 = true;
        mp.gb.gridx = 1;
        mp.mainPanel.updateUI();
        mp.mainPanel.validate();
        mp.mainPanel.repaint();
        
        mp.toExcelField.requestFocusInWindow();
    }
}