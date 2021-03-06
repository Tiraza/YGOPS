package br.com.extractor.ygops.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.android.gms.analytics.GoogleAnalytics;

import br.com.extractor.ygops.R;

/**
 * Created by Muryllo Tiraza on 27/01/2016.
 */
public abstract class ParentActivity extends AppCompatActivity {

    private Toast toast;
    protected Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
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

    protected final void displayHomeEnabled() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
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
