package com.thequizapp.quizalong.adapter;

import com.thequizapp.quizalong.model.payment.TransactionResponse;
import com.thequizapp.quizalong.view.payment.AllTransactionFragment;
import com.thequizapp.quizalong.view.payment.PaymentsFragment;
import com.thequizapp.quizalong.view.payment.WinningsFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TransactionHistoryViewPagerAdapter extends FragmentPagerAdapter {
    private TransactionResponse transactionResponse = new TransactionResponse();

    public TransactionHistoryViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new PaymentsFragment(transactionResponse.getHistory().get(position));
            case 2:
                return new WinningsFragment(transactionResponse.getHistory().get(position));
            case 0:
            default:
                return new AllTransactionFragment(transactionResponse.getHistory().get(position));
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "All";
        }
        else if (position == 1)
        {
            title = "Payments";
        }
        else
        {
            title = "Winnings";
        }

        return title;
    }

    public void setData(TransactionResponse transactionResponse){
        this.transactionResponse = transactionResponse;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
