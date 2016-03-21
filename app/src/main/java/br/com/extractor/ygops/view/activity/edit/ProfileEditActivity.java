package br.com.extractor.ygops.view.activity.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.io.ByteArrayOutputStream;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.util.RealmUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.dialog.ImagePicker;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ProfileEditActivity extends ParentActivity {

    @Bind(R.id.txtName)
    TextView txtName;
    @Bind(R.id.imgProfile)
    ImageButton imgProfile;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private Profile profile;
    private byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_profile_edit);
        setTitle(getString(R.string.profile));
        displayHomeEnabled();
        ButterKnife.bind(this);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ImagePicker.getPickImageIntent(ProfileEditActivity.this);
                startActivityForResult(intent, ImagePicker.IMAGE_PICKER_ID);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                if ("".equals(name)) {
                    txtName.setError(getResources().getString(R.string.field_required));
                } else {
                    Profile update = new Profile();
                    update.setUuid(profile.getUuid());
                    update.setNome(name);
                    update.setDecks(profile.getDecks());
                    if (image != null) {
                        update.setImage(image);
                    }

                    RealmUtils.getInstance().update(update);

                    Intent returnIntent = new Intent();
                    setResult(0, returnIntent);
                    finish();
                }
            }
        });

        profile = RealmUtils.getInstance().get(Profile.class);
        if (profile.getImage() != null) {
            imgProfile.setImageBitmap(ImageUtils.getInstance().getRoundedCornerBitmap(profile.getImage()));
        }

        txtName.setText(profile.getNome());
        fab.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_scale_in));
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        if (requestCode == ImagePicker.IMAGE_PICKER_ID && resultCode != 0) {
            new AsyncTask<Void, Void, Void>() {
                private Bitmap roundedImage;

                @Override
                protected void onPreExecute() {
                    imgProfile.setImageBitmap(null);
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    Bitmap bitmap = ImagePicker.getImageFromResult(ProfileEditActivity.this, resultCode, data);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    image = stream.toByteArray();
                    roundedImage = ImageUtils.getInstance().getRoundedCornerBitmap(image);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    imgProfile.setImageBitmap(roundedImage);
                }
            }.execute();
        }
    }
}
