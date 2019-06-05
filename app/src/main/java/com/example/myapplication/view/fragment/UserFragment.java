package com.example.myapplication.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.view.activity.CouponActivity;
import com.example.myapplication.view.activity.MembershipActivity;
import com.example.myapplication.view.activity.UserNameActivity;

public class UserFragment extends Fragment implements View.OnClickListener {

    ImageView imgAvatar;
    TextView txtName, txtGuest, txtMemCode, txtCoupon, txtHistory, txtCombo, txtTaP, txtLogout;
    SharedPreferences mPreferences;
    String sharePrefFile = "com.example.myapplication";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_custom_user, container, false);

        imgAvatar = view.findViewById(R.id.imgAvatarUser);
        txtGuest = view.findViewById(R.id.txtMemberUser);
        txtName = view.findViewById(R.id.txtNameUser);
        txtMemCode = view.findViewById(R.id.txtMembershipcode);
        txtCombo = view.findViewById(R.id.txtCombo);
        txtCoupon = view.findViewById(R.id.txtCoupon);
        txtHistory = view.findViewById(R.id.txtHistory);
        txtTaP = view.findViewById(R.id.txtTermPolicy);
        txtLogout = view.findViewById(R.id.txtLogOut);

        mPreferences = getActivity().getSharedPreferences(sharePrefFile, Context.MODE_PRIVATE);
        if (mPreferences.contains("avatar")){
            Uri uri = Uri.parse(mPreferences.getString("avatar", ""));
            Glide.with(getActivity()).load(uri).into(imgAvatar);
        }
        if (mPreferences.contains("name")){
            txtName.setText(mPreferences.getString("name", ""));
        }
        if (mPreferences.contains("isVip")){
            if (mPreferences.getBoolean("isVip", true)){
                txtGuest.setText("Vip member");
            } else {
                txtGuest.setText("Basic member");
            }
        }

        txtLogout.setOnClickListener(this);
        txtCoupon.setOnClickListener(this);
        txtMemCode.setOnClickListener(this);
        txtCombo.setOnClickListener(this);
        txtHistory.setOnClickListener(this);
        txtTaP.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtLogOut:
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.remove("accessToken");
                editor.apply();
                startActivity(new Intent(getActivity(), UserNameActivity.class));
                break;
            case R.id.txtMembershipcode:
                startActivity(new Intent(getActivity(), MembershipActivity.class));
                break;
            case R.id.txtCoupon:
                startActivity(new Intent(getActivity(), CouponActivity.class));
            case R.id.txtCombo:
                break;
            case R.id.txtHistory:
                break;
            case R.id.txtTermPolicy:
                break;
        }
    }
}
