package online.starsmc.roleplayid.model.repository;

import com.google.common.cache.Cache;
import online.starsmc.roleplayid.model.Model;

public class GuavaModelRepository<ModelType extends Model>
        implements ModelRepository<ModelType> {

    private final Cache<String, ModelType> cache;

    public GuavaModelRepository(Cache<String, ModelType> cache) {
        this.cache = cache;
    }

    @Override
    public ModelType find(String id) {
        return cache.getIfPresent(id);
    }

    @Override
    public void save(ModelType model) {
        cache.put(model.getId(), model);
    }

    @Override
    public void remove(ModelType model) {
        cache.invalidate(model.getId());
    }
}
