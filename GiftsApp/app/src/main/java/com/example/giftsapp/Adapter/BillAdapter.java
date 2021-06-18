package com.example.giftsapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.giftsapp.Controller.BillAdmin;
import com.example.giftsapp.Controller.Fragment_Accounts.BillStatusAdmin;
import com.example.giftsapp.Controller.OrderDetailsActivity;
import com.example.giftsapp.Model.Bill;
import com.example.giftsapp.Model.BillModel;
import com.example.giftsapp.Model.Helper;
import com.example.giftsapp.Model.Revenue;
import com.example.giftsapp.Model.StatusBill;
import com.example.giftsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillAdapter extends BaseAdapter {
    private final Context context;
    private final int layout;
    private ArrayList<Bill> billList;
    private final boolean isAdmin;
    FirebaseFirestore fStore;
    private String status;
    private ArrayList<Bill> billByStatus;

    public BillAdapter(Context context, int layout, ArrayList<Bill> billList, boolean isAdmin, String status) {
        this.context = context;
        this.layout = layout;
        this.billList = billList;
        this.fStore = FirebaseFirestore.getInstance();
        this.isAdmin = isAdmin;
        this.status = status;
        billByStatus = new ArrayList<>();
        for (int i = 0; i < billList.size(); i++) {
            ArrayList<StatusBill> statusBill = billList.get(i).getStatus();
            for (int j = 0; j < statusBill.size(); j ++) {
                if (statusBill.get(j).getDone()) {
                    if (statusBill.get(j).getName().equals(status)) {
                        Bill bill = billList.get(i);
                        billByStatus.add(bill);
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return billByStatus.size();
    }

    @Override
    public Object getItem(int position) {
        return billByStatus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);

        TextView txtFirstPro = convertView.findViewById(R.id.txtFirstPro);
        TextView txtFirstPrice = convertView.findViewById(R.id.txtFirstPrice);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);
        TextView txtTotal = convertView.findViewById(R.id.txtTotal);
        TextView txtViewDetail = convertView.findViewById(R.id.txtViewDetail);
        ImageView imgSp = convertView.findViewById(R.id.imgSp);


        Bill bill = billByStatus.get(position);
        String statusPresent = "";
        Integer positionPresent = null;
        txtFirstPro.setText(bill.getProductsArrayList().get(0).getName());
        txtFirstPrice.setText(bill.getProductsArrayList().get(0).getPrice());
        for (int i = 0; i < bill.getStatus().size(); i++) {
            if (bill.getStatus().get(i).getDone()) {
                statusPresent = bill.getStatus().get(i).getName();
                positionPresent = i;
                txtStatus.setText(statusPresent);
                break;
            }
        }
        txtQuantity.setText(bill.getQuantityProduct() + " sản phẩm");
        txtTotal.setText(bill.getTotalPrice());
        Glide.with(context.getApplicationContext())
                .load(bill.getProductsArrayList().get(0).getImageUrl())
                .into(imgSp);

        if ( this.context instanceof BillAdmin ) {
            Button btnChangeStatus = convertView.findViewById(R.id.btnChangeStatus);
            TextView txtBuyerName = convertView.findViewById(R.id.txtBuyerName);
            fStore.collection("Users").document(bill.getUserID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            txtBuyerName.setText(document.getString("fullName"));
                        }
                    }
                }
            });
            String actionForButton = "Hoàn thành";
            switch (statusPresent) {
                case  "Chờ xác nhận":
                    actionForButton = "Xác nhận";
                    break;
                case "Chờ lấy hàng":
                    actionForButton = "Lấy hàng";
                    break;
                case "Đang giao hàng":
                    actionForButton = "Giao hàng";
                    break;
                default:
                    btnChangeStatus.setVisibility(View.INVISIBLE);
                    btnChangeStatus.setEnabled(false);
                    break;
            }
            btnChangeStatus.setText(actionForButton);
            String finalStatusPresent = statusPresent;
            Integer finalPositionPresent = positionPresent;
            btnChangeStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
                    HashMap<String, Object> oldStatus = new HashMap<String, Object>();
                    oldStatus.put("createAt", bill.getStatus().get(finalPositionPresent).getDate());
                    oldStatus.put("isPresent", true);
                    oldStatus.put("name", finalStatusPresent);
                    fStore.collection("Bill").document(bill.getId()).update("status", FieldValue.arrayRemove(oldStatus))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            HashMap<String, Object> updateOldStatus = new HashMap<String, Object>();
                            updateOldStatus.put("createAt", bill.getStatus().get(finalPositionPresent).getDate());
                            updateOldStatus.put("isPresent", false);
                            updateOldStatus.put("name", finalStatusPresent);
                            fStore.collection("Bill").document(bill.getId()).update("status", FieldValue.arrayUnion(updateOldStatus))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String nextStatus = "Null";
                                    switch (finalStatusPresent) {
                                        case  "Chờ xác nhận":
                                            nextStatus = "Chờ lấy hàng";
                                            break;
                                        case "Chờ lấy hàng":
                                            nextStatus = "Đang giao hàng";
                                            break;
                                        case "Đang giao hàng":
                                            nextStatus = "Đã giao hàng";
                                            break;
                                        default:
                                            break;
                                    }
                                    Date date = java.util.Calendar.getInstance().getTime();
                                    HashMap<String, Object> newStatus = new HashMap<String, Object>();
                                    newStatus.put("createAt", date);
                                    newStatus.put("isPresent", true);
                                    newStatus.put("name", nextStatus);
                                    String finalNextStatus = nextStatus;
                                    fStore.collection("Bill").document(bill.getId()).update("status", FieldValue.arrayUnion(newStatus))
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Đã chuyển trạng thái", Toast.LENGTH_SHORT).show();
                                            billList.removeIf(x -> x.getId().equals(bill.getId()));
                                            bill.getStatus().add(new StatusBill(false, finalStatusPresent, bill.getStatus().get(finalPositionPresent).getDate()));
                                            bill.getStatus().removeIf(StatusBill::getDone);
                                            bill.getStatus().add(new StatusBill(true, finalNextStatus, date));
                                            billList.add(bill);
                                            billByStatus.remove(position);
                                            BillAdmin.viewPagerAdapter.setBillArrayList(billList);
                                            BillAdmin.viewPagerAdapter.notifyDataSetChanged();
                                            notifyDataSetChanged();
                                            if (finalNextStatus.equals("Đã giao hàng")) {
                                                int year = Calendar.getInstance().get(Calendar.YEAR);
                                                fStore.collection("Revenue").whereEqualTo("year", year).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.getResult().isEmpty()) {
                                                            Revenue revenue = new Revenue("0",0);
                                                            Map<String, Object> revenueInCurrentYear = new HashMap<>();
                                                            revenueInCurrentYear.put("January", revenue);
                                                            revenueInCurrentYear.put("February", revenue);
                                                            revenueInCurrentYear.put("March", revenue);
                                                            revenueInCurrentYear.put("April", revenue);
                                                            revenueInCurrentYear.put("May", revenue);
                                                            revenueInCurrentYear.put("June", revenue);
                                                            revenueInCurrentYear.put("July", revenue);
                                                            revenueInCurrentYear.put("August", revenue);
                                                            revenueInCurrentYear.put("September", revenue);
                                                            revenueInCurrentYear.put("October", revenue);
                                                            revenueInCurrentYear.put("November", revenue);
                                                            revenueInCurrentYear.put("December", revenue);
                                                            revenueInCurrentYear.put("year", year);
                                                            fStore.collection("Revenue").add(revenueInCurrentYear)
                                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentReference documentReference) {
                                                                            Log.d("TAG", "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                        }}).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Log.w("TAG", "Error adding document", e);
                                                                }
                                                            });
                                                        } else {
                                                            String idRevenue = "";
                                                            long recentRevenue = 0;
                                                            int recentTotalBill = 0;
                                                            Map<String, Object> revenue;
                                                            String month = Helper.getMonth();
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                Log.d("TAG", document.getId() + " => " + document.getData());
                                                                revenue = (Map<String, Object>) document.get(month);
                                                                idRevenue = document.getId();
                                                                recentRevenue = Long.parseLong(revenue.get("revenue").toString());
                                                                recentTotalBill = Integer.parseInt(revenue.get("totalBill").toString());
                                                                Log.d("TAG", "doanh thu hiện tại: " + recentRevenue + "- tổng bill hiện tại: " + recentTotalBill);
                                                            }

                                                            String newRevenue = String.valueOf(recentRevenue + Long.parseLong(bill.getTotalPrice()));

                                                            int newTotalBill = recentTotalBill + 1;
                                                            Log.d("TAG", "-doanh thu mới: " + newRevenue + "-tổng bill mới: " + newTotalBill + "-tháng: " + month);
                                                            fStore.collection("Revenue").document(idRevenue).update(
                                                                    month+".revenue", newRevenue,
                                                                    month+".totalBill", newTotalBill)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            Log.d("TAG", "Đã cộng doanh thu");
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }

        txtViewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("PARCEL_BILL", bill);
                bundle.putBoolean("FROM_ADMIN", isAdmin);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        return convertView;
    }
}
