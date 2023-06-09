package online.starsmc.roleplayid.model.repository;

import online.starsmc.roleplayid.model.Model;


public class CachedModelRepository<ModelType extends Model>
        implements ModelRepository<ModelType> {

    private ModelRepository<ModelType> persistentModelRepository;
    private ModelRepository<ModelType> cachedModelRepository;

    public CachedModelRepository(
            ModelRepository<ModelType> persistentModelRepository,
            ModelRepository<ModelType> cachedModelRepository
    ) {
        this.persistentModelRepository = persistentModelRepository;
        this.cachedModelRepository = cachedModelRepository;
    }

    // Find in persistent
    @Override
    public ModelType find(String id) throws Exception {
        return this.persistentModelRepository.find(id);
    }

    // Find in cached
    public ModelType get(String id) throws Exception {
        return this.cachedModelRepository.find(id);
    }

    // Find first in cached repository
    // if not present then find in persistent repository
    public ModelType getOrFind(String id) throws Exception {
        ModelType model = get(id);
        if (model == null) {
            return find(id);
        }
        return model;
    }

    // Find first in cached repository
    // if not present then find in persistent repository
    // (And cached the model)
    public ModelType getOrFindAndCache(String id) throws Exception {
        ModelType model = get(id);
        if (model == null) {
            model = find(id);
            if (model == null) {
                return null;
            }
            this.cachedModelRepository.save(model);
        }
        return model;
    }

    @Override
    public void save(ModelType model) throws Exception {
        this.persistentModelRepository.save(model);
    }

    public void saveInCache(ModelType model) throws Exception {
        this.cachedModelRepository.save(model);
    }

    public void saveInBoth(ModelType model) throws Exception {
        this.persistentModelRepository.save(model);
        this.cachedModelRepository.save(model);
    }

    public void upload(String id) throws Exception {
        ModelType modelCached = get(id);
        if (modelCached == null) {
            return;
        }
        this.persistentModelRepository.save(modelCached);
        this.cachedModelRepository.remove(modelCached);
    }

    @Override
    public void remove(ModelType model) throws Exception {
        this.persistentModelRepository.remove(model);
    }

    public void removeInCache(ModelType model) throws Exception {
        this.cachedModelRepository.remove(model);
    }

    public void removeInBoth(ModelType model) throws Exception {
        this.persistentModelRepository.remove(model);
        this.cachedModelRepository.remove(model);
    }
}
