package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.storage.ResumeTestData.newResume;
import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String DUMMY = "dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = newResume(UUID_1, "Name1");
        RESUME_2 = newResume(UUID_2, "Name2");
        RESUME_3 = newResume(UUID_3, "Name3");
        RESUME_4 = newResume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume newResume = newResume(UUID_1, "newResume");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get(DUMMY);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actualResumes = storage.getAllSorted();
        assertEquals(3, actualResumes.size());
        assertEquals(actualResumes, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(DUMMY);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(DUMMY);
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}