package com.shqip.tv.oni.models;

import java.io.Serializable;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Other implements Serializable {

    public String useragent;
    public String url_kontakt;
    public String tabela_ads;
    public String tabela_update;


    public Other(String useragent, String url_kontakt, String tabela_ads, String tabela_update) {
        this.useragent = useragent;
        this.url_kontakt = url_kontakt;
        this.tabela_ads = tabela_ads;
        this.tabela_update = tabela_update;
    }

    public Other() {

    }


    public String getUseragent() {
        return useragent;
    }

    public void setUseragent(String useragent) {
        this.useragent = useragent;
    }

    public String getUrl_kontakt() {
        return url_kontakt;
    }

    public void setUrl_kontakt(String url_kontakt) {
        this.url_kontakt = url_kontakt;
    }

    public String getTabela_ads() {
        return tabela_ads;
    }

    public void setTabela_ads(String tabela_ads) {
        this.tabela_ads = tabela_ads;
    }

    public String getTabela_update() {
        return tabela_update;
    }

    public void setTabela_update(String tabela_update) {
        this.tabela_update = tabela_update;
    }

}
