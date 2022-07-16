package org.unict.domain;

public class StrategyFactory {
    private static StrategyFactory singleton;

    private StrategyFactory() {}

    public static StrategyFactory getInstance() {
        if(singleton == null)
            singleton = new StrategyFactory();
            //System.out.println("Instanza già creata");
        return singleton;
    }

    public StrategyInterface getEtaStrategy(){return new StrategyEta();}
}
