package com.haotsang.wanandroid.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewBindingFragment<VB extends ViewBinding> extends Fragment {

    @Nullable
    protected VB binding;

    protected abstract VB getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    protected abstract void observe();
    protected abstract void initView();
    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setClickable(true);
        observe();
        initView();
        initData();
    }


    @Override
    public void onDestroyView() {
        binding = null;
        super.onDestroyView();
    }

}