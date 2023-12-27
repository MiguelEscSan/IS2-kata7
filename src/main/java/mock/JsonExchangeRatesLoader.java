package mock;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interfaces.ExchangeRatesLoader;
import model.Currency;
import model.ExchangeRates;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonExchangeRatesLoader implements ExchangeRatesLoader {

    private String url;
    private ExchangeRates exchangeRate;

    public void setUrl(Currency base, Currency target) {
        // Por limitaciones de la api no podemos escoger el base no obstante seguiria el mismo patron que para escoger el target.
        this.url = "http://api.exchangeratesapi.io/v1/latest?access_key=e7a83fdc52a9dac2758d5d8fdc326d4a&symbols="+target.getCode()+"&format=1";
    }
    @Override
    public ExchangeRates load(Currency from, Currency to) {
        try {
            String jsonResponse = fetchJsonResponse();
            exchangeRate =  parseJsonResponse(jsonResponse, from , to);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    private String fetchJsonResponse() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }

        reader.close();
        connection.disconnect();

        return response.toString();
    }

    private ExchangeRates parseJsonResponse(String jsonResponse, Currency from, Currency to) throws ParseException {
        ClassLoader classLoader = JsonExchangeRatesLoader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("currencies.tsv");
        TsvFileCoinsLoader coinsLoader = new TsvFileCoinsLoader(inputStream);
        List<Currency> currencies = coinsLoader.load();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonResponse).getAsJsonObject();

        // Sacamos la fecha
        String dateString = jsonObject.get("date").getAsString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);


                // Obtenemos las tasas de cambio
        JsonObject ratesObject = jsonObject.getAsJsonObject("rates");

        // Suponiendo que solo hay una entrada en el objeto ratesObject
        Map.Entry<String, JsonElement> entry = ratesObject.entrySet().iterator().next();

        float exchangeRate = entry.getValue().getAsFloat(); // El valor (tasa de cambio)

        return new ExchangeRates(from, to, date, exchangeRate);
    }



}