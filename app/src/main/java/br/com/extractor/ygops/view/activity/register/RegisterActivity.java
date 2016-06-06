package br.com.extractor.ygops.view.activity.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Constantes;
import br.com.extractor.ygops.model.FBProfile;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.FirebaseUtils;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.util.PasswordUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.dialog.ImagePicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Muryllo Tiraza on 04/02/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.txtNome) TextView txtNome;
    @Bind(R.id.txtEmail) TextView txtEmail;
    @Bind(R.id.txtSenha) TextView txtSenha;
    @Bind(R.id.imgProfile) ImageView imgProfile;
    @Bind(R.id.fab) FloatingActionButton fab;

    private byte[] image = null;

    private FirebaseAuth mAuth;
    private FirebaseStorage mStorage;
    private FirebaseDatabase mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseUtils.getFirebaseAuth();
        mStorage = FirebaseUtils.getFirebaseStorage();
        mDatabase = FirebaseUtils.getFirebaseDatabase();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ImagePicker.getPickImageIntent(RegisterActivity.this);
                startActivityForResult(intent, ImagePicker.IMAGE_PICKER_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (requestCode == ImagePicker.IMAGE_PICKER_ID && resultCode != 0) {
            new AsyncTask<Void, Void, Void>() {
                private Bitmap bitmapImage;

                @Override
                protected void onPreExecute() {
                    imgProfile.setImageBitmap(null);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(RegisterActivity.this, resultCode, data);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image = stream.toByteArray();
                    bitmapImage = ImageUtils.getInstance().getRoundedCornerBitmap(image);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    imgProfile.setImageBitmap(bitmapImage);
                }
            }.execute();
        }
    }

    @OnClick(R.id.btnRegister)
    public void registerUser() {
        if (validaCampos()) {
            String nome = txtNome.getText().toString();
            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();
            register(nome, email, senha);
        }
    }

    private void register(final String nome, final String email, final String senha) {
        final MaterialDialog progressDialog;

        progressDialog = new MaterialDialog.Builder(RegisterActivity.this)
                .title(R.string.registering)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();

        mAuth.createUserWithEmailAndPassword(email, PasswordUtils.md5(senha))
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (task.getException() != null) {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            final String id = task.getResult().getUser().getUid();

                            FBProfile fbProfile = new FBProfile();
                            fbProfile.setNome(nome);
                            fbProfile.setEmail(email);

                            DatabaseReference mRef = mDatabase.getReference().child(Constantes.USUARIOS).child(id);
                            mRef.setValue(fbProfile, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (image != null) {
                                        StorageReference mStorageRef = mStorage.getReference();
                                        StorageReference mImageRef = mStorageRef.child(id + ".png");

                                        UploadTask uploadTask = mImageRef.putBytes(image);
                                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                mAuth.signOut();
                                                progressDialog.dismiss();
                                                RegisterActivity.this.finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                mAuth.signOut();
                                                progressDialog.dismiss();
                                            }
                                        });
                                    } else {
                                        mAuth.signOut();
                                        progressDialog.dismiss();
                                        RegisterActivity.this.finish();
                                    }
                                }
                            });
                        }
                    }
                });
    }

    private Boolean validaCampos() {
        Boolean isValido = true;

        if (txtNome.getText() == null || "".equals(txtNome.getText().toString())) {
            isValido = false;
            txtNome.setError(getString(R.string.field_required));
        }

        if (txtEmail.getText() == null || "".equals(txtEmail.getText().toString())) {
            isValido = false;
            txtEmail.setError(getString(R.string.field_required));
        }

        if (txtSenha.getText() == null || "".equals(txtSenha.getText().toString())) {
            isValido = false;
            txtSenha.setError(getString(R.string.field_required));
        }

        return isValido;
    }
}
