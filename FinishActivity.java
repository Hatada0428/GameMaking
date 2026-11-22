package com.example.quiz_typingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top,
                    systemBars.right, systemBars.bottom);
            return insets;
        });

        int correctCount = getIntent().getIntExtra("correctCount", 0);
        int totalCount = getIntent().getIntExtra("totalCount", 15);


        double accuracy = getIntent().getDoubleExtra("accuracy", 0);
        long elapsedTime = getIntent().getLongExtra("elapsedTime", 0);


        int percentage = correctCount * 100 / totalCount;


        long seconds = elapsedTime / 1000;


        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;


        TextView resultText = findViewById(R.id.resultText);

        resultText.setText(
                "最終結果\n\n"
                        + "正解数：" + correctCount + "問\n"
                        + "不正解数：" + (totalCount - correctCount) + "問\n"
                        + "正答率：" + percentage + "%\n\n"
                        + "キー入力正確率：" + String.format("%.1f", accuracy) + "%\n"
                        + "入力時間：" + minutes + "分" + remainingSeconds + "秒"
        );
        Button btnBack = (Button) findViewById(R.id.button_Back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName("com.example.quiz_typingapplication","com.example.quiz_typingapplication.MainActivity");
                startActivity(intent);
            }
        });
    }
}