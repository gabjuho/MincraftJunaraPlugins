package com.gabjuho.junaraplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private final Main plugin; // 메인클래스 객체
    private static DataManager instance = new DataManager(Main.getPlugin(Main.class)); //데이터 매니저는 싱글톤으로 생성
    private FileConfiguration dataConfig = null;
    private File dataFile = null;

    private DataManager(Main plugin) // 생성자 막아놓음 (싱글톤 패턴으로 인한)
    {
        this.plugin = plugin;
        saveDefaultConfig();
    }
    public static DataManager getInstance() // 싱글톤 인스턴스 반환
    {
        if(instance == null)
            instance = new DataManager(Main.getPlugin(Main.class));
        return instance;
    }

    public void reloadConfig() // 데이터 리로드
    {
        if(this.dataFile == null)
            this.dataFile = new File(this.plugin.getDataFolder(),"data.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.dataFile);
        InputStream defaultStream = this.plugin.getResource("data.yml");
        if(defaultStream != null)
        {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getDataConfig() // 데이터 불러오기
    {
        if(this.dataConfig == null)
            reloadConfig();

        return this.dataConfig;
    }

    public void saveConfig() // 데이터 저장
    {
        if(this.dataConfig == null || this.dataFile == null)
            return;

        try
        {
            this.getDataConfig().save(this.dataFile);
        }catch(IOException e)
        {
            plugin.getLogger().log(Level.SEVERE,"config 파일이 저장되지 않았습니다. -> " + this.dataFile,e);
        }
    }

    public void saveDefaultConfig() // 데이터 디폴트 저장
    {
        if(this.dataFile == null)
            this.dataFile = new File(this.plugin.getDataFolder(),"data.yml");

        if(!this.dataFile.exists())
        {
            this.plugin.saveResource("data.yml",false);
        }
    }
}