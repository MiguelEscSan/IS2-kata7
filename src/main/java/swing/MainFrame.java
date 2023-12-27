package swing;

import control.Command;
import interfaces.CurrencyDialog;
import interfaces.MoneyDialog;
import interfaces.MoneyDisplay;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {

    private MoneyDisplay moneyDisplay;
    private MoneyDialog moneyDialog;
    private CurrencyDialog currencyDialog;
    private Map<String, Command> commands = new HashMap<>();


    public MainFrame() throws HeadlessException {
        setTitle("Money Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Utilizar BoxLayout en orientación vertical
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Panel para contener los componentes relacionados con la moneda
        JPanel currencyPanel = new JPanel();
        currencyPanel.setLayout(new BoxLayout(currencyPanel, BoxLayout.X_AXIS));
        currencyPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Ajustar los márgenes
        currencyPanel.add(new JLabel("From:"));
        currencyPanel.add(createMoneyDialog());

        // Panel para contener los componentes relacionados con la moneda de destino
        JPanel targetCurrencyPanel = new JPanel();
        targetCurrencyPanel.setLayout(new BoxLayout(targetCurrencyPanel, BoxLayout.X_AXIS));
        targetCurrencyPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Ajustar los márgenes
        targetCurrencyPanel.add(new JLabel("To:"));
        targetCurrencyPanel.add(createCurrencyDialog());

        // Panel para contener el botón de cambio
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5)); // Ajustar los márgenes
        buttonPanel.add(createChangeButton());

        // Panel para contener el resultado
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(new EmptyBorder(5, 5, 20, 5)); // Ajustar los márgenes
        resultPanel.add(createMoneyDisplay());

        // Agregar los paneles al contenedor principal en orientación vertical
        add(currencyPanel);
        add(targetCurrencyPanel);
        add(buttonPanel);
        add(resultPanel);
    }



        private Component createChangeButton() {
        JButton button = new JButton("Change");
        button.addActionListener(e -> {
            try {
                commands.get("change").execute();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private Component createCurrencyDialog() {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        currencyDialog = dialog;
        return dialog;
    }

    private Component createMoneyDisplay() {
        SwingMoneyDisplay display = new SwingMoneyDisplay();
        moneyDisplay = display;
        return display;
    }

    private Component createMoneyDialog() {
        SwingMoneyDialog dialog = new SwingMoneyDialog();
        moneyDialog = dialog;
        return dialog;
    }

    public MoneyDisplay getMoneyDisplay() {
        return moneyDisplay;
    }

    public MoneyDialog getMoneyDialog() {
        return moneyDialog;
    }

    public CurrencyDialog getCurrencyDialog() {
        return currencyDialog;
    }

    public void add(String name, Command command) {
        commands.put(name, command);
    }
}
