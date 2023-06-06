package com.example.juegodados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;


import com.github.barteksc.pdfviewer.PDFView;

public class PdfManual extends AppCompatActivity {
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_manual);
        byte[] pdf =  getIntent().getByteArrayExtra("pdf");
        pdfView = findViewById(R.id.pdfView);
        pdfView.fromBytes(pdf).load();
        init();

    }
    private void init(){
        Toolbar toolbar = this.findViewById(R.id.toolbar);
//        toolbar.setOnClickListener(v->{
//            this.finish();
//        });
    }
}