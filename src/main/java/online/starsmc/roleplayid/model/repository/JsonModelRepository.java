package online.starsmc.roleplayid.model.repository;

import com.google.gson.Gson;
import online.starsmc.roleplayid.model.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonModelRepository<ModelType extends Model>
        implements ModelRepository<ModelType> {

    private final Path folderPath;
    private final Class<ModelType> modelTypeClass;
    private final Gson gson;

    public JsonModelRepository(
            final Path folderPath,
            final Class<ModelType> modelTypeClass,
            final Gson gson
    ) {
        if(Files.notExists(folderPath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.folderPath = folderPath;
        this.modelTypeClass = modelTypeClass;
        this.gson = gson;
    }

    @Override
    public ModelType find(final String id) throws Exception {
        Path modelPath = this.folderPath.resolve(id + ".json");
        if(Files.notExists(modelPath)) {
            return null;
        }
        try (BufferedReader bufferedReader = Files.newBufferedReader(modelPath)) {
            return this.gson.fromJson(
                    bufferedReader,
                    this.modelTypeClass);
        }
    }

    @Override
    public void save(final ModelType modelType) throws Exception {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(this.folderPath.resolve(modelType.getId() + ".json"))) {
            this.gson.toJson(
                    modelType,
                    bufferedWriter);
        }
    }

    @Override
    public void remove(ModelType model) throws Exception {
        Files.delete(this.folderPath.resolve(model.getId() + ".json"));
    }

}
