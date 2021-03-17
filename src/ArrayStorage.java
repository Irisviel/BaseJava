/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    // Позиция для вставки нового элемента
    int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void update(Resume r) {
        if (r == null) return;

        int indexToUpdate = indexOf(r);
        if (indexToUpdate == -1) return;

        storage[indexToUpdate] = r;
    }

    void save(Resume r) {
        if (r == null) return;
        if (size >= storage.length) return;
        if (indexOf(r) != -1) return;

        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {
            storage[indexToDelete] = storage[size - 1];
            storage[size] = null;
            size--;
        }
    }

    int indexOf(Resume r) {
        for (int i = 0; i < size; i++) {
            if (r.uuid.equals(storage[i].uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size];
        System.arraycopy(storage, 0, result, 0, size);
        return result;
    }

    int size() {
        return size;
    }
}
