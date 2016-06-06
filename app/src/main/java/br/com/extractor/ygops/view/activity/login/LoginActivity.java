package br.com.extractor.ygops.view.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Constantes;
import br.com.extractor.ygops.model.FBProfile;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.FirebaseUtils;
import br.com.extractor.ygops.util.PasswordUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.activity.register.RegisterActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Muryllo Tiraza on 01/06/2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.edtEmail) EditText edtEmail;
    @Bind(R.id.edtSenha) EditText edtSenha;

    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseUtils.getFirebaseAuth();
        mStorage = FirebaseUtils.getFirebaseStorage();
        mDatabase = FirebaseUtils.getFirebaseDatabase();

        if (mAuth.getCurrentUser() != null) {
            redirecionaMainActicity();
        }
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        if (validaCampos()) {
            logar(edtEmail.getText().toString(), edtSenha.getText().toString());
        }
    }

    @OnClick(R.id.btnRegister)
    public void newRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.txtRecover)
    public void recover() {

    }

    private void logar(final String email, final String senha) {
        final MaterialDialog progressDialog;

        progressDialog = new MaterialDialog.Builder(LoginActivity.this)
                .title(R.string.authenticating)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();


        mAuth.signInWithEmailAndPassword(email, PasswordUtils.md5(senha))
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String idUsuario = task.getResult().getUser().getUid();

                            StorageReference mStorageRef = mStorage.getReference();
                            StorageReference mImageRef = mStorageRef.child(idUsuario + ".png");
                            mImageRef.getBytes(Constantes.UM_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    salvaUsuario(idUsuario, bytes);
                                    progressDialog.dismiss();
                                    redirecionaMainActicity();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    salvaUsuario(idUsuario, null);
                                    progressDialog.dismiss();
                                    redirecionaMainActicity();
                                }
                            });
                        } else {
                            progressDialog.dismiss();
                            if (task.getException() != null) {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void salvaUsuario(final String idUsuario, final byte[] image) {
        mDatabase.getReference().child(Constantes.USUARIOS).child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FBProfile fbProfile = dataSnapshot.getValue(FBProfile.class);
                Profile profileInsert = new Profile();
                profileInsert.setNome(fbProfile.getNome());
                profileInsert.setUuid(UUID.randomUUID().toString());

                if (image != null) {
                    profileInsert.setImage(image);
                }

                RealmUtils.getInstance().insert(profileInsert);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(Constantes.LOG_TAG, "Erro ao buscar usuarios", databaseError.toException());
            }
        });
    }

    private void redirecionaMainActicity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private Boolean validaCampos() {
        Boolean isValido = true;

        if ("".equals(edtEmail.getText().toString())) {
            isValido = false;
            edtEmail.setError(getString(R.string.field_required));
        }

        if ("".equals(edtSenha.getText().toString())) {
            isValido = false;
            edtSenha.setError(getString(R.string.field_required));
        }

        return isValido;
    }
}
