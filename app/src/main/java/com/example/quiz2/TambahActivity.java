package com.example.quiz2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiz2.model.Transaksi;
import com.example.quiz2.helper.TransaksiRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahActivity extends AppCompatActivity {
    private EditText etJumlah, etKeterangan;
    private Spinner spKategori;
    private Button btnDate; // Ubah ini menjadi btnDate
    private Button btnSimpan;
    private String selectedDate; // Menyimpan tanggal yang dipilih
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah); // Ensure the layout name matches

        etJumlah = findViewById(R.id.et_jumlah);
        etKeterangan = findViewById(R.id.et_keterangan);
        spKategori = findViewById(R.id.sp_kategori);
        btnDate = findViewById(R.id.btnDate); // Ubah ini menjadi btnDate
        btnSimpan = findViewById(R.id.btn_simpan);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Inisialisasi dateFormat

        // Menangani klik pada Button untuk memilih tanggal
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Update the spinner options
        String[] arrKategori = {"Pemasukkan", "Pengeluaran"};
        spKategori.setAdapter(new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                arrKategori
        ));

        btnSimpan.setOnClickListener(v -> saveTransaction());
    }

    private void saveTransaction() {
        String jumlahStr = etJumlah.getText().toString();
        String keteranganStr = etKeterangan.getText().toString();
        boolean isIncome = spKategori.getSelectedItemPosition() == 0; // Assuming the first item is income

        if (jumlahStr.isEmpty() || keteranganStr.isEmpty() || selectedDate == null) {
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show();
            return;
        }

        int jumlah = Integer.parseInt(jumlahStr);
        String tanggal = selectedDate; // Gunakan tanggal yang dipilih

        Transaksi transaksi = new Transaksi(tanggal, jumlah, keteranganStr, isIncome);
        TransaksiRepository transaksiRepository = new TransaksiRepository(this);
        transaksiRepository.insertTransaction(transaksi);

        setResult(RESULT_OK);
        finish();
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Mengatur format bulan dan hari agar terlihat benar
            selectedMonth += 1; // Bulan dimulai dari 0
            selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth, selectedYear);
            btnDate.setText(selectedDate); // Tampilkan tanggal yang dipilih di Button
        }, year, month, day);

        datePickerDialog.show();
    }
}
