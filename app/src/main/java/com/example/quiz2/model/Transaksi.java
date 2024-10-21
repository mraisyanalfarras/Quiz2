package com.example.quiz2.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "Transaksi") // Ensure the table name matches
public class Transaksi implements Serializable {
    @PrimaryKey(autoGenerate = true) // Use auto-increment for the primary key
    private int id; // Ensure you have an ID field as a primary key
    private String date;
    private int amount;
    private String description;
    private boolean isIncome; // To distinguish between income and expense

    public Transaksi(String date, int amount, String description, boolean isIncome) {
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.isIncome = isIncome;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    // Getters and Setters
    public int getId() {
        return id; // ID sebagai int
    }

    public void setId(int id) {
        this.id = id; // Pastikan ID diset dengan benar
    }

    public String getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIncome() {
        return isIncome;
    }

    @Override
    public String toString() {
        return "Transaksi{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", isIncome=" + isIncome +
                '}';
    }
}