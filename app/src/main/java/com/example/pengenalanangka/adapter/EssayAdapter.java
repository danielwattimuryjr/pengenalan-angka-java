package com.example.pengenalanangka.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pengenalanangka.fragments.essay.EssayFour;
import com.example.pengenalanangka.fragments.essay.EssayOne;
import com.example.pengenalanangka.fragments.essay.EssayThree;
import com.example.pengenalanangka.fragments.essay.EssayTwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EssayAdapter extends FragmentStateAdapter {
    private List<Integer> fragmentOrder;

    public EssayAdapter(FragmentActivity fa) {
        super(fa);
        initializeFragmentOrder();
    }

    private void initializeFragmentOrder() {
        // Inisialisasi urutan fragment secara acak
        fragmentOrder = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            fragmentOrder.add(i);
        }
        Collections.shuffle(fragmentOrder);
    }

    @Override
    public Fragment createFragment(int position) {
        int orderedPosition = fragmentOrder.get(position);
        switch (orderedPosition) {
            case 0:
                return new EssayOne();
            case 1:
                return new EssayTwo();
            case 2:
                return new EssayThree();
            case 3:
                return new EssayFour();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
