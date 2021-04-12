package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.input.GeneralAlert;
import java.time.Instant;
import java.util.List;
import java.util.ListIterator;

public class RisingFallingService {

    public GeneralAlert checkAlert(List<CurrencyConversionRate> ccrs) {
        //extract basic elements
        CurrencyConversionRate currentCcr = ccrs.get(ccrs.size() - 1);
        CurrencyConversionRate lastCcr = ccrs.get(ccrs.size() - 2);
        Instant currentTime = currentCcr.getTimestamp();
        String currentPair = currentCcr.getCurrencyPair();
        Double currentRate = currentCcr.getRate();
        Double lastRate = lastCcr.getRate();

        //detect continuous trend
        String trendType = (currentRate > lastRate) ? "rising" : "falling";
        int trendSeconds = 1;
        Double tempRate = currentRate;
        ListIterator iterator = ccrs.listIterator(ccrs.size() - 1);
        while (iterator.hasPrevious()) {
            CurrencyConversionRate prevCcr = (CurrencyConversionRate)iterator.previous();
            Double prevRate = prevCcr.getRate();
            if (trendType.equals("rising")) {
                if (prevRate >= tempRate) break;
                tempRate = prevRate;
            } else if (trendType.equals("falling")) {
                if (prevRate <= tempRate) break;
                tempRate = prevRate;
            }
            trendSeconds++;
        }

        //check 15 minutes threshold and implement 1-minute throttle
        if (trendSeconds >= 900 && currentCcr.getTimestamp().getEpochSecond() % 60 == 0) {
            return new GeneralAlert(currentTime, currentPair, trendType, trendSeconds);
        }
        return null;
    }
}
