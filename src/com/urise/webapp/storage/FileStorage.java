package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileStorage extends AbstractStorage<File> {
    private File directory;

    private StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");

        this.streamSerializer = streamSerializer;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException exception) {
            throw new StorageException("Unable to write file", resume.getUuid(), exception);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new StorageException("Unable to create file " + file.getName(), file.getName(), exception);
        }
        doUpdate(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException exception) {
            throw new StorageException("Unable to read file " + file.getName(), file.getName(), exception);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Unable to delete file" + file.getName(), file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.stream(getStoredFiles()).map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        for (File file : getStoredFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getStoredFiles().length;
    }

    private File[] getStoredFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Invalid path to storage directory");
        }
        return files;
    }
}
