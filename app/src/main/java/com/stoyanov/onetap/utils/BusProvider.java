package com.stoyanov.onetap.utils;

import com.squareup.otto.Bus;

/**
 * Created by alexander on 6/4/16.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}