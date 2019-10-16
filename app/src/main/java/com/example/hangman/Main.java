package com.example.hangman;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;


public class Main extends AppCompatActivity implements View.OnClickListener {
    public Stack<String> entryList = new Stack<>();
    public String secretWord ="";
    public int score = 0;
    public int guesses = 5;
    public ArrayList<Button> buttonList = new ArrayList<>();
    boolean win = false;
    public String blanksArr[];
    public ArrayList<String> arrBlanks = new ArrayList<>();
    public ArrayList<String> goodGuesses = new ArrayList<>();
    public String blanks = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //All buttons
        Button a = findViewById(R.id.a); buttonList.add(a);
        a.setOnClickListener(this);Button b = findViewById(R.id.b);buttonList.add(b);
        b.setOnClickListener(this);Button c = findViewById(R.id.c);buttonList.add(c);
        c.setOnClickListener(this);Button d = findViewById(R.id.d);buttonList.add(d);
        d.setOnClickListener(this);Button e = findViewById(R.id.e);buttonList.add(e);
        e.setOnClickListener(this);Button f = findViewById(R.id.f);buttonList.add(f);
        f.setOnClickListener(this);Button g = findViewById(R.id.g);buttonList.add(g);
        g.setOnClickListener(this);Button h = findViewById(R.id.h);buttonList.add(h);
        h.setOnClickListener(this);Button i = findViewById(R.id.i);buttonList.add(i);
        i.setOnClickListener(this);Button j = findViewById(R.id.j);buttonList.add(j);
        j.setOnClickListener(this);Button k = findViewById(R.id.k);buttonList.add(k);
        k.setOnClickListener(this);Button l = findViewById(R.id.l);buttonList.add(l);
        l.setOnClickListener(this);Button m = findViewById(R.id.m);buttonList.add(m);
        m.setOnClickListener(this);Button n = findViewById(R.id.n);buttonList.add(n);
        n.setOnClickListener(this);Button o = findViewById(R.id.o);buttonList.add(o);
        o.setOnClickListener(this);Button p = findViewById(R.id.p);buttonList.add(p);
        p.setOnClickListener(this);Button q = findViewById(R.id.q);buttonList.add(q);
        q.setOnClickListener(this);Button r = findViewById(R.id.r);buttonList.add(r);
        r.setOnClickListener(this);Button s = findViewById(R.id.s);buttonList.add(s);
        s.setOnClickListener(this);Button t = findViewById(R.id.t);buttonList.add(t);
        t.setOnClickListener(this);Button u = findViewById(R.id.u);buttonList.add(u);
        u.setOnClickListener(this);Button v = findViewById(R.id.v);buttonList.add(v);
        v.setOnClickListener(this);Button w = findViewById(R.id.w);buttonList.add(w);
        w.setOnClickListener(this);Button x = findViewById(R.id.x);buttonList.add(x);
        x.setOnClickListener(this);Button y = findViewById(R.id.y);buttonList.add(y);
        y.setOnClickListener(this);Button z = findViewById(R.id.z);z.setOnClickListener(this);buttonList.add(z);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Word was " + secretWord, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                reset();
            }
        });
        reset();
    }


    public void reset(){
        secretWord = readRandomWord();
        TextView test = findViewById(R.id.testSecret);
        test.setText(secretWord);
        TextView blanks = findViewById(R.id.blanks);
        blankGen(blanks, secretWord);
        entryList = new Stack<>();
        for(Button b: buttonList){
            b.setEnabled(true);
        }
        goodGuesses.clear();
        TextView win = findViewById(R.id.testCorrect);
        win.setText("");
    }

    public void blankGen(TextView view, String secretWord) {
        arrBlanks.clear();
        view.setText(secretWord);
        String tempString = "";
        for (int i = 0; i < secretWord.length(); i++) {
            arrBlanks.add("_ ");
        }
        view.setText(listToString(arrBlanks));
    }

    public void play(View v){
            String guess = entryList.peek().trim().toLowerCase();
            if (secretWord.contains(guess)) {
                arrBlanks.set(secretWord.indexOf(guess), guess);
                if(secretWord.lastIndexOf(guess) != secretWord.indexOf(guess)){
                    arrBlanks.set(secretWord.lastIndexOf(guess), guess);
                    goodGuesses.add(guess);
                }
                goodGuesses.add(guess);
            }
            goodGuesses.trimToSize();
            if((goodGuesses).size() == arrBlanks.size()){
                score+=100;
                TextView scoreField = findViewById(R.id.score);
                scoreField.setText("" + score);
                TextView test = findViewById(R.id.testCorrect);
                test.setText("Win!");
                addDelay();
            }
            TextView correctBlanks = findViewById(R.id.blanks);
            correctBlanks.setText(listToString(arrBlanks));
        }


    public void addDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 800);
    }

    protected String readRandomWord()  {
        String fileContents = "";
        try {
            InputStream stream = getAssets().open("dictionary.txt");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            fileContents = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String secret = "";
        String dict = (fileContents);
        Scanner in = new Scanner(dict);
        ArrayList<String> words = new ArrayList<>();
        while (in.hasNextLine())
            words.add(in.next());
        Random rand = new Random();
        secret = words.get(rand.nextInt(words.size()));
        return secret;
    }

    public void toastMe(View view){
        Toast myToast = Toast.makeText(this, "Letters: " + entryList, Toast.LENGTH_SHORT);
        myToast.show();
    }

    public String viewChar(View view){
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        return buttonText;
    }

    public void addChar(View view){
        Button b = (Button)view;
        String buttonText = b.getText().toString();
        entryList.add(buttonText);
    }

    public void disable(View view){
        Button b = (Button)view;
        b.setEnabled(false);
    }

    public void renable(View view){
        Button b = (Button) view;
        b.setEnabled(true);
    }

    // array toString
    public static String charToString(String[] c) {
        String s = "";
        for (int i = 0; i < c.length; i++) {
            s += c[i] + " ";
        }
        s += ("\t");
        return s;
    }

    public String listToString(ArrayList<String> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            result += " " + list.get(i);
        }
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
       addChar(v);
       play(v);
       disable(v);
    }
}
