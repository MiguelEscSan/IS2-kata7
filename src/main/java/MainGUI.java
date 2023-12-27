import control.ExchangeRatesCommand;
import interfaces.CoinsLoader;
import interfaces.CurrencyDialog;
import interfaces.ExchangeRatesLoader;
import interfaces.MoneyDialog;
import interfaces.MoneyDisplay;
import mock.JsonExchangeRatesLoader;
import mock.TsvFileCoinsLoader;
import model.Currency;
import swing.MainFrame;

import java.io.InputStream;
import java.util.List;

public class MainGUI {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();

        // Obtener la ruta del archivo currencies.tsv desde el classpath
        ClassLoader classLoader = MainGUI.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("currencies.tsv");

        if (inputStream == null) {
            System.err.println("Error: No se pudo encontrar el archivo currencies.tsv en el classpath.");
            System.exit(1);
        }

        CoinsLoader coinsLoader = new TsvFileCoinsLoader(inputStream);
        List<Currency> currencyList = coinsLoader.load();
        MoneyDialog moneyDialog = frame.getMoneyDialog().define(currencyList);
        CurrencyDialog currencyDialog = frame.getCurrencyDialog().define(currencyList);
        MoneyDisplay moneyDisplay = frame.getMoneyDisplay();
        ExchangeRatesLoader exchangeRatesLoader = new JsonExchangeRatesLoader();
        frame.add("change", new ExchangeRatesCommand(moneyDisplay, moneyDialog, currencyDialog, exchangeRatesLoader));

        frame.setVisible(true);
    }
}
