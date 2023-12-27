package swing;

import interfaces.CurrencyDialog;
import interfaces.MoneyDialog;
import model.Currency;
import model.Money;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SwingMoneyDialog extends JPanel implements MoneyDialog {

    private final JTextField amount;
    private CurrencyDialog currencyDialog;

    public SwingMoneyDialog() {
        this.setLayout(new FlowLayout());
        this.add(new JLabel("Amount:"));
        this.amount = createAmountField();
        this.add(amount);
    }

    private JTextField createAmountField() {
        JTextField result = new JTextField();
        result.setColumns(8);
        return result;
    }

    private Component createCurrencyDialog(List<Currency> currencies) {
        SwingCurrencyDialog dialog = new SwingCurrencyDialog();
        dialog.define(currencies);
        this.currencyDialog = dialog;
        return dialog;
    }
    @Override
    public MoneyDialog define(List<Currency> currencies) {
        add(createCurrencyDialog(currencies));
        return this;
    }

    @Override
    public Money get() {
        try {

            double moneyAmount = Double.parseDouble(amount.getText());
            return new Money(moneyAmount, currencyDialog.get());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, introduce un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
