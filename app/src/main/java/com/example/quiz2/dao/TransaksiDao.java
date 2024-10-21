package com.example.quiz2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quiz2.model.Transaksi;

import java.util.List;

@Dao
public interface TransaksiDao {

    // Insert a new transaction
    @Insert
    void insert(Transaksi transaksi);

    // Update an existing transaction
    @Update
    void update(Transaksi transaksi);

    // Delete a transaction
    @Delete
    void delete(Transaksi transaksi); // Hapus berdasarkan objek transaksi


    // Get all transactions
    @Query("SELECT * FROM Transaksi")  // Make sure the table name matches
    List<Transaksi> getAllTransactions();

    // Get a transaction by its ID
    @Query("SELECT * FROM Transaksi WHERE id = :transactionId") // Make sure the table name matches
    Transaksi getTransactionById(int transactionId);

    // Get transactions based on income or expense
    @Query("SELECT * FROM Transaksi WHERE isIncome = :isIncome")
    List<Transaksi> getTransactionsByType(boolean isIncome);

}
