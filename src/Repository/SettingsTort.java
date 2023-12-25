package Repository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SettingsTort {
    private static SettingsTort instance;

    private String repoType;

    private final String repoFile;

    private SettingsTort(String repoType , String repoFile)
    {
        this.repoFile = repoFile;
        this.repoType = repoType;
    }

    public String getRepoType(){
        return repoType;
    }
    public String getRepoFile()
    {
        return repoFile;
    }

    private static Properties loadSettings()
    {
        try(FileReader fr = new FileReader("/Users/paulb/Desktop/lab4/src/Repository/settingsTort.properties"))
        {
            Properties settings = new Properties();
            settings.load(fr);
            return settings;
        }catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static synchronized SettingsTort getInstance(){
        Properties properties = loadSettings();
        instance = new SettingsTort(properties.getProperty("repo_type"),properties.getProperty("repo_file"));
        return instance;
    }
}
