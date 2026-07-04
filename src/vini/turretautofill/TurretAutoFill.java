package vini.turretautofill;

import mindustry.mod.Mod;

public class TurretAutoFill extends Mod {
    private final TurretFillFeature turretFill = new TurretFillFeature();

    public TurretAutoFill() {
        turretFill.init();
    }
}
