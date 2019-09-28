package com.bzu.gxs.smartrubbish;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.idtk.smallchart.chart.PieChart;
import com.idtk.smallchart.data.PieData;
import com.idtk.smallchart.interfaces.iData.IPieData;

import java.util.ArrayList;

import lecho.lib.hellocharts.view.PieChartView;

public class smallchartTest extends AppCompatActivity {
    private ArrayList<IPieData> mPieDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smallchart_test);

        initData();
        PieChart pieChart = (PieChart)findViewById(R.id.pieChart2);
        pieChart.setDataList(mPieDataList);
        pieChart.setAxisColor(Color.WHITE);
        pieChart.setAxisTextSize(pxTodp(20));

    }

    private void initData(){
        for (int i=0; i<9; i++){
            PieData pieData = new PieData();
            pieData.setName("区域"+i);
            pieData.setValue((float)i+1);
            pieData.setColor(mColors[i]);
            mPieDataList.add(pieData);
        }
    }

    private float pxTodp(float value){
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
        return valueDP;
    }

    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

}
