package com.blackgear.geologicexpansion.core;

import com.blackgear.geologicexpansion.client.ClientSetup;
import com.blackgear.geologicexpansion.common.CommonSetup;
import com.blackgear.geologicexpansion.core.platform.ModInstance;

public class GeologicExpansion {
    public static final String MOD_ID = "geologicexpansion";
    public static final ModInstance INSTANCE = ModInstance.create(MOD_ID).common(CommonSetup::onInstance).postCommon(CommonSetup::postInstance).client(ClientSetup::onInstance).postClient(ClientSetup::postInstance).build();

    public static void bootstrap() {
        INSTANCE.bootstrap();
    }
}