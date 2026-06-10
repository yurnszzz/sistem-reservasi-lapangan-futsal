package com.futsalku.interfaces;

import java.util.List;

/**
 * Interface Searchable
 * Untuk pencarian data member & booking
 * 
 * Menerapkan konsep: Abstraction (Interface)
 */
public interface Searchable<T> {
    List<T> cari(String keyword);
}
