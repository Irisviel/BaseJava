package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];

    // Количество резюме
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (resume == null) return;

        int indexToUpdate = getIndex(resume.getUuid());
        if (indexToUpdate == -1) {
            System.out.println("No resume with uuid: " + resume.getUuid());
            return;
        }

        storage[indexToUpdate] = resume;
    }

    public void save(Resume resume) {
        if (resume == null) return;

        if (size >= storage.length) {
            System.out.println("Storage overflow.");
            return;
        }
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Resume with uuid " + resume.getUuid() + " already exists.");
            return;
        }

        storage[size] = resume;
        size++;
    }

    public Resume get(String uuid) {
        if (uuid == null) return null;

        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("No resume with uuid: " + uuid);
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = getIndex(uuid);
        if (indexToDelete == -1) {
            System.out.println("No resume with uuid: " + uuid);
            return;
        } else {
            storage[indexToDelete] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResume = new Resume[size];
        System.arraycopy(storage, 0, allResume, 0, size);
        return allResume;
    }

    public int size() {
        return size;
    }
}
