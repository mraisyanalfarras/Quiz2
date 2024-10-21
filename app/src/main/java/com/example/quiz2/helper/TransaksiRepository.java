package com.example.quiz2.helper;

import android.content.Context;
import com.example.quiz2.AppDatabase;
import com.example.quiz2.dao.TransaksiDao;
import com.example.quiz2.model.Transaksi;
import java.util.List;

public class TransaksiRepository {
    private final TransaksiDao transaksiDao;

    public TransaksiRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        transaksiDao = db.transactionDao();
    }

    public void insertTransaction(Transaksi transaksi) {
        new Thread(() -> transaksiDao.insert(transaksi)).start(); // Run insertion in a separate thread
    }

    public List<Transaksi> getAllTransactions() {
        return transaksiDao.getAllTransactions();
    }
}