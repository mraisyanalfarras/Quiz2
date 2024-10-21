package com.example.quiz2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiz2.adapter.TransaksiAdapter;
import com.example.quiz2.helper.TransaksiRepository;
import com.example.quiz2.model.Transaksi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_TRANSACTION_REQUEST = 1;
    private ListView listViewTransaksi;
    private TransaksiAdapter adapter;
    private TransaksiRepository transaksiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure to use the correct layout file name

        listViewTransaksi = findViewById(R.id.lvTransactions);
        transaksiRepository = new TransaksiRepository(this);

        loadTransactions();

        FloatingActionButton fabAddTransaction = findViewById(R.id.fabAddTransaction);
        fabAddTransaction.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TambahActivity.class);
            startActivityForResult(intent, ADD_TRANSACTION_REQUEST);
        });
    }

    private void loadTransactions() {
        new Thread(() -> {
            List<Transaksi> transactions = transaksiRepository.getAllTransactions();
            runOnUiThread(() -> {
                adapter = new TransaksiAdapter(MainActivity.this, transactions);
                listViewTransaksi.setAdapter(adapter);
            });
        }).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRANSACTION_REQUEST && resultCode == RESULT_OK) {
            loadTransactions(); // Refresh the transaction list
        }
    }
}
