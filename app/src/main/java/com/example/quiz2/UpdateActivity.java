package com.example.quiz2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quiz2.model.Transaksi;
import com.example.quiz2.helper.TransaksiRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UpdateActivity extends AppCompatActivity {

    private EditText etJumlah, etKeterangan;
    private Spinner spKategori;
    private Button btnDate, btnUpdate, btnDelete;
    private String selectedDate;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private Transaksi transaksi; // Object transaksi yang akan diupdate
    private TransaksiRepository transaksiRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update); // Ganti sesuai dengan layout yang digunakan untuk update

        etJumlah = findViewById(R.id.et_jumlah);
        etKeterangan = findViewById(R.id.et_keterangan);
        spKategori = findViewById(R.id.sp_kategori);
        btnDate = findViewById(R.id.btnDate);
        btnUpdate = findViewById(R.id.btnupdate);
        btnDelete = findViewById(R.id.btndelete);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Inisialisasi TransaksiRepository
        transaksiRepository = new TransaksiRepository(this);

        // Ambil data transaksi dari intent
        transaksi = (Transaksi) getIntent().getSerializableExtra("TRANSAKSI");

        if (transaksi != null) {
            // Isi form dengan data transaksi yang akan diupdate
            etJumlah.setText(String.valueOf(transaksi.getAmount()));
            etKeterangan.setText(transaksi.getDescription());
            selectedDate = transaksi.getDate();
            btnDate.setText(selectedDate);

            // Set spinner berdasarkan jenis transaksi (pemasukan/pengeluaran)
            spKategori.setSelection(transaksi.isIncome() ? 0 : 1);
        }

        // Menangani klik pada Button untuk memilih tanggal
        btnDate.setOnClickListener(v -> showDatePickerDialog());

        // Atur opsi spinner
        String[] arrKategori = {"Pemasukan", "Pengeluaran"};
        spKategori.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrKategori
        ));

        // Mengatur event klik tombol update
        btnUpdate.setOnClickListener(v -> updateTransaction());

        // Mengatur event klik tombol delete
        btnDelete.setOnClickListener(v -> deleteTransaction());
    }

    private void updateTransaction() {
        String jumlahStr = etJumlah.getText().toString();
        String keteranganStr = etKeterangan.getText().toString();
        boolean isIncome = spKategori.getSelectedItemPosition() == 0; // Pemasukan jika pilihan pertama

        if (jumlahStr.isEmpty() || keteranganStr.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlah;
        try {
            jumlah = Integer.parseInt(jumlahStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Jumlah harus berupa angka", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perbarui objek transaksi dengan data baru
        transaksi.setAmount(jumlah);
        transaksi.setDescription(keteranganStr);
        transaksi.setDate(selectedDate);
        transaksi.setIncome(isIncome);

        // Simpan perubahan ke database
        transaksiRepository.updateTransaction(transaksi);

        Toast.makeText(this, "Transaksi berhasil diperbarui", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); // Kembali ke activity sebelumnya
    }

    private void deleteTransaction() {
        // Tidak perlu memeriksa ID, hapus langsung berdasarkan objek transaksi
        if (transaksi != null) {
            // Hapus transaksi dari database
            transaksiRepository.deleteTransaction(transaksi); // Langsung menggunakan objek transaksi

            // Berikan pesan sukses kepada pengguna
            Toast.makeText(this, "Transaksi berhasil dihapus", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish(); // Kembali ke activity sebelumnya
        } else {
            // Berikan pesan error jika transaksi tidak ditemukan
            Toast.makeText(this, "Transaksi tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedMonth += 1; // Bulan dimulai dari 0
            selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth, selectedYear);
            btnDate.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.show();
    }
}
