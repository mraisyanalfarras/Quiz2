package com.example.quiz2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quiz2.R;
import com.example.quiz2.model.Transaksi;

import java.util.List;

public class TransaksiAdapter extends ArrayAdapter<Transaksi> {
    private final Context context;
    private final List<Transaksi> transaksiList;

    public TransaksiAdapter(Context context, List<Transaksi> transaksiList) {
        super(context, R.layout.item, transaksiList);
        this.context = context;
        this.transaksiList = transaksiList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Inflate layout item_transaksi.xml
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        // Ambil elemen dari daftar transaksi
        Transaksi transaksi = transaksiList.get(position);

        // Inisialisasi tampilan
        TextView tvTanggal = convertView.findViewById(R.id.tvTanggal);
        TextView tvJumlah = convertView.findViewById(R.id.tvNominal);
        TextView tvKeterangan = convertView.findViewById(R.id.tvDeskripsi);

        // Set data ke TextView
        tvTanggal.setText(transaksi.getDate());
        tvJumlah.setText("Rp. " + transaksi.getAmount());
        tvKeterangan.setText(transaksi.getDescription());

        // Mengatur warna jumlah berdasarkan kategori (pemasukan/pengeluaran)
        if (transaksi.isIncome()) {
            tvJumlah.setTextColor(context.getResources().getColor(android.R.color.holo_green_dark));
        } else {
            tvJumlah.setTextColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return transaksiList.size();
    }
}
