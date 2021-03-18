package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchVal = new Resume();
        searchVal.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchVal);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = resume;
    }

    @Override
    protected void fillDeletedElement(int index) {
        int elementsToMove = size - index - 1;
        if (elementsToMove > 0) {
            System.arraycopy(storage, index + 1, storage, index, elementsToMove);
        }
    }
}
