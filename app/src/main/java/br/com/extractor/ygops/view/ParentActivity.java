package br.com.extractor.ygops.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.PercentFormatter;

import br.com.extractor.ygops.R;
import io.realm.Realm;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentActivity extends ActionBarActivity {

    private Toast toast;
    protected Toolbar toolbar;
    protected Realm realm;

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = getElementById(R.id.tb_main);
        setSupportActionBar(toolbar);
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onPause() {
        realm.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        realm = Realm.getDefaultInstance();
        super.onResume();
    }

    protected final <T> T getElementById(int id) {
        return (T) findViewById(id);
    }

    protected final void makeToast(int idMensagem, int duracao) {
        makeToast(getString(idMensagem), duracao);
    }

    protected final void makeToast(String mensagem, int duracao) {
        if (toast != null)
            toast.cancel();

        toast = Toast.makeText(this, mensagem, duracao);
        toast.setText(mensagem);
        toast.setDuration(duracao);
        toast.show();
    }

    protected void setup(Chart<?> chart) {
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");
        chart.setTouchEnabled(true);

        if (chart instanceof BarLineChartBase) {

            BarLineChartBase mChart = (BarLineChartBase) chart;
            mChart.setDrawGridBackground(false);
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setPinchZoom(false);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines();
            leftAxis.setStartAtZero(false);
            leftAxis.setTextSize(8f);
            leftAxis.setTextColor(Color.DKGRAY);
            leftAxis.setValueFormatter(new PercentFormatter());

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(8f);
            xAxis.setTextColor(Color.DKGRAY);

            mChart.getAxisRight().setEnabled(false);
        }
    }
}
