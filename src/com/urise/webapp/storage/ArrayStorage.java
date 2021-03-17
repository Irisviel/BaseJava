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

        int indexToUpdate = getIndex(resume.uuid());
        if (indexToUpdate == -1) {
            System.out.println("No resume with uuid: " + resume.uuid());
            return;
        }

        storage[indexToUpdate] = resume;
    }

    public void save(Resume resume) {
        if (resume == null) return;
        if (size >= storage.length) return;
        if (getIndex(resume.uuid()) != -1) {
            System.out.println("Resume with uuid " + resume.uuid() + " already exists.");
            return;
        };

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
        } else {
            storage[indexToDelete] = storage[size - 1];
            storage[size] = null;
            size--;
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    public int size() {
        return size;
    }
}
