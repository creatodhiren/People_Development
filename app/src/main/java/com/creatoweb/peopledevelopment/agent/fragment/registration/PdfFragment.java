package com.creatoweb.peopledevelopment.agent.fragment.registration;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import com.creatoweb.peopledevelopment.R;
import com.creatoweb.peopledevelopment.data.AppPref;
import com.creatoweb.peopledevelopment.databinding.FragmentPdfBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PdfFragment extends Fragment {
    private Context context;
    private Activity activity;
    private ObservableInt status = new ObservableInt();

    public static final String TAG = "PdfFragment";

    private Bitmap bitmap;

    private FragmentPdfBinding binding;

    private String name = "", memberActId = "", memberAccountId = "", accountType = "", image = "", amount = "", percent = "";

    private String dateString = "", pdfName = "";

    private ProgressDialog progressDialog;

    public PdfFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPdfBinding.inflate(inflater, container, false);
        binding.setStatus(status);

        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("loading");
        progressDialog.show();

        if (getArguments() != null) {
            name = getArguments().getString("name");
            memberActId = getArguments().getString("memberActId");
            accountType = getArguments().getString("accounttype");
            amount = getArguments().getString("amount");
            percent = getArguments().getString("percent");

            Log.v("tag", accountType);

            if (accountType.equalsIgnoreCase("Saving")) {
                memberAccountId = getArguments().getString("memberAccountId");
                binding.tandcondSaving.setVisibility(View.VISIBLE);
                binding.tandcondFd.setVisibility(View.GONE);
                binding.tandcondRd.setVisibility(View.GONE);
                binding.tandcondDds.setVisibility(View.GONE);
            } else if (accountType.equalsIgnoreCase("FD (Fixed Deposit)")) {
                memberAccountId = getArguments().getString("memberAccountId");
                binding.tandcondFd.setVisibility(View.VISIBLE);
                binding.tandcondSaving.setVisibility(View.GONE);
                binding.tandcondRd.setVisibility(View.GONE);
                binding.tandcondDds.setVisibility(View.GONE);
            } else if (accountType.equalsIgnoreCase("RD (Recurring Deposit)")) {
                memberAccountId = getArguments().getString("memberAccountId");
                binding.tandcondRd.setVisibility(View.VISIBLE);
                binding.tandcondFd.setVisibility(View.GONE);
                binding.tandcondSaving.setVisibility(View.GONE);
                binding.tandcondDds.setVisibility(View.GONE);

            } else if (accountType.equalsIgnoreCase("DDS (Daily Deposit Scheme)")) {
                memberAccountId = getArguments().getString("memberAccountId");
                binding.tandcondDds.setVisibility(View.VISIBLE);
                binding.tandcondRd.setVisibility(View.GONE);
                binding.tandcondFd.setVisibility(View.GONE);
                binding.tandcondSaving.setVisibility(View.GONE);
            }

            image = getArguments().getString("image");
            if (!image.equalsIgnoreCase("")) {
                Bitmap bitmap = StringToBitMap(image);
                binding.ivUserSignature.setImageBitmap(bitmap);
            }
        }

        AppPref appPref = AppPref.getInstance(context);
        String agentId = appPref.getString(AppPref.Key.ID, "");
        String agentName = appPref.getString(AppPref.Key.NAME, "");

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        binding.tvDate.setText(dateString);

        binding.tvName.setText(name);
        binding.tvMembercode.setText(memberActId);
        binding.tvBranchcode.setText("00000001");

        binding.tvCode.setText(agentId);
        binding.tvAgentName.setText(agentName);
        binding.tvAccounttype.setText(accountType);
        binding.tvAccountcode.setText(memberAccountId);
        binding.tvAmount.setText(amount);
        binding.tvInterestpercent.setText(percent);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("size", " " + binding.getRoot().getWidth() + "  " + binding.linearlayoutPdf.getHeight());
                bitmap = loadBitmapFromView(binding.linearlayoutPdf, binding.linearlayoutPdf.getWidth(), binding.linearlayoutPdf.getHeight());
                createPdf();
            }
        }, 1000);

        return binding.getRoot();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateString = sdf.format(date);

        File directory = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/");
        File directory1 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/");
        File directory2 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Saving/");
        File directory3 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Fd (Fixed Deposit)/");
        File directory4 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Rd (recurring Deposit)/");
        File directory5 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/DDS (Daily Deposit Scheme)/");
        File directory6 = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Loan/");

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!directory1.exists()) {
            directory1.mkdir();
        }

        if (!directory2.exists()) {
            directory2.mkdir();
        }

        if (!directory3.exists()) {
            directory3.mkdir();
        }

        if (!directory4.exists()) {
            directory4.mkdir();
        }

        if (!directory5.exists()) {
            directory5.mkdir();
        }

        if (!directory6.exists()) {
            directory6.mkdir();
        }

        String targetPdf = "";

        if (accountType.equalsIgnoreCase("Saving")) {
            pdfName = name + "" + memberAccountId + ".pdf";

            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Saving/" + pdfName;


        } else if (accountType.equalsIgnoreCase("FD (Fixed Deposit)")) {
            pdfName = name + "" + memberAccountId + ".pdf";
            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Fd (Fixed Deposit)/" + pdfName;


        } else if (accountType.equalsIgnoreCase("RD (Recurring Deposit)")) {
            pdfName = name + "" + memberAccountId + ".pdf";
            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Rd (recurring Deposit)/" + pdfName;


        } else if (accountType.equalsIgnoreCase("DDS (Daily Deposit Scheme)")) {
            pdfName = name + "" + memberAccountId + ".pdf";
            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/DDS (Daily Deposit Scheme)/" + pdfName;


        } else if (accountType.equalsIgnoreCase("Loan")) {
            pdfName = name + "" + memberAccountId + ".pdf";
            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/Loan/" + pdfName;


        } else {
            pdfName = name + "" + memberActId + ".pdf";
            targetPdf = Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/" + pdfName;

        }


        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(activity, "PDF is created!!!", Toast.LENGTH_SHORT).show();
            progressDialog.hide();

            Navigation.findNavController(binding.getRoot()).navigate(R.id.dashboard);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();

//        openGeneratedPDF();
    }

    private void openGeneratedPDF() {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/People Development/" + dateString + "/" + pdfName);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(activity, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        this.activity = activity;
    }
}
