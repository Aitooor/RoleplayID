package online.starsmc.roleplayid.model.repository;

import online.starsmc.roleplayid.model.Model;

public interface ModelRepository<ModelType extends Model> {

    ModelType find(String id) throws Exception;
    void save(ModelType model) throws Exception;
    void remove(ModelType model) throws Exception;
}
