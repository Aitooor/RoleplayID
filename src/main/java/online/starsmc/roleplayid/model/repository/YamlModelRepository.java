package online.starsmc.roleplayid.model.repository;

import online.starsmc.roleplayid.model.Model;
import online.starsmc.roleplayid.model.YamlCodec;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlModelRepository<ModelType extends Model>
        implements ModelRepository<ModelType> {

    private final Path folderPath;
    private final YamlCodec<ModelType> yamlCodec;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public YamlModelRepository(Path folderPath, YamlCodec<ModelType> yamlCodec) {
        if(Files.notExists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.folderPath = folderPath;
        this.yamlCodec = yamlCodec;
    }


    @Override
    public ModelType find(String id) throws IOException {
        Path modelPath = this.folderPath.resolve(id + ".yml");
        if(Files.notExists(modelPath)) {
            return null;
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(modelPath)) {
            YamlConfiguration modelYaml = YamlConfiguration.loadConfiguration(bufferedReader);
            return yamlCodec.deserialize(modelYaml.getValues(true));
        }

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void save(ModelType model) throws IOException {
        YamlConfiguration modelYaml = new YamlConfiguration();
        modelYaml.options().copyDefaults(true);
        modelYaml.addDefaults(yamlCodec.serialize(model));
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.folderPath.resolve(model.getId() + ".yml"))) {
            String data = modelYaml.saveToString();
            bufferedWriter.write(data);
        }
    }

    @Override
    public void remove(ModelType model) throws IOException {
        Files.delete(this.folderPath.resolve(model.getId() + ".yml"));
    }

    public boolean equals(Object object) {

        return true;
    }
}
