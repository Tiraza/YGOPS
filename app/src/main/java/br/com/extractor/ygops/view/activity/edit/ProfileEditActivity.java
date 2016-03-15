package br.com.extractor.ygops.view.activity.edit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.io.ByteArrayOutputStream;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.model.Profile;
import br.com.extractor.ygops.util.ImageUtils;
import br.com.extractor.ygops.view.ParentActivity;
import br.com.extractor.ygops.view.activity.MainActivity;
import br.com.extractor.ygops.view.dialog.ImagePicker;
import io.realm.Realm;

public class ProfileEditActivity extends ParentActivity {

    private TextView txtName;
    private ImageButton imgProfile;
    private FloatingActionButton fab;

    private Profile profile;
    private byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        txtName = getElementById(R.id.txtName);
        imgProfile = getElementById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ImagePicker.getPickImageIntent(ProfileEditActivity.this);
                startActivityForResult(intent, ImagePicker.IMAGE_PICKER_ID);
            }
        });

        fab = getElementById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                if("".equals(name)){
                    txtName.setError(getResources().getString(R.string.field_required));
                } else {
                    realm.beginTransaction();
                    profile.setNome(name);

                    if(image != null){
                        profile.setImage(image);
                    }

                    realm.commitTransaction();

                    Intent returnIntent = new Intent();
                    setResult(0, returnIntent);
                    finish();
                }
            }
        });

        realm = Realm.getDefaultInstance();
        profile = realm.where(Profile.class).findFirst();

        if (profile.getImage() != null) {
            imgProfile.setImageBitmap(ImageUtils.getRoundedCornerBitmap(profile.getImage()));
        }

        txtName.setText(profile.getNome());
        fab.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fab_scale_in));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImagePicker.IMAGE_PICKER_ID && resultCode != 0) {
            Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = stream.toByteArray();

            imgProfile.setImageBitmap(ImageUtils.getRoundedCornerBitmap(image));
        }
    }
}
