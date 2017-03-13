package com.example.asharm93.colormemory;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Result;

public class DashBoardActivity extends AppCompatActivity {
    private static final int NUM_CARDS = 16;

    private int flips = 0;
    private int flippedCards = 0;
    private int first;
    private int second;
    private int firstImage;
    private int score = 0;
    private List<Integer> images;
    private Boolean disableButtons = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        loadCards();
    }

    private void loadCards() {

        images = Arrays.asList(
                R.drawable.colour1,
                R.drawable.colour2,
                R.drawable.colour3,
                R.drawable.colour4,
                R.drawable.colour5,
                R.drawable.colour6,
                R.drawable.colour7,
                R.drawable.colour8,
                R.drawable.colour1,
                R.drawable.colour2,
                R.drawable.colour3,
                R.drawable.colour4,
                R.drawable.colour5,
                R.drawable.colour6,
                R.drawable.colour7,
                R.drawable.colour8);

        Collections.shuffle(images);

    }

    public void onButtonClick(View v) {
        //High score activity
    }

    public void onCardClick(View v) {

        if (disableButtons)
            return;

        if (flips == 0) {
            flips++;
            first = v.getId();
            firstImage = images.get(Integer.parseInt((String) v.getTag()));
            ((ImageView) v).setImageResource(firstImage);
        } else {
            second = v.getId();
            //user clicks on the same card
            if (first == second)
                return;
            Log.d("TAG", "Second Card!");
            disableButtons = true;

            int secondImage = images.get(Integer.parseInt((String) v.getTag()));
            ((ImageView) v).setImageResource(secondImage);

            final Handler handler = new Handler();
            if (firstImage == secondImage) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideCards(first, second);
                        disableButtons = false;
                        score = score + 2;
                        displayCurrentScore(String.valueOf(score));

                        if (endOfGame()) {
                            Log.d("TAG", "Game Over!");
                            showDialog();
//                            resetGame();
                        }
                    }
                }, 1000);
                flippedCards++;
            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        flipCards(first, second);
                        disableButtons = false;
                        score = score - 1;
                        displayCurrentScore(String.valueOf(score));
                    }
                }, 1000);
            }

            flips = 0;
        }

    }

    private void displayCurrentScore(String score) {

        ((TextView) findViewById(R.id.scoreView)).setText(score);

    }

    private void hideCards(int first, int second) {

        findViewById(second).setVisibility(View.INVISIBLE);
        findViewById(first).setVisibility(View.INVISIBLE);

    }

    private void flipCards(int first, int second) {

        ((ImageView) findViewById(first)).setImageResource(R.drawable.card_bg);
        ((ImageView) findViewById(second)).setImageResource(R.drawable.card_bg);

    }

    private Boolean endOfGame() {

        return flippedCards * 2 == NUM_CARDS;

    }

    private void resetGame() {

        first = 0;
        second = 0;
        score = 0;
        flips = 0;

        displayCurrentScore(String.valueOf(score));

        int count = ((ViewGroup) findViewById(R.id.choice_grid)).getChildCount();
        for (int i = 0; i < count; i++) {
            View viewNext = ((ViewGroup) findViewById(R.id.choice_grid)).getChildAt(i);
            viewNext.setVisibility(View.VISIBLE);
            ((ImageView) viewNext).setImageResource(R.drawable.card_bg);
        }

        loadCards();

    }


    public static class AlertDialogFragment extends DialogFragment {

        public static AlertDialogFragment newInstance(int score) {
            AlertDialogFragment frag = new AlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("score", score);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            LayoutInflater inflater = getActivity().getLayoutInflater();
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_register_score, null))
                    // Add action buttons
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            ContentValues values = new ContentValues();
                            Bundle bundle = getArguments();

                            EditText playerName = (EditText) ((Dialog) dialog).findViewById(R.id.username);

                            values.put(PlayerContract.ScoreEntry.COLUMN_NAME_PLAYER, playerName.getText().toString());
                            values.put(PlayerContract.ScoreEntry.COLUMN_NAME_SCORE, String.valueOf(bundle.getInt("score")));
                            ((DashBoardActivity)getActivity()).new InsertPlayerTask().execute(values);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            ((DashBoardActivity) getActivity()).resetGame();
                        }
                    });
            return builder.create();
        }

    }

    void showDialog() {
        AlertDialogFragment.newInstance(score).show(getSupportFragmentManager(), "dialog");
    }

    private class InsertPlayerTask extends AsyncTask<ContentValues, Void, Void> {

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(ContentValues... values) {

            DBManager db = new DBManager(getApplicationContext());

            try {
                db.insertPlayer(values[0]);
                Log.d("TAG", "INSERTED!");
            } catch (SQLException e) {
                //TODO throw exeption!!
                Log.d("TAG", "Exeption!");
            } finally {
                db.close();
            }
            return null;
        }

        protected void onPostExecute(Result result) {
            //TODO toast, dialog on error, smth
        }
    }

}
