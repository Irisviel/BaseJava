package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class AbstractArrayStorage implements Storage {
    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public void update(Resume r) {
        throw new NotImplementedException();
    }

    @Override
    public void save(Resume r) {
        throw new NotImplementedException();
    }

    @Override
    public Resume get(String uuid) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(String uuid) {
        throw new NotImplementedException();
    }

    @Override
    public Resume[] getAll() {
        throw new NotImplementedException();
    }

    @Override
    public int size() {
        throw new NotImplementedException();
    }
}
