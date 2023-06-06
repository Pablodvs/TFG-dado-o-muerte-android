package com.example.juegodados;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView diceView;
    private int[] diceFaces = {
            R.drawable.dado1,
            R.drawable.dado2,
            R.drawable.dado3,
            R.drawable.dado4,
            R.drawable.dado5,
            R.drawable.dado6,
    };
    private Button btnNuevaPartida, btnManual;
    private int currentFaceIndex = 0;
    private TextView tvBienvenida;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBienvenida = findViewById(R.id.tvBienvenida);
        animateText();
        diceView = findViewById(R.id.diceView);
        animateDice();
        btnNuevaPartida = findViewById(R.id.btnNuevaPartida);
        btnManual = findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfView();
            }
        });
        btnNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaPartida();
            }
        });
    }

    private void pdfView() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.manual);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            Intent intent = new Intent(this, PdfManual.class);
            intent.putExtra("pdf", bytes);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void nuevaPartida() {
        Intent intent= new Intent(this, PantallaEleccion.class);
        startActivity(intent);
    }

    private void animateDice() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Cambiar a la siguiente cara de dado
                currentFaceIndex = (currentFaceIndex + 1) % diceFaces.length;
                diceView.setImageResource(diceFaces[currentFaceIndex]);

                // Iniciar la animación nuevamente después de 3 segundos
                handler.postDelayed(runnable, 3000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        diceView.startAnimation(rotateAnimation);

        // Programar la ejecución de la animación nuevamente después de 3 segundos
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                diceView.startAnimation(rotateAnimation);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Detener la ejecución de la animación al destruir la actividad
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
    private void animateText() {
        String mensaje = getString(R.string.mensaje_bienvenida);
        SpannableString spannableString = new SpannableString(mensaje);

        // Aplica un color diferente a cada letra
        for (int i = 0; i < mensaje.length(); i++) {
            ForegroundColorSpan span = new ForegroundColorSpan(Color.rgb(0,0,0));
            spannableString.setSpan(span, i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvBienvenida.setText(spannableString);
        startAnimation();
    }

    private void startAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.5f, 1.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        tvBienvenida.startAnimation(scaleAnimation);
    }

}
