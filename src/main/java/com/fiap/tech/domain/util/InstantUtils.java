package com.fiap.tech.domain.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
}
