package com.example.quiz2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.quiz2.dao.TransaksiDao;
import com.example.quiz2.model.Transaksi;

@Database(entities = {Transaksi.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase instance; // Volatile variable to ensure visibility across threads

    public abstract TransaksiDao transactionDao(); // Abstract method to get the DAO

    // Singleton instance getter
    public static AppDatabase getInstance(Context context) {
        if (instance == null) { // Check if instance is null
            synchronized (AppDatabase.class) { // Synchronize to prevent multiple threads from creating multiple instances
                if (instance == null) { // Double-checked locking
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database") // Create the database
                            .fallbackToDestructiveMigration() // Handle schema changes
                            .build();
                }
            }
        }
        return instance; // Return the singleton instance
    }
}
