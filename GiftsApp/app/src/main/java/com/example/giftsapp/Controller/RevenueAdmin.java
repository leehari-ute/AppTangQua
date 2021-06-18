package com.example.giftsapp.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giftsapp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevenueAdmin extends AppCompatActivity {
    LineChart lineChart;
    TextView txtTongdonhang,txtTongTien;
    ArrayList<String> Tongtien;
    ListView lstTongtien;
    ArrayAdapter arrayAdapter;
    int donhang=0;
    int tongtien=0;
    int t1 = 0;
    int t2= 0;
    int t3= 0;
    int t4= 0;
    int t5= 0;
    int t6= 0;
    int t7= 0;
    int t8= 0;
    int t9=0;
    int t10=0;
    int t11=0;
    int t12=0;

    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_admin);
        AnhXa();

    }
    private void AnhXa()
    {
        lstTongtien=findViewById(R.id.lsttongtien);
        Tongtien=new ArrayList<>();
        arrayAdapter= new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,Tongtien);
        lstTongtien.setAdapter(arrayAdapter);

        lineChart=findViewById(R.id.linechart);
        txtTongdonhang=findViewById(R.id.txtTongDonhang);
        txtTongTien=findViewById(R.id.txtTongtienDoanhthu);
        Revenue();
    }
    private void Revenue(){
        DocumentReference docRef = fStore.collection("Revenue").document("eeVpcXp8mtfmBHQrSevX");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String revenue="revenue";
                        String totalBill="totalBill";
                        Map<String,Object> thang4 = (Map<String, Object>) document.get("April");
                        Map<String,Object> thang5 = (Map<String, Object>) document.get("May");
                        Map<String,Object> thang6 = (Map<String, Object>) document.get("June");
                        Map<String,Object> thang7 = (Map<String, Object>) document.get("July");
                        Map<String,Object> thang8 = (Map<String, Object>) document.get("August");
                        Map<String,Object> thang9 = (Map<String, Object>) document.get("September");
                        Map<String,Object> thang10 = (Map<String, Object>) document.get("October");
                        Map<String,Object> thang11 = (Map<String, Object>) document.get("November");
                        Map<String,Object> thang12 = (Map<String, Object>) document.get("December");
                        Tongtien.add(thang4.get(totalBill).toString());
                        Tongtien.add(thang5.get(totalBill).toString());
                        Tongtien.add(thang6.get(totalBill).toString());
                        Tongtien.add(thang7.get(totalBill).toString());
                        Tongtien.add(thang8.get(totalBill).toString());
                        Tongtien.add(thang9.get(totalBill).toString());
                        Tongtien.add(thang10.get(totalBill).toString());
                        Tongtien.add(thang11.get(totalBill).toString());
                        Tongtien.add(thang12.get(totalBill).toString());
                        arrayAdapter.notifyDataSetChanged();

                        t4=Integer.parseInt(thang4.get(revenue).toString());
                        t5=Integer.parseInt(thang5.get(revenue).toString());
                        t6=Integer.parseInt(thang6.get(revenue).toString());
                        t7=Integer.parseInt(thang7.get(revenue).toString());
                        t8=Integer.parseInt(thang8.get(revenue).toString());
                        t9=Integer.parseInt(thang9.get(revenue).toString());
                        t10=Integer.parseInt(thang10.get(revenue).toString());
                        t11=Integer.parseInt(thang11.get(revenue).toString());
                        t12=Integer.parseInt(thang12.get(revenue).toString());

                        tongtien=t4+t5+t6+t7+t8+t9+t10+t11+t12;
                        txtTongTien.setText(tongtien+"");

                        t4=Integer.parseInt(thang4.get(totalBill).toString());
                        t5=Integer.parseInt(thang5.get(totalBill).toString());
                        t6=Integer.parseInt(thang6.get(totalBill).toString());
                        t7=Integer.parseInt(thang7.get(totalBill).toString());
                        t8=Integer.parseInt(thang8.get(totalBill).toString());
                        t9=Integer.parseInt(thang9.get(totalBill).toString());
                        t10=Integer.parseInt(thang10.get(totalBill).toString());
                        t11=Integer.parseInt(thang11.get(totalBill).toString());
                        t12=Integer.parseInt(thang12.get(totalBill).toString());
                        tongtien=t4+t5+t6+t7+t8+t9+t10+t11+t12;
                        txtTongdonhang.setText(tongtien+"");
                        lineChart.setDragEnabled(true);
                        lineChart.setScaleEnabled(false);
                        LimitLine upper_limit=new LimitLine(65f,"Danger");//tạo 1 line trên biểu đồ
                        upper_limit.setLineWidth(4f);
                        upper_limit.enableDashedLine(10f,10f,0f);
                        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        upper_limit.setTextSize(15f);

                        LimitLine lower_limit=new LimitLine(35f,"Too Low");
                        lower_limit.setLineWidth(2f);
                        lower_limit.enableDashedLine(5f,5f,0f);
                        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                        lower_limit.setTextSize(15f);

                        YAxis leftAxis=lineChart.getAxisLeft(); //tạo các line ẩn
                        leftAxis.removeAllLimitLines();
                        leftAxis.addLimitLine(upper_limit);
                        leftAxis.addLimitLine(lower_limit);
                        leftAxis.setAxisMaximum(100f);
                        leftAxis.setAxisMinimum(0f);
                        leftAxis.enableGridDashedLine(10f,10f,0);
                        leftAxis.setDrawLimitLinesBehindData(true);
                        lineChart.getAxisRight().setEnabled(false);
                        // xóa 1 cạnh phải đồ thị
                        float th4=(t4)*100/50;
                        float th5=(t5)*100/50;
                        float th6=(t6)*100/50;
                        float th7=(t7)*100/50;
                        float th8=(t8)*100/50;
                        float th9=(t9)*100/50;
                        float th10=(t10)*100/50;
                        float th11=(t11)*100/50;
                        float th12=(t12)*100/50;

                        Log.d("aaaaaa",t4+"");
                        ArrayList<Entry> yValue= new ArrayList<>();

                        yValue.add(new Entry(0,th4));
                        yValue.add(new Entry(1,th5));
                        yValue.add(new Entry(2,th6));
                        yValue.add(new Entry(3,th7));
                        yValue.add(new Entry(4,th8));
                        yValue.add(new Entry(5,th9));
                        yValue.add(new Entry(6,th10));
                        yValue.add(new Entry(7,th11));
                        yValue.add(new Entry(8,th12));


                        LineDataSet set1 = new LineDataSet(yValue,"Biểu đồ doanh thu 2021");

                        set1.setFillAlpha(110);

                        set1.setColor(Color.RED);
                        set1.setLineWidth(3f);
                        set1.setValueTextColor(Color.BLUE);

                        ArrayList<ILineDataSet> dataSets= new ArrayList<>();
                        dataSets.add(set1);

                        LineData data= new LineData(set1);

                        lineChart.setData(data);

                        String[] Values={"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};//tạo tên cho côt

                        XAxis xAxis=lineChart.getXAxis();
                        xAxis.setValueFormatter(new MyAxisValueFormatter(Values));
                        xAxis.setGranularity(0.5f);
                        xAxis.setTextSize(0.2f);
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        lineChart.notifyDataSetChanged();
                        lineChart.invalidate();
                    } else {
                        lineChart.invalidate();
                    }
                }
            }
        });
    }

    public class MyAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;
        public MyAxisValueFormatter(String[] mValues){
            this.mValues=mValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    public void onChartLongPressed(MotionEvent me) {

    }

    public void onChartDoubleTapped(MotionEvent me) {

    }

    public void onChartSingleTapped(MotionEvent me) {

    }

    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    public void onValueSelected(Entry e, Highlight h) {

    }

    public void onNothingSelected() {

    }
}