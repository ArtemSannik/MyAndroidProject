package com.example.mydiplomand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> articles;
    ArrayAdapter<String> adapter;
    EditText info;
    EditText title;
    Button btn;
    Integer count = 0;

    HashMap<Integer,String> articlesMap =new HashMap<Integer,String>();
    HashSet<String> abrivatet = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        info = findViewById(R.id.info);
        title = findViewById(R.id.title);
        btn = findViewById(R.id.btn);


        articles = new ArrayList<>();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, articles);
        listView.setAdapter(adapter);

        btn.setOnClickListener(v -> {

            String info_user = info.getText().toString();
            String title_user = title.getText().toString();

            boolean isExists = false;

            if (info_user.equals("") || title_user.equals("")) {
                info.setHint("Вы не добавили полную ссылку");
                title.setHint("Вы не добавили свою краткую ссылку");
                return;
            } else {
                if(abrivatet.size()!=0) {
                    if (!abrivatet.contains(title.getText().toString())){
                        isExists = true;
                    }
                }else{
                    isExists = true;
                }
            }
            if (!isExists) {
                Toast.makeText(MainActivity.this, "уже есть такое сокращение: ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Вы добавили: " + info_user + "\n" + "сокращение: " + title_user, Toast.LENGTH_LONG).show();
                addArticle(title_user);
                articlesMap.put(count, info.getText().toString());
                abrivatet.add(title.getText().toString());
                count++;
            }
            info.setText("");
            title.setText("");


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(view);
                    Toast.makeText(MainActivity.this, getInfo(position), Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + getInfo(position)));
                    startActivity(browserIntent);
                }
            });

        });

    }
    public void addArticle(String article) {
        articles.add("Cокращение: " + article);
        listView.setAdapter(adapter);

    }

    public String getInfo(Integer position) {
        for (Map.Entry<Integer,String> entry : articlesMap.entrySet()) {
            if (entry.getKey() == position) {
                return entry.getValue();
            }
        }
        return "error";
    }
}




