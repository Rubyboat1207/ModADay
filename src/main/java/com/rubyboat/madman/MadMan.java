package com.rubyboat.madman;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class MadMan implements ModInitializer {

    public static final GameRules.Key<DoubleRule> TIME_MULTIPLIER =
            GameRuleRegistry.register("timeMultiplier", GameRules.Category.MISC, GameRuleFactory.createDoubleRule(1, 0, Double.MAX_VALUE));

    public static final GameRules.Key<GameRules.IntRule> DAY_COUNT =
            GameRuleRegistry.register("modDayCount", GameRules.Category.UPDATES, GameRuleFactory.createIntRule(0));

    @Override
    public void onInitialize() {

    }
}
