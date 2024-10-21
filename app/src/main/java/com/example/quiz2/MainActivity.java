package com.example.quiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiz2.adapter.TransaksiAdapter;
import com.example.quiz2.helper.TransaksiRepository;
import com.example.quiz2.model.Transaksi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TRANSACTION_REQUEST = 1;
    private static final int REQUEST_UPDATE_TRANSACTION = 1;
    private ListView listViewTransaksi;
    private TransaksiAdapter adapter;
    private TransaksiRepository transaksiRepository;
    private TextView tvSaldo; // Tambahkan TextView untuk saldo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTransaksi = findViewById(R.id.lvTransactions);
        tvSaldo = findViewById(R.id.tvSaldo); // Inisialisasi TextView saldo
        transaksiRepository = new TransaksiRepository(this);

        loadTransactions();

        FloatingActionButton fabAddTransaction = findViewById(R.id.fabAddTransaction);
        fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            startActivityForResult(intent, ADD_TRANSACTION_REQUEST);
        });

        listViewTransaksi.setOnItemClickListener((parent, view, position, id) -> {
            Transaksi selectedTransaksi = (Transaksi) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
            intent.putExtra("TRANSAKSI", selectedTransaksi);
            startActivityForResult(intent, REQUEST_UPDATE_TRANSACTION);
        });
    }

    private void loadTransactions() {
        new Thread(() -> {
            List<Transaksi> transactions = transaksiRepository.getAllTransactions();
            runOnUiThread(() -> {
                adapter = new TransaksiAdapter(MainActivity.this, transactions);
                listViewTransaksi.setAdapter(adapter);
                calculateSaldo(transactions); // Hitung saldo setiap kali transaksi dimuat
            });
        }).start();
    }

    private void calculateSaldo(List<Transaksi> transactions) {
        int totalSaldo = 0; // Inisialisasi saldo

        // Menghitung total pemasukan dan pengeluaran
        for (Transaksi transaksi : transactions) {
            if (transaksi.isIncome()) {
                totalSaldo += transaksi.getAmount(); // Tambah jika pemasukan
            } else {
                totalSaldo -= transaksi.getAmount(); // Kurang jika pengeluaran
            }
        }

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        tvSaldo.setText(numberFormat.format(totalSaldo)); // Format dan update TextView saldo
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRANSACTION_REQUEST && resultCode == RESULT_OK) {
            loadTransactions(); // Refresh the transaction list
        } else if (requestCode == REQUEST_UPDATE_TRANSACTION && resultCode == RESULT_OK) {
            loadTransactions(); // Refresh after update
        }
    }
}
