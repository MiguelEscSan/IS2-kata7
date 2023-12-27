package swing;

import interfaces.CurrencyDialog;
import model.Currency;

import javax.swing.*;
import java.util.List;

public class SwingCurrencyDialog extends JPanel implements CurrencyDialog {
    private List<Currency> currencies;
    private JComboBox<Currency> currencyJComboBox;

    @Override
    public CurrencyDialog define(List<Currency> currencyList) {
        this.currencies = currencyList;
        add(currencyJComboBox = currencySelector(currencyList));
        return this;
    }

    public JComboBox<Currency> currencySelector(List<Currency> currencies) {
        JComboBox<Currency> result = new JComboBox<>();
        for(Currency currency : currencies) {
            result.addItem(currency);
        }
        return result;
    }

    @Override
    public Currency get() {
        return (Currency) currencyJComboBox.getSelectedItem();

    }
}
