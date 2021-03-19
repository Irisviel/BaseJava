package com.urise.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    public void saveOverflow() throws Exception {
        assertTrue(true);
    }
}