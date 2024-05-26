package com.example.pengenalanangka.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pengenalanangka.fragments.multiple_choice.MultipleChoiceFour;
import com.example.pengenalanangka.fragments.multiple_choice.MultipleChoiceOne;
import com.example.pengenalanangka.fragments.multiple_choice.MultipleChoiceThree;
import com.example.pengenalanangka.fragments.multiple_choice.MultipleChoiceTwo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultipleChoiceAdapter extends FragmentStateAdapter {
    private List<Integer> fragmentOrder;

    public MultipleChoiceAdapter(FragmentActivity fa) {
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
                return new MultipleChoiceOne();
            case 1:
                return new MultipleChoiceTwo();
            case 2:
                return new MultipleChoiceThree();
            case 3:
                return new MultipleChoiceFour();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
