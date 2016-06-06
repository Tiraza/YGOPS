package br.com.extractor.ygops.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by muryllo.santos on 02/06/2016.
 */
public class FirebaseUtils {

    private static FirebaseAuth mAuth;
    private static FirebaseStorage mStorage;
    private static FirebaseDatabase mDatabase;

    public static FirebaseAuth getFirebaseAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }

        return mAuth;
    }

    public static FirebaseStorage getFirebaseStorage() {
        if (mStorage == null) {
            mStorage = FirebaseStorage.getInstance();
        }

        return mStorage;
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
        }

        return mDatabase;
    }
}
