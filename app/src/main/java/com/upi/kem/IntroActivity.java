package com.upi.kem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(new SimpleSlide.Builder()
                .title("Kemampuan Efektif Membaca")
                .description(R.string.description_1)
                .image(R.drawable.ic_petunjuk)
                .background(R.color.light_blue_500)
                .scrollable(false)
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Kemampuan Efektif Membaca")
                .description(R.string.description_1)
                .image(R.drawable.ic_petunjuk)
                .background(R.color.light_blue_500)
                .scrollable(false)
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(IntroActivity.this, "test", Toast.LENGTH_SHORT).show();
                    }
                })
                .build());
        addSlide(new SimpleSlide.Builder()
                .title("Kemampuan Efektif Membaca")
                .description(R.string.description_1)
                .image(R.drawable.ic_petunjuk)
                .background(R.color.light_blue_500)
                .scrollable(false)
                .build());


    }
}
