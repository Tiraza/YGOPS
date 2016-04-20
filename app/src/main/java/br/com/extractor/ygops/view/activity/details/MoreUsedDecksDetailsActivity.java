package br.com.extractor.ygops.view.activity.details;

import android.os.Bundle;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentActivity;
import butterknife.ButterKnife;

/**
 * Created by Muryllo Tiraza on 20/04/2016.
 */
public class MoreUsedDecksDetailsActivity extends ParentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_card_details);
        displayHomeEnabled();
        ButterKnife.bind(this);

    }

}
