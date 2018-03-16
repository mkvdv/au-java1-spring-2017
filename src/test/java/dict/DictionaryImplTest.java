package dict;

import org.junit.Test;

public class DictionaryImplTest {

    private static DictionaryImpl instance() {
        try {
            return (DictionaryImpl) Class.forName("ru.spbau.mit.java1.dict.DictionaryImpl").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Error while class loading");
    }

    @Test
    public void test() {
        // todo impl
    }

}