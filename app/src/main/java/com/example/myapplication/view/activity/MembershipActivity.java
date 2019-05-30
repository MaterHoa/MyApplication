package com.example.myapplication.view.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MembershipActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtName, txtMember;
    ImageView imageView;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_membership);

        toolbar = findViewById(R.id.toolBarMember);
        txtMember = findViewById(R.id.txtMemberGuest);
        txtName = findViewById(R.id.txtNameMember);
        imageView = findViewById(R.id.imgCode);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPreferences = getSharedPreferences(sharePrefFile, MODE_PRIVATE);

        if (mPreferences.contains("name")){
            txtName.setText(mPreferences.getString("name", ""));
        }
        if (mPreferences.contains("isVip")){
            if (mPreferences.getBoolean("isVip", true)){
                txtMember.setText("Vip member");
            } else {
                txtMember.setText("Basic member");
            }
        }
        String id = mPreferences.getString("id", "");

        try {
            imageView.setImageBitmap(encodeAsBitmap(id, BarcodeFormat.QR_CODE, 800,800));
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

    static Bitmap encodeAsBitmap(String contents,
                                 BarcodeFormat format,
                                 int desiredWidth,
                                 int desiredHeight) throws WriterException {
        Hashtable<EncodeHintType,Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new Hashtable<>(2);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(contents, format, desiredWidth, desiredHeight, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static String trim(String s) {
        if (s == null) {
            return null;
        }
        s = s.trim();
        return s.length() == 0 ? null : s;
    }

}
