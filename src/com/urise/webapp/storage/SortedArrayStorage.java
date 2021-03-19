package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "stub");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void insertElement(Resume resume, int index) {
        int insertIdx = -index - 1;
        System.arraycopy(storage, insertIdx, storage, insertIdx + 1, size - insertIdx);
        storage[insertIdx] = resume;
    }

    protected void fillDeletedElement(int index) {
        int elementsToMove = size - index - 1;
        if (elementsToMove > 0) {
            System.arraycopy(storage, index + 1, storage, index, elementsToMove);
        }
    }
}
