package interfaces;

import model.Currency;

import java.util.List;

public interface CurrencyDialog {

    CurrencyDialog define(List<Currency> currencyList);
    Currency get();
}
