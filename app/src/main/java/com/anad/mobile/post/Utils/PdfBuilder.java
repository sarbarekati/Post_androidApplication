package com.anad.mobile.post.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anad.mobile.post.Adapter.PdfPointAdapter;
import com.anad.mobile.post.Models.MiddlePoint;
import com.anad.mobile.post.Models.RFID;
import com.anad.mobile.post.Models.ReportCreator;
import com.anad.mobile.post.R;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class PdfBuilder extends AppCompatActivity {

    ScrollView root;
    TextView txtDate,txtNumber,txtPelak,txtDriverName,txtPath;
    TextView txtMabadaDate,txtMabdaTime,txtMabdaRunDate,txtMabdaRunMogharar,txtMabdaRunTime,txtMabdaTakhir,txtMabdaTajil;

    TextView txtMaghsadDate,txtMaghsadMogharar,txtMaghsadRun,txtMaghsadTakhir,txtMaghsadTajil;
    TextView txtMaghsadOpenDoorDate,txtMaghsadOpenDoorTime;
    TextView txtMaghsadMarsolatTime;

    TextView txtTeyMasirLength,txtTeyMasirAvgSpeed,txtTeyMasirMaxSpeed,txtTeyMasirMoghrar,txtTeyMasirTime,txtTeyMasirTakhir,txtTeyMasirTajil;
    TextView txtReportTitle;


    private static final String TAG = "PdfBuilder";
    static boolean hasPermission;
    private  int position;
    private static final String EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String READ_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_CODE = 200;
    RecyclerView rc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        txtReportTitle = findViewById(R.id.report_title);


        //REPORT INFO

        txtDate = findViewById(R.id.report_date_data);
        txtDriverName = findViewById(R.id.report_driver_name_data);
        txtPath = findViewById(R.id.report_path_data);
        txtPelak = findViewById(R.id.report_pelak_data);
        txtNumber = findViewById(R.id.report_number_data);

        //MABDA DATE TIME INFO

        txtMabadaDate = findViewById(R.id.report_mabda_date);
        txtMabdaTime = findViewById(R.id.report_mabda_time);
        txtMabdaRunDate = findViewById(R.id.mabda_in_out_date_data);
        txtMabdaRunMogharar = findViewById(R.id.mabda_in_out_mogharar_data);
        txtMabdaRunTime = findViewById(R.id.mabda_in_out_run_data);
        txtMabdaTakhir = findViewById(R.id.mabda_in_out_takhir_data);
        txtMabdaTajil = findViewById(R.id.mabda_in_out_tajil_data);

        //MAGHSAD DATE TIME INFO

        txtMaghsadDate = findViewById(R.id.maghsad_in_out_date_data);
        txtMaghsadMogharar = findViewById(R.id.maghsad_in_out_mogharar_data);
        txtMaghsadRun = findViewById(R.id.maghsad_in_out_run_data);
        txtMaghsadTakhir = findViewById(R.id.maghsad_in_out_takhir_data);
        txtMaghsadTajil = findViewById(R.id.maghsad_in_out_tajil_data);

        //OPEN DOOR INFO

        txtMaghsadOpenDoorDate = findViewById(R.id.maghsad_open_date_data);
        txtMaghsadOpenDoorTime = findViewById(R.id.maghsad_open_time_data);

        //MARSOLAT INFO
        txtMaghsadMarsolatTime = findViewById(R.id.maghsad_marsolat_time_data);

        //TEY MASIR DATE TIME INFO

        txtTeyMasirLength = findViewById(R.id.tey_masir_length_data);
        txtTeyMasirAvgSpeed = findViewById(R.id.tey_masir_avg_speed_date);
        txtTeyMasirMaxSpeed = findViewById(R.id.tey_masir_max_speed_data);

        txtTeyMasirMoghrar = findViewById(R.id.tey_masir_mogharar_data);
        txtTeyMasirTime = findViewById(R.id.tey_masir_time_data);
        txtTeyMasirTakhir = findViewById(R.id.tey_masir_takhir_data);
        txtTeyMasirTajil = findViewById(R.id.tey_masir_tajil_data);



        rc = findViewById(R.id.rc_pdf_mobadele_point);
        rc.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Bundle b = getIntent().getExtras();
        if(b != null && b.containsKey("PERMISSION"))
        {
            hasPermission = b.getBoolean("PERMISSION");
            position = b.getInt("ID");
        }

        if(b!=null && b.containsKey("PDF_INFO")){
            Gson gson = new Gson();
            ReportCreator report =gson.fromJson(b.getString("PDF_INFO"),ReportCreator.class);
            txtDate.setText(report.getRDate());
            txtDriverName.setText(report.getDriver_Name());
            txtPath.setText(report.getRoute_Name());
            txtPelak.setText(report.getPelak());
            txtNumber.setText(report.getVeh_ID()+"");

            txtMabadaDate.setText(report.getBargiriDate());
            txtMabdaTime.setText(report.getModateBargiriMabda());

            txtMabdaRunDate.setText(report.getMabDate());
            txtMabdaRunTime.setText(report.getMabdaKhoroj());
            txtMabdaRunMogharar.setText(report.getMabdaMogharar());
            txtMabdaTakhir.setText(report.getMabdaTakhir());
            txtMabdaTajil.setText(report.getMabdaTajil());


            txtMaghsadDate.setText(report.getMagDate());
            txtMaghsadMogharar.setText(report.getMagsadMogharar());
            txtMaghsadRun.setText(report.getMagsadVorod());
            txtMaghsadTakhir.setText(report.getMagsadTakhir());
            txtMaghsadTajil.setText(report.getMagsadTajil());

            txtMaghsadOpenDoorDate.setText(report.getBazgoshaiDate());
            txtMaghsadOpenDoorTime.setText(report.getSaatBazgoshyMagsad());

            txtMaghsadMarsolatTime.setText(report.getMagsadMobadele());


            txtTeyMasirLength.setText(report.getLen()+"");
            txtTeyMasirAvgSpeed.setText(report.getSpd_Avg()+"");
            txtTeyMasirMaxSpeed.setText(report.getSpd_Max()+"");
            txtTeyMasirMoghrar.setText(report.getZamaneMoghararTey());
            txtTeyMasirTime.setText(report.getZamaneTey());
            txtTeyMasirTakhir.setText(report.getTeyTakhir());
            txtTeyMasirTajil.setText(report.getTeyTajil());



            if(b.containsKey("RFID_DETAIL") && b.getString("RFID_DETAIL").equals(""))
            {
                txtReportTitle.setText("گزارش رهسپاری");
                List<MiddlePoint> mPoints = new ArrayList<>();
                for (int i = 1; i <=5 ; i++) {
                    MiddlePoint m = new MiddlePoint();
                    switch (i)
                    {
                        case 1:
                            if(!report.getNoghteMobadele1().equals("") && report.getNoghteMobadele1() != null){
                            m.setPName(report.getNoghteMobadele1());
                            m.setDate(report.getMobadeleDate1());
                            m.setExchageT(report.getModateMobadele1());
                            m.setPSEnT(report.getSaatMoghararVorod1());
                            m.setEnT(report.getSaatVorod1());
                            m.setPSExT(report.getSaatMoghararKhoroj1());
                            m.setExT(report.getSaatKhoroj1());
                            m.setDelayEnT(report.getTakhirVorod1());
                            m.setAccEnT(report.getTajilvorod1());
                            m.setDelayExT(report.getTakhireKhoroj1());
                            m.setAccExT(report.getTajilKhoroj1());
                                mPoints.add(m);
                            }
                            break;
                        case 2:
                            if(!report.getNoghteMobadele2().equals("") && report.getNoghteMobadele2() != null){
                            m.setPName(report.getNoghteMobadele2());
                            m.setDate(report.getMobadeleDate2());
                            m.setExchageT(report.getModateMobadele2());
                            m.setPSEnT(report.getSaatMoghararVorod2());
                            m.setEnT(report.getSaatVorod2());
                            m.setPSExT(report.getSaatMoghararKhoroj2());
                            m.setExT(report.getSaatKhoroj2());
                            m.setDelayEnT(report.getTakhirVorod2());
                            m.setAccEnT(report.getTajilvorod2());
                            m.setDelayExT(report.getTakhireKhoroj2());
                            m.setAccExT(report.getTajilKhoroj2());
                                mPoints.add(m);
                            }
                            break;
                        case 3:
                            if(!report.getNoghteMobadele3().equals("") && report.getNoghteMobadele3() != null){
                            m.setPName(report.getNoghteMobadele3());
                            m.setDate(report.getMobadeleDate3());
                            m.setExchageT(report.getModateMobadele3());
                            m.setPSEnT(report.getSaatMoghararVorod3());
                            m.setEnT(report.getSaatVorod3());
                            m.setPSExT(report.getSaatMoghararKhoroj3());
                            m.setExT(report.getSaatKhoroj3());
                            m.setDelayEnT(report.getTakhirVorod3());
                            m.setAccEnT(report.getTajilvorod3());
                            m.setDelayExT(report.getTakhireKhoroj3());
                            m.setAccExT(report.getTajilKhoroj3());
                            mPoints.add(m);
                            }
                            break;
                        case 4:
                            if(!report.getNoghteMobadele4().equals("") && report.getNoghteMobadele4() != null) {
                                m.setPName(report.getNoghteMobadele4());
                                m.setDate(report.getMobadeleDate4());
                                m.setExchageT(report.getModateMobadele4());
                                m.setPSEnT(report.getSaatMoghararVorod4());
                                m.setEnT(report.getSaatVorod4());
                                m.setPSExT(report.getSaatMoghararKhoroj4());
                                m.setExT(report.getSaatKhoroj4());
                                m.setDelayEnT(report.getTakhirVorod4());
                                m.setAccEnT(report.getTajilvorod4());
                                m.setDelayExT(report.getTakhireKhoroj4());
                                m.setAccExT(report.getTajilKhoroj4());
                                mPoints.add(m);
                            }
                            break;
                        case 5:
                            if(!report.getNoghteMobadele5().equals("") && report.getNoghteMobadele5() != null) {
                                m.setPName(report.getNoghteMobadele5());
                                m.setDate(report.getMobadeleDate5());
                                m.setExchageT(report.getModateMobadele5());
                                m.setPSEnT(report.getSaatMoghararVorod5());
                                m.setEnT(report.getSaatVorod5());
                                m.setPSExT(report.getSaatMoghararKhoroj5());
                                m.setExT(report.getSaatKhoroj5());
                                m.setDelayEnT(report.getTakhirVorod5());
                                m.setAccEnT(report.getTajilvorod5());
                                m.setDelayExT(report.getTakhireKhoroj5());
                                m.setAccExT(report.getTajilKhoroj5());
                                mPoints.add(m);
                            }
                            break;
                    }

                }

                PdfPointAdapter adapter = new PdfPointAdapter(this,mPoints);
                rc.setAdapter(adapter);

            }
            else{
                List<MiddlePoint> mRfidPoints = new ArrayList<>();
                txtReportTitle.setText("گزارش پیمانکاری");

                RFID rfidObj = gson.fromJson(b.getString("RFID_DETAIL"), RFID.class);

                if(rfidObj.getMiddle() != null && rfidObj.getMiddle().size()>0)
                {
                    mRfidPoints.addAll(rfidObj.getMiddle());
                }

                PdfPointAdapter rfidAdapter = new PdfPointAdapter(this,mRfidPoints);
                rc.setAdapter(rfidAdapter);

            }

        }

        root = findViewById(R.id.scroll_root);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                getStoragePermission(root);

            }
        });
    }

    private void createPdf(ScrollView root) {


        View view = root.getChildAt(0);

        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();

        Bitmap bitmap = getBitmapFromView(root, height, width);


        try {
            File saveFolder = new File(Environment.getExternalStorageDirectory().getPath() + "/Post_document");
            if (!saveFolder.exists())
                saveFolder.mkdirs();
            String fileName = saveFolder + "/" + "report_"+position+".jpg";
            String filePdf = saveFolder + "/" + "report_"+position+".pdf";
            File file = new File(fileName);
          final  File filePdfFolder = new File(filePdf);

            FileOutputStream output = new FileOutputStream(file);
            OutputStream outputPdf = new FileOutputStream(filePdfFolder);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);


            Document doc = new Document();
            PdfWriter.getInstance(doc, outputPdf);
            doc.open();
            Image image = Image.getInstance(fileName);
            float scalerY = ((doc.getPageSize().getHeight() - doc.bottomMargin()
                    - doc.topMargin() - 0) / image.getHeight()) * 100;

            float scalerX = ((doc.getPageSize().getWidth() - doc.leftMargin()
                    - doc.rightMargin() - 0) / image.getWidth()) * 100;
            image.scalePercent(scalerX,scalerY);
            image.setAlignment(Image.ALIGN_TOP);

            doc.add(image);
            doc.close();



                    Uri uri;
                    if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                    {
                        uri = FileProvider.getUriForFile(PdfBuilder.this,"com.anad.mobile.post.provider",filePdfFolder);
                    }
                    else
                    {
                        uri = Uri.fromFile(filePdfFolder);
                    }

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_STREAM,uri);
                    i.setType("text/plain");
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);


            output.flush();
            output.close();
            outputPdf.flush();
            outputPdf.close();
            Log.i(TAG, "createPdf: file created");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "createPdf: have error ", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "createPdf: have error ", e);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    private void getStoragePermission(ScrollView root) {
        String[] permissions = {EXTERNAL_STORAGE, READ_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, READ_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                createPdf(root);

            } else {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
               createPdf(root);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
