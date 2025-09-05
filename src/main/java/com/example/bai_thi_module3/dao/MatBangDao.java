package com.example.bai_thi_module3.dao;

import com.example.bai_thi_module3.model.MatBang;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MatBangDao {
    List<MatBang> findAllSortedByArea();

    List<MatBang> search(String loai, Integer tang, Long giaMin, Long giaMax,
                         LocalDate from, LocalDate to);

    Optional<MatBang> findById(String maMb);

    boolean insert(MatBang mb);

    boolean delete(String maMb);
}
