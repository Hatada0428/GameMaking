
package com.example.quiz_typingapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.ArrayList;
import java.util.Collections;

public class HardActivity extends AppCompatActivity {

    private ArrayList<QuizQuestion> questions;

    private int questionCount = 0;

    private TextView questionText;
    private EditText answerInput;
    private Button checkButton;
    private TextView resultText;

    private int correctCount = 0;
    private long startTime;
    private long elapsedTime;


    private int correctInputChars = 0;
    private int totalInputChars = 0;
    private Button nextButton;

    private static class QuizQuestion {
        String question;
        String answer;

        QuizQuestion(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_normal);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        nextButton = findViewById(R.id.nextButton);
        Button btnBack = findViewById(R.id.button_Back);

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(
                    HardActivity.this,
                    MainActivity.class
            );

            startActivity(intent);
            finish();
        });


        questionText = findViewById(R.id.question_Text);
        answerInput = findViewById(R.id.answerInput);
        checkButton = findViewById(R.id.checkButton);
        resultText = findViewById(R.id.resultText);


        questions = new ArrayList<>();

        questions.add(new QuizQuestion("中央処理装置", "Central Processing Unit"));
        questions.add(new QuizQuestion("主記憶装置", "Main Memory"));
        questions.add(new QuizQuestion("補助記憶装置", "Secondary Storage"));
        questions.add(new QuizQuestion("オペレーティングシステム", "Operating System"));
        questions.add(new QuizQuestion("データベース", "Database"));
        questions.add(new QuizQuestion("ネットワーク", "Network"));
        questions.add(new QuizQuestion("アルゴリズム", "Algorithm"));
        questions.add(new QuizQuestion("暗号化", "Encryption"));
        questions.add(new QuizQuestion("仮想記憶", "Virtual Memory"));
        questions.add(new QuizQuestion("入力装置", "Input Device"));
        questions.add(new QuizQuestion("出力装置", "Output Device"));
        questions.add(new QuizQuestion("人工知能", "Artificial Intelligence"));
        questions.add(new QuizQuestion("情報セキュリティ", "Information Security"));
        questions.add(new QuizQuestion("プログラミング", "Programming"));
        questions.add(new QuizQuestion("通信速度", "Communication Speed"));
        questions.add(new QuizQuestion("ファイアウォール", "Firewall"));
        questions.add(new QuizQuestion("クラウドコンピューティング", "Cloud Computing"));
        questions.add(new QuizQuestion("オープンソース", "Open Source"));
        questions.add(new QuizQuestion("インターフェース", "Interface"));
        questions.add(new QuizQuestion("コンパイラ", "Compiler"));
        questions.add(new QuizQuestion("サーバー", "Server"));
        questions.add(new QuizQuestion("クライアント", "Client"));
        questions.add(new QuizQuestion("プロトコル", "Protocol"));
        questions.add(new QuizQuestion("バックアップ", "Backup"));
        questions.add(new QuizQuestion("圧縮", "Compression"));


        Collections.shuffle(questions);


        answerInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {


                if (count > 0 && questionCount > 0) {

                    String correctAnswer =
                            questions.get(questionCount - 1).answer;

                    for (int i = 0; i < count; i++) {

                        int position = start + i;


                        totalInputChars++;


                        if (position < correctAnswer.length()) {

                            char inputChar = s.charAt(position);
                            char correctChar = correctAnswer.charAt(position);


                            if (Character.toLowerCase(inputChar)
                                    == Character.toLowerCase(correctChar)) {

                                correctInputChars++;
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        startTime = System.currentTimeMillis();


        showNextWord();


        checkButton.setOnClickListener(v -> {

            String answer = answerInput.getText().toString().trim();

            String correctAnswer =
                    questions.get(questionCount - 1).answer;

            if (answer.equalsIgnoreCase(correctAnswer)) {


                resultText.setText("正解！");
                resultText.setTextColor(Color.BLUE);

                correctCount++;

                answerInput.setEnabled(false);
                checkButton.setEnabled(false);

                if (questionCount == 15) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                }


                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    showNextWord();
                }, 1000);

            } else {


                resultText.setText(
                        "不正解！\n正解：" + correctAnswer
                );

                resultText.setTextColor(Color.RED);

                answerInput.setEnabled(false);
                checkButton.setEnabled(false);


                if (questionCount == 15) {
                    elapsedTime = System.currentTimeMillis() - startTime;
                }


                nextButton.setVisibility(android.view.View.VISIBLE);
            }
        });
        nextButton.setOnClickListener(v -> {
            showNextWord();
        });
    }


    private void showNextWord() {

        answerInput.setText("");
        resultText.setText("");
        nextButton.setVisibility(android.view.View.GONE);

        if (questionCount < 15) {

            questionText.setText(
                    "第" + (questionCount + 1) + "問\n\n"
                            + questions.get(questionCount).question
            );

            answerInput.setText("");
            resultText.setText("");


            answerInput.setEnabled(true);
            checkButton.setEnabled(true);

            questionCount++;

        } else {


            double accuracy = 0;

            if (totalInputChars > 0) {
                accuracy = correctInputChars * 100.0 / totalInputChars;
            }

            // 結果画面へ移動
            Intent intent = new Intent(
                    HardActivity.this,
                    FinishActivity.class
            );

            intent.putExtra("correctCount", correctCount);
            intent.putExtra("totalCount", 15);
            intent.putExtra("accuracy", accuracy);
            intent.putExtra("elapsedTime", elapsedTime);

            startActivity(intent);
            finish();
        }
    }
}
