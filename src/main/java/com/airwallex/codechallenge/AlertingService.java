package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.CurrencyConversionRate;
import com.airwallex.codechallenge.input.GeneralAlert;
import java.util.LinkedList;
import java.util.List;

public class AlertingService {

    public static SpotChangeService scService = new SpotChangeService();
    public static RisingFallingService rfService = new RisingFallingService();

    public List<GeneralAlert> checkAlert(List<CurrencyConversionRate> ccrs) {
        List<GeneralAlert> alertList = new LinkedList<>();
        alertList.add(scService.checkAlert(ccrs));
        alertList.add(rfService.checkAlert(ccrs));
        return alertList;
    }
}
