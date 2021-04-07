package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.*;

// TODO: Remove, obsolete
public class ObjectStreamFileStorage extends AbstractFileStorage {
    protected ObjectStreamFileStorage(File directory) {
        super(directory, new ObjectStreamSerializer());
    }
}