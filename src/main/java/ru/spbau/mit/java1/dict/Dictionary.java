package ru.spbau.mit.java1.dict;


import org.jetbrains.annotations.NotNull;

public interface Dictionary {

    // хеш-таблица, использующая список
    // ключами и значениями выступают строки
    // стандартный способ получить хеш объекта -- вызвать у него метод hashCode()

    // кол-во ключей в таблице
    int size();

    // true, если такой ключ содержится в таблице
    boolean contains(@NotNull String key);

    // возвращает значение, хранимое по ключу key
    // если такого нет, возвращает null
    String get(@NotNull String key);

    // положить по ключу key значение value
    // и вернуть ранее хранимое, либо null;
    // провести рехеширование по необходимости
    String put(@NotNull String key, @NotNull String value);

    // забыть про пару key-value для переданного key
    // и вернуть забытое value, либо null, если такой пары не было;
    // провести рехеширование по необходимости
    String remove(@NotNull String key);

    // забыть про все пары key-value
    void clear();
}