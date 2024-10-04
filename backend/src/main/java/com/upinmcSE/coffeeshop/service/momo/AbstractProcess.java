package com.upinmcSE.coffeeshop.service.momo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.upinmcSE.coffeeshop.configuration.momopay.CustomEnvironment;
import com.upinmcSE.coffeeshop.configuration.momopay.PartnerInfo;
import com.upinmcSE.coffeeshop.exception.MoMoException;
import com.upinmcSE.coffeeshop.utils.momopay.Execute;

public abstract class AbstractProcess<T, V> {
    protected PartnerInfo partnerInfo;
    protected CustomEnvironment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(CustomEnvironment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    public abstract V execute(T request) throws MoMoException;
}
