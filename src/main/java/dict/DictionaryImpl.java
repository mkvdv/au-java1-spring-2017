package dict;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;

public class DictionaryImpl implements Dictionary {
    private final int maxChainLength;
    private int size = 0;
    private ArrayList<LinkedList<String>> table;

    DictionaryImpl(int maxChainLength) {
        if (maxChainLength < 0) {
            throw new IllegalArgumentException();
        }
        this.maxChainLength = maxChainLength;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NotNull String key) {
        // todo impl
        return false;
    }

    @Override
    public String get(@NotNull String key) {
        // todo impl
        return null;
    }

    @Override
    public String put(@NotNull String key, @NotNull String value) {
        // todo impl
        return null;
    }

    @Override
    public String remove(@NotNull String key) {
        // todo impl
        return null;
    }

    @Override
    public void clear() {
        // todo impl
    }
}
