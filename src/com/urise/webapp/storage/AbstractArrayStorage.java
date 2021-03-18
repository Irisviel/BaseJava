package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume resume) {
        if (resume == null) return;

        int indexToUpdate = getIndex(resume.getUuid());
        if (indexToUpdate == -1) {
            throw new NotExistStorageException(resume.getUuid());
        }

        storage[indexToUpdate] = resume;
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) return;

        int indexToSave = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        }
        if (indexToSave >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insertElement(resume, indexToSave);
        size++;
    }

    @Override
    public Resume get(String uuid) {
        if (uuid == null) return null;

        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = getIndex(uuid);
        if (indexToDelete < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedElement(indexToDelete);
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
