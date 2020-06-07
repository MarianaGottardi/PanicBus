package com.mariana.gottardi.panicbus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    //Widgets
    private String numero1;
    private String numero2;
    private final static int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private Button sendMessageHelp;
    private EditText etNomeLinha;
    private EditText etNumero;
    private EditText etSentido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Panic in the Bus!");

        //REFS
        etNomeLinha = findViewById(R.id.ma_et_linha);
        etNumero= findViewById(R.id.ma_et_number);
        etSentido = findViewById(R.id.ma_et_sentido);
        
        //Número do telefone que deseja enviar a mensagem
        numero2 = "+5551999999999";
        numero1 = "+5551999999999";

        sendMessageHelp = (Button) findViewById(R.id.button_panic);
        sendMessageHelp.setEnabled(false);


        if (checkPermission(Manifest.permission.SEND_SMS)) {
            sendMessageHelp.setEnabled(true);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        sendMessageHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String linha = etNomeLinha.getText().toString();
                String sentido = etSentido.getText().toString();
                String numeroBus = etNumero.getText().toString();
                String msg = "Onibus "+linha+" sentido "+sentido+" de número "+numeroBus+" sendo assaltado!";
                enviaMensagem(msg);

            }
        });

    }

    private void enviaMensagem(String msg) {
        if (checkPermission(Manifest.permission.SEND_SMS)) {
            if (numero1 != null && numero2 != null) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numero1, null, msg, null, null);
                smsManager.sendTextMessage(numero2, null, msg, null, null);
                Toast.makeText(MainActivity.this, "Mensagem enviada com sucesso! ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

}

