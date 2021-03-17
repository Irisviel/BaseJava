/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    // Позиция для вставки нового элемента
    int head = 0;

    void clear() {
        for (int i = 0; i < head; i++) {
            storage[i] = null;
        }
        head = 0;
    }

    void update(Resume r) {
        if (r == null) return;

        int indexToUpdate = indexOf(r);
        if (indexToUpdate == -1) return;

        storage[indexToUpdate] = r;
    }

    void save(Resume r) {
        if (r == null) return;
        if (head >= storage.length) return;
        if (indexOf(r) != -1) return;

        storage[head] = r;
        head++;
    }

    Resume get(String uuid) {
        if (uuid == null) return null;

        for (int i = 0; i < head; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (uuid == null) return;

        int indexToDelete = -1;
        for (int i = 0; i < head; i++) {
            if (uuid.equals(storage[i].uuid)) {
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete != -1) {
            storage[indexToDelete] = storage[head - 1];
            storage[head] = null;
            head--;
        }
    }

    int indexOf(Resume r) {
        for (int i = 0; i < head; i++) {
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
        Resume[] result = new Resume[head];
        System.arraycopy(storage, 0, result, 0, head);
        return result;
    }

    int size() {
        return head;
    }
}
