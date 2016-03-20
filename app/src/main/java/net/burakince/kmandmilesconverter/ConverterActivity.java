package net.burakince.kmandmilesconverter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ConverterActivity extends AppCompatActivity implements View.OnTouchListener {

    private final DecimalFormat formatValue = new DecimalFormat("##.##");

    private TextView fromLabel;
    private EditText fromValue;
    private TextView toLabel;
    private EditText result;
    private AlertDialog alertDialog;
    private Mode mode = Mode.MILES_TO_KM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        fromLabel = (TextView) findViewById(R.id.fromLabel);
        fromValue = (EditText) findViewById(R.id.fromValue);
        toLabel = (TextView) findViewById(R.id.toLabel);
        result = (EditText) findViewById(R.id.result);

        fromValue.setOnTouchListener(this);

        final LinearLayout linearLayout = new LinearLayout(ConverterActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        final NumberPicker leftNumberPicker = new NumberPicker(ConverterActivity.this);
        leftNumberPicker.setMaxValue(9999);
        leftNumberPicker.setMinValue(0);

        final TextView dotLabel = new TextView(ConverterActivity.this);
        dotLabel.setText(".");
        dotLabel.setTextSize(40);

        final NumberPicker rightNumberPicker = new NumberPicker(ConverterActivity.this);
        rightNumberPicker.setMaxValue(99);
        rightNumberPicker.setMinValue(0);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        final LinearLayout.LayoutParams leftNumPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftNumPicerParams.weight = 1;

        final LinearLayout.LayoutParams dotLabelParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dotLabelParams.setMargins(10, 180, 10, 0);
        leftNumPicerParams.weight = 1;

        final LinearLayout.LayoutParams rightNumPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rightNumPicerParams.weight = 1;

        linearLayout.setLayoutParams(params);
        linearLayout.addView(leftNumberPicker, leftNumPicerParams);
        linearLayout.addView(dotLabel, dotLabelParams);
        linearLayout.addView(rightNumberPicker, rightNumPicerParams);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConverterActivity.this);
        alertDialogBuilder.setTitle("Select the number");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                fromValue.setText(String.valueOf(leftNumberPicker.getValue()) + "." + String.valueOf(rightNumberPicker.getValue()));
                                calculateResult();
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        alertDialog = alertDialogBuilder.create();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            return true;
        }

        alertDialog.show();
        return true;
    }

    public void switchMode(View view) {
        if (mode == Mode.MILES_TO_KM) {
            mode = Mode.KM_TO_MILES;
            fromLabel.setText("Km");
            toLabel.setText("Miles");
        } else {
            mode = Mode.MILES_TO_KM;
            fromLabel.setText("Miles");
            toLabel.setText("Km");

        }
        calculateResult();
    }

    private void calculateResult() {
        if (mode == Mode.MILES_TO_KM) {
            double vMiles = Double.valueOf(fromValue.getText().toString());
            double vKm = vMiles / 0.62137;
            result.setText(formatValue.format(vKm));
        } else {
            double vKm = Double.valueOf(fromValue.getText().toString());
            double vMiles = vKm * 0.62137;
            result.setText(formatValue.format(vMiles));
        }
    }

}
