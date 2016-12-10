package com.example.nicolas.spotifyshare;

import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by Nicolas on 9/21/2016.
 */
public class FragmentUtils {

    public static void replaceFragment(@NonNull AppCompatActivity context, @IdRes int replacementId,
                                       @NonNull Fragment replacement, @AnimRes int animIn, @AnimRes int animOut) {
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for(Fragment fragment : fragments) {
               if (fragment != null && fragment.getClass().toString().equals(replacement.getClass().toString())){
                   return;
               }
            }
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(animIn, animOut);
        fragmentTransaction.replace(replacementId, replacement);
        fragmentTransaction.commit();
    }

}
