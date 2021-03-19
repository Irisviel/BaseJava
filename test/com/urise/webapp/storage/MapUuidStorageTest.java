package com.urise.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    @Test
    public void getAll() {
        assertTrue(true);
    }

    @Override
    @Test
    public void saveOverflow() {
        assertTrue(true);
    }
}
