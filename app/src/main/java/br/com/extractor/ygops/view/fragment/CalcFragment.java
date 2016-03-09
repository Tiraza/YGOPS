package br.com.extractor.ygops.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.extractor.ygops.R;
import br.com.extractor.ygops.view.ParentFragment;
import br.com.extractor.ygops.view.activity.MainActivity;

/**
 * Created by Muryllo Tiraza on 29/02/2016.
 */
public class CalcFragment extends ParentFragment implements View.OnClickListener{


    private TextView txtValue;
    private TextView txtLifePlayer;
    private TextView txtLifeOponnent;

    private static final Integer MAX_LENGTH = 4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateView(inflater, container, R.layout.fragment_calc);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setTitle(R.string.calculator);
        ((MainActivity) activity).toggleIconToolbar(true);

        setupTextView();
        setupButtons();
        setupCalcButtons();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_1:
                setValue("1");
                break;
            case R.id.btn_2:
                setValue("2");
                break;
            case R.id.btn_3:
                setValue("3");
                break;
            case R.id.btn_4:
                setValue("4");
                break;
            case R.id.btn_5:
                setValue("5");
                break;
            case R.id.btn_6:
                setValue("6");
                break;
            case R.id.btn_7:
                setValue("7");
                break;
            case R.id.btn_8:
                setValue("8");
                break;
            case R.id.btn_9:
                setValue("9");
                break;
            case R.id.btn_0:
                setValue("0");
                break;
            case R.id.btn_00:
                setValue("00");
                break;
            case R.id.btn_000:
                setValue("000");
                break;
            case R.id.btnClear:
                txtValue.setText("");
                break;
            case R.id.btnReset:
                txtLifePlayer.setText("8000");
                txtLifeOponnent.setText("8000");
                txtValue.setText("");
                break;
            case R.id.btnPlayerAdd:
                addValue(txtLifePlayer);
                break;
            case R.id.btnPlayerRemove:
                removeValue(txtLifePlayer);
                break;
            case R.id.btnOponnentAdd:
                addValue(txtLifeOponnent);
                break;
            case R.id.btnOponnentRemove:
                removeValue(txtLifeOponnent);
                break;
        }
    }

    private void setupTextView() {
        txtLifePlayer = getElementById(R.id.txtLifePlayer);
        txtLifeOponnent = getElementById(R.id.txtLifeOpponent);
        txtValue = getElementById(R.id.txtValue);
    }

    private void setupButtons() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.fab_scale_in);

        ImageButton btnPlayerAdd = getElementById(R.id.btnPlayerAdd);
        btnPlayerAdd.setColorFilter(getResources().getColor(R.color.match_winner));
        btnPlayerAdd.startAnimation(animation);
        btnPlayerAdd.setOnClickListener(this);

        ImageButton btnPlayerRemove = getElementById(R.id.btnPlayerRemove);
        btnPlayerRemove.setColorFilter(getResources().getColor(R.color.match_loser));
        btnPlayerRemove.startAnimation(animation);
        btnPlayerRemove.setOnClickListener(this);

        ImageButton btnOpponentAdd = getElementById(R.id.btnOponnentAdd);
        btnOpponentAdd.setColorFilter(getResources().getColor(R.color.match_winner));
        btnOpponentAdd.startAnimation(animation);
        btnOpponentAdd.setOnClickListener(this);

        ImageButton btnOponnentRemove = getElementById(R.id.btnOponnentRemove);
        btnOponnentRemove.setColorFilter(getResources().getColor(R.color.match_loser));
        btnOponnentRemove.startAnimation(animation);
        btnOponnentRemove.setOnClickListener(this);
    }

    private void setupCalcButtons(){
        Button btn_1 = getElementById(R.id.btn_1);
        btn_1.setOnClickListener(this);

        Button btn_2 = getElementById(R.id.btn_2);
        btn_2.setOnClickListener(this);

        Button btn_3 = getElementById(R.id.btn_3);
        btn_3.setOnClickListener(this);

        Button btn_4 = getElementById(R.id.btn_4);
        btn_4.setOnClickListener(this);

        Button btn_5 = getElementById(R.id.btn_5);
        btn_5.setOnClickListener(this);

        Button btn_6 = getElementById(R.id.btn_6);
        btn_6.setOnClickListener(this);

        Button btn_7 = getElementById(R.id.btn_7);
        btn_7.setOnClickListener(this);

        Button btn_8 = getElementById(R.id.btn_8);
        btn_8.setOnClickListener(this);

        Button btn_9 = getElementById(R.id.btn_9);
        btn_9.setOnClickListener(this);

        Button btn_0 = getElementById(R.id.btn_0);
        btn_0.setOnClickListener(this);

        Button btn_00 = getElementById(R.id.btn_00);
        btn_00.setOnClickListener(this);

        Button btn_000 = getElementById(R.id.btn_000);
        btn_000.setOnClickListener(this);

        Button btnClear = getElementById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        Button btnReset = getElementById(R.id.btnReset);
        btnReset.setOnClickListener(this);
    }

    private void setValue(String value) {
        String valueTxt = txtValue.getText().toString();
        if(valueTxt.length() <= MAX_LENGTH) {
            if (value.contains("0") && !"".equals(valueTxt)) {
                valueTxt = valueTxt + value;
            } else if (!value.contains("0")) {
                valueTxt = valueTxt + value;
            }
        }

        if(valueTxt.length() > MAX_LENGTH){
            valueTxt = valueTxt.substring(0, 5);
        }

        txtValue.setText(valueTxt);
    }

    private void addValue(TextView txt){
        if(!"".equals(txtValue.getText().toString())){
            Integer val1 = Integer.valueOf(txtValue.getText().toString());
            Integer val2 = Integer.valueOf(txt.getText().toString());
            Integer result = val1 + val2;
            txt.setText(getStringResult(result));
            txtValue.setText("");
        }
    }

    private void removeValue(TextView txt){
        if(!"".equals(txtValue.getText().toString())){
            Integer val1 = Integer.valueOf(txtValue.getText().toString());
            Integer val2 = Integer.valueOf(txt.getText().toString());
            Integer result = val2 - val1;
            txt.setText(getStringResult(result));

            if(result < 0){
                txt.setText("0000");
            }

            txtValue.setText("");
        }
    }

    private String getStringResult(Integer value){
        String valueResult = "" + value;
        while (valueResult.length() != 4){
            valueResult = "0" + valueResult;
        }
        return valueResult;
    }

}
