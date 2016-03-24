package br.com.extractor.ygops.view.activity.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.dialog.ImagePicker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Muryllo Tiraza on 04/02/2016.
 */
public class ProfileRegisterActivity extends ParentActivity {

    @Bind(R.id.edtName) EditText edtName;
    @Bind(R.id.btnImage) ImageButton btnImage;

    private byte[] image = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_profile_register);
        ButterKnife.bind(this);

        btnImage.setColorFilter(getResources().getColor(R.color.primary));
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ImagePicker.getPickImageIntent(ProfileRegisterActivity.this);
                startActivityForResult(intent, ImagePicker.IMAGE_PICKER_ID);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (requestCode == ImagePicker.IMAGE_PICKER_ID && resultCode != 0) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(ProfileRegisterActivity.this, resultCode, data);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image = stream.toByteArray();
                    return null;
                }
            }.execute();
        }
    }
    @OnClick(R.id.btnDone)
    public void done(){
        if (edtName.getText() != null && !"".equals(edtName.getText().toString())) {
            Profile profile = new Profile();
            profile.setUuid(UUID.randomUUID().toString());
            profile.setNome(edtName.getText().toString());

            if (image != null) {
                profile.setImage(image);
            }

            RealmUtils.getInstance().insert(profile);

            Intent intent = new Intent(ProfileRegisterActivity.this, MainActivity.class);
            startActivity(intent);
            ProfileRegisterActivity.this.finish();
        } else {
            edtName.setError(getString(R.string.field_required));
        }
    }
}
