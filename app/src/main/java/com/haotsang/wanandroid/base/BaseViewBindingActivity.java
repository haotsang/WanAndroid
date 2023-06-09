package com.haotsang.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewBindingActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;

    protected abstract VB getViewBinding(@NonNull LayoutInflater inflater);

    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }


}