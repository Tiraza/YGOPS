package br.com.extractor.ygops.view.activity.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
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

    @Bind(R.id.txtName) TextView txtName;
    @Bind(R.id.imgProfile) ImageView imgProfile;
    @Bind(R.id.fab) FloatingActionButton fab;

    private byte[] image = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_profile_edit);
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
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
                private Bitmap bitmapImage;

                @Override
                protected void onPreExecute() {
                    imgProfile.setImageBitmap(null);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(ProfileRegisterActivity.this, resultCode, data);
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
    @OnClick(R.id.btnDone)
    public void done(){
        if (txtName.getText() != null && !"".equals(txtName.getText().toString())) {
            Profile profile = new Profile();
            profile.setUuid(UUID.randomUUID().toString());
            profile.setNome(txtName.getText().toString());

            if (image != null) {
                profile.setImage(image);
            }

            RealmUtils.getInstance().insert(profile);

            Intent intent = new Intent(ProfileRegisterActivity.this, MainActivity.class);
            startActivity(intent);
            ProfileRegisterActivity.this.finish();
        } else {
            txtName.setError(getString(R.string.field_required));
        }
    }
}
