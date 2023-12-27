package model;

import java.util.Date;
import java.util.Objects;
public class ExchangeRates {

    private final Currency from;
    private final Currency to;
    private final Date date;
    private final float rate;

    public ExchangeRates(Currency from, Currency to, Date date, float rate) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.rate = rate;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public Date getDate() {
        return date;
    }

    public float getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRates that = (ExchangeRates) o;
        return Float.compare(rate, that.rate) == 0 && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, date, rate);
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", rate=" + rate +
                '}';
    }


}
