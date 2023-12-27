package interfaces;

import model.Currency;

import java.util.List;

public interface CoinsLoader {
    List<Currency> load();
}
