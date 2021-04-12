package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.input.GeneralAlert;
import java.util.List;
import java.time.Instant;

public class SpotChangeService {

    public GeneralAlert checkAlert(List<CurrencyConversionRate> ccrs) {
        //extract basic elements
        CurrencyConversionRate currentCcr = ccrs.get(ccrs.size() - 1);
        Instant currentTime = currentCcr.getTimestamp();
        String currentPair = currentCcr.getCurrencyPair();
        Double currentRate = currentCcr.getRate();

        //calculate 5 minutes average
        Double averageRate = ccrs.stream()
                .filter(ccr -> ccr.getTimestamp().isBefore(currentTime)
                                && ccr.getTimestamp().isAfter(currentTime.minusSeconds(300)))
                .mapToDouble(ccr -> ccr.getRate())
                .average().orElse(Double.NaN);

        //check 10% change
        if (Math.abs(averageRate - currentRate) > (currentRate / 10)) {
            return new GeneralAlert(currentTime, currentPair, "spotChange", null);
        }
        return null;
    }
}
