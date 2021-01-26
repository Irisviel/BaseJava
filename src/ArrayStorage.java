/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int head = 0;                               // Позиция для вставки нового элемента

    void clear() {
        for (int i = 0; i < storage.length && storage[i] != null; i++) {
            storage[i] = null;
        }
        head = 0;
    }

    void save(Resume r) {
        if (r == null) return;
        if (head >= storage.length) return;

        storage[head] = r;
        head++;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;

        Resume result = null;
        for (int i = 0; i < head && result == null; i++) {
            if (uuid.equals(storage[i].uuid)) {
                result = storage[i];
            }
        }
        return result;
    }

    void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = -1;
        for (int i = 0; i < head && indexToDelete == -1; i++) {
            if (uuid.equals(storage[i].uuid))
                indexToDelete = i;
        }

        if (indexToDelete != -1) {
            System.arraycopy(storage, indexToDelete + 1, storage, indexToDelete, storage.length - 1 - indexToDelete);
            head--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[head];
        System.arraycopy(storage, 0, result, 0, head);
        return result;
    }

    int size() {
        return head;
    }
}
