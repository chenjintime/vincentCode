package com.airwallex.codechallenge;

import com.airwallex.codechallenge.input.GeneralAlert;
import com.airwallex.codechallenge.input.Reader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.stream.Collectors;

public class App {

    public static AlertingService alertingService = new AlertingService();
    public static JavaTimeModule module = new JavaTimeModule();
    public static ObjectMapper objectMapper =
            new ObjectMapper().registerModule(module.addSerializer(new CustomSerializer()));

    public static void main(String[] args) {
        Reader reader = new Reader();
        reader.read(args[0])
                .distinct().collect(Collectors.groupingBy(ccr -> ccr.getCurrencyPair()))
                .values().stream()
                .map(ccrs -> alertingService.checkAlert(ccrs))
                .forEach(alerts -> alerts.forEach(alert -> printJson(alert)));
    }

    public static void printJson(GeneralAlert alert) {
        if (alert != null) {
            try {
                System.out.println(objectMapper.writeValueAsString(alert));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
