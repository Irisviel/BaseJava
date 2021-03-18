package com.urise.webapp.storage;

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
            System.out.println("No resume with uuid: " + resume.getUuid());
            return;
        }

        storage[indexToUpdate] = resume;
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) return;
        int indexToSave = getIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow.");
            return;
        }
        if (indexToSave != -1) {
            System.out.println("Resume with uuid " + resume.getUuid() + " already exists.");
            return;
        };
        insertElement(resume, indexToSave);
        size++;
    }

    @Override
    public Resume get(String uuid) {
        if (uuid == null) return null;

        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("No resume with uuid: " + uuid);
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = getIndex(uuid);

        if (indexToDelete == -1) {
            System.out.println("No resume with uuid: " + uuid);
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
