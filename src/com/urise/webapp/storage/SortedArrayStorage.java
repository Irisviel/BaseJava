package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public void update(Resume resume) {
        throw new NotImplementedException();
    }

    @Override
    public void save(Resume resume) {
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
    protected int getIndex(String uuid) {
        throw new NotImplementedException();
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        throw new NotImplementedException();
    }

    @Override
    protected void fillDeletedElement(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int size() {
        throw new NotImplementedException();
    }
}
