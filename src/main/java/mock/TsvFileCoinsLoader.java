package mock;

import interfaces.CoinsLoader;
import model.Currency;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TsvFileCoinsLoader implements CoinsLoader {

    private final InputStream file;

    public TsvFileCoinsLoader(InputStream file) {
        this.file = file;
    }

    @Override
    public List<Currency> load() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file))) {
            return load(bufferedReader);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo", e);
        }
    }

    private List<Currency> load(BufferedReader bufferedReader) throws IOException {
        List<Currency> result = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.add(toCurrency(line));
        }
        return result;
    }

    private Currency toCurrency(String line) {
        String[] split = line.split("\t");
        if (split.length > 2) {
            return new Currency(split[0], split[1], split[2]);
        } else {
            return new Currency(split[0], split[1], "");
        }
    }
}
