package com.airwallex.codechallenge;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

public class CustomSerializer extends JsonSerializer<Instant> {
    @Override
    public Class<Instant> handledType() {
        return Instant.class;
    }

    @Override
    public void serialize(Instant instant, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeNumber(BigDecimal.valueOf(instant.toEpochMilli(), 3));
    }
}
