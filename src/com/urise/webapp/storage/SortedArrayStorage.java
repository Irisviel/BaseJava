package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SortedArrayStorage extends AbstractArrayStorage {
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
}
